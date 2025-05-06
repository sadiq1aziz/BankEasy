package com.bankEasy.accounts.audit;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

//create bean out of this class

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("MS_ACCOUNTS");
        }
    }
