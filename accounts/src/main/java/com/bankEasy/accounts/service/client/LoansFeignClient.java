package com.bankEasy.accounts.service.client;
import com.bankEasy.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

// Here the goal is to obtain instance details mentioned in the feign client via eureka
// we use declarative programming similar to JPA repo, wherein only abstract details
// are provided rather than impl code

//1. This interface will communicate with eureka
//2. Fetch instance details
//3. If multiple, spring load balancer passes request to node that is most suitable to process (eg: least nodes connection strategy)
//4. Target microservice node (loans) processes request and returns response
//5. Parent invoker method consolidates data from all feign clients along with its own microservice data
//6. returns consolidated response


//Note : all client interfaces will be invoked from parent invoker method which is defined in interface under service
@FeignClient("loans")
public interface LoansFeignClient {

    //mapping and return types to match the actual target microservice controller method
    @GetMapping(value = "/api/fetch-loan",consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoan (@RequestParam String mobileNumber, @RequestHeader("bankeasy-correlation-id") String correlationId);
}
