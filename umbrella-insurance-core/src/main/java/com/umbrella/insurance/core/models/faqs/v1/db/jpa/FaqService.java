package com.umbrella.insurance.core.models.faqs.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Faq;

import java.util.List;
import java.util.Optional;

public interface FaqService {
    Faq saveFaq(Faq faq);
    List<Faq> getFaqs();
    Faq updateFaq(Faq faq);
    void deleteFaq(Long faqId);
    Optional<Faq> getFaq(Long faqId);
    Optional<Faq> getFaqByName(String faqName);
    void deleteFaqByName(String faqName);
}
