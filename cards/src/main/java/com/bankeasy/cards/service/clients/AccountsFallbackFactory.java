//package com.bankeasy.cards.service.clients;
//
//import com.bankeasy.cards.dto.CustomerDto;
//import com.bankeasy.cards.dto.ErrorResponseDto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.openfeign.FallbackFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AccountsFallbackFactory implements FallbackFactory<AccountsFeignClient> {
//
//    private static final Logger logger = LoggerFactory.getLogger(AccountsFallbackFactory.class);
//
//    @Override
//    public AccountsFeignClient create(Throwable cause) {
//
//        logger.error("Fallback Factory triggered due to: {}", cause.toString(), cause);
//
//        return new AccountsFeignClient() {
//            @Override
//            public ResponseEntity<CustomerDto> fetchAccount(String mobileNumber) {
//                CustomerDto dto = new CustomerDto();
//                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(dto);
//            }
//        };
//    }
//}
