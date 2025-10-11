# Starting directory: /helm
$rootPath = Get-Location

Write-Host ">>> Navigating into bankeasy-services..."
Set-Location "$rootPath/bankeasy-services"

# List of service charts
$services = @(
    "cards",
    "loans",
    "message",
    "accounts",
    "gatewayserver",
    "configserver",
    "eurekaserver"
)

foreach ($service in $services) {
    $servicePath = "$rootPath/bankeasy-services/$service"
    if (Test-Path $servicePath) {
        Write-Host ">>> Building dependencies for $service..."
        Set-Location $servicePath
        helm dependency build
    } else {
        Write-Host "!!! Skipping $service (folder not found)"
    }
}

Write-Host ">>> Returning to /helm..."
Set-Location $rootPath

Write-Host ">>> Navigating into environments..."
Set-Location "$rootPath/environments"

# List of environment charts
$environments = @(
    "qa-env",
    "dev-env",
    "prod-env"
)

foreach ($env in $environments) {
    $envPath = "$rootPath/environments/$env"
    if (Test-Path $envPath) {
        Write-Host ">>> Building dependencies for $env..."
        Set-Location $envPath
        helm dependency build
    } else {
        Write-Host "!!! Skipping $env (folder not found)"
    }
}

Write-Host ">>> Finished building all dependencies."