package com.bankeasy.loans.audit;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

//register this class as a bean for spring to manage
@Component("auditAwareImpl")
//Use interface provided by spring JPA framework for capturing auditor info
//returns auditor data as a String (currently hardcoded)
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("LOANS_MS");
    }
}
