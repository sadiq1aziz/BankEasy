package com.bankeasy.cards.audit;


import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component("auditAwareImpl")
//we implement the auditorAware Interface to take care of auto populating fields
//via Spring Data JPA such as lastModifiedBy, createdBy etc.
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    //auto populates with the string value
    //Optional.of -> ensures value is not null
    //Optional.nullable -> needs to be handled via isPresent orElse
    public Optional<String> getCurrentAuditor() {
        return Optional.of("MS_Cards");
    }
}
