# MySQL Redeploy Script for PowerShell
# This script uninstalls, cleans up, and reinstalls the MySQL Helm chart

# Configuration
$ReleaseName = "loansdb"
$ChartDir = "loansdb"
$Namespace = if ($env:NAMESPACE) { $env:NAMESPACE } else { "default" }
$RootPassword = if ($env:MYSQL_ROOT_PASSWORD) { $env:MYSQL_ROOT_PASSWORD } else { "root" }
$ServicePort = if ($env:MYSQL_SERVICE_PORT) { $env:MYSQL_SERVICE_PORT } else { "3308" }

Write-Host ""
Write-Host "Starting MySQL redeployment..." -ForegroundColor Green
Write-Host ""

# Step 1: Uninstall existing release
Write-Host "Step 1: Uninstalling Helm release '$ReleaseName'..." -ForegroundColor Yellow
try {
    helm uninstall $ReleaseName -n $Namespace 2>&1 | Out-Null
    Write-Host "[OK] Release uninstalled" -ForegroundColor Green
} catch {
    Write-Host "[SKIP] Release not found" -ForegroundColor Yellow
}
Write-Host ""

# Step 2: Delete PVC
Write-Host "Step 2: Deleting PVC 'data-$ReleaseName-mysql-0'..." -ForegroundColor Yellow
try {
    kubectl delete pvc "data-$ReleaseName-mysql-0" -n $Namespace 2>&1 | Out-Null
    Write-Host "[OK] PVC deleted" -ForegroundColor Green
} catch {
    Write-Host "[SKIP] PVC not found" -ForegroundColor Yellow
}
Write-Host ""

# Step 3: Build Helm dependencies
Write-Host "Step 3: Building Helm dependencies..." -ForegroundColor Yellow
Push-Location $ChartDir
helm dependency build
Pop-Location
Write-Host "[OK] Dependencies built" -ForegroundColor Green
Write-Host ""

# Step 4: Install Helm chart
Write-Host "Step 4: Installing Helm chart '$ReleaseName'..." -ForegroundColor Yellow
helm install $ReleaseName $ChartDir --set auth.rootPassword=$RootPassword --set primary.service.port=$ServicePort -n $Namespace

if ($LASTEXITCODE -eq 0) {
    Write-Host "[OK] Chart installed" -ForegroundColor Green
} else {
    Write-Host "[ERROR] Failed to install chart" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Step 5: Wait for pod to be ready
Write-Host "Step 5: Waiting for pod to be ready..." -ForegroundColor Yellow
$maxWait = 60
$elapsed = 0
$interval = 5

while ($elapsed -lt $maxWait) {
    $podStatus = kubectl get pod "$ReleaseName-mysql-0" -n $Namespace -o jsonpath='{.status.phase}' 2>&1
    if ($podStatus -eq "Running") {
        $ready = kubectl get pod "$ReleaseName-mysql-0" -n $Namespace -o jsonpath='{.status.conditions[?(@.type=="Ready")].status}' 2>&1
        if ($ready -eq "True") {
            Write-Host "[OK] Pod is ready" -ForegroundColor Green
            break
        }
    }
    Write-Host "Waiting... $elapsed of $maxWait seconds" -ForegroundColor Cyan
    Start-Sleep -Seconds $interval
    $elapsed += $interval
}

if ($elapsed -ge $maxWait) {
    Write-Host "[ERROR] Pod failed to become ready. Showing logs:" -ForegroundColor Red
    Write-Host ""
    kubectl logs "$ReleaseName-mysql-0" -n $Namespace
    exit 1
}
Write-Host ""

# Step 6: Show logs
Write-Host "Step 6: Displaying pod logs..." -ForegroundColor Yellow
Write-Host "============================================" -ForegroundColor Green
kubectl logs "$ReleaseName-mysql-0" -n $Namespace
Write-Host "============================================" -ForegroundColor Green
Write-Host ""

Write-Host "[SUCCESS] MySQL redeployment completed!" -ForegroundColor Green