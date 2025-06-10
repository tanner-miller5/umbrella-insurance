package com.umbrella.insurance.core.models.faqs.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
    Optional<Faq> findFaqByFaqName(String faqName);
    void deleteFaqByFaqName(String faqName);
}
