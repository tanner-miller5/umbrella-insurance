package com.umbrella.insurance.core.models.faqs.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Faq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FaqServiceImpl implements FaqService {
    @Autowired
    FaqRepository faqRepository;

    @Override
    public Faq saveFaq(Faq faq) {
        return faqRepository.save(faq);
    }

    @Override
    public List<Faq> getFaqs() {
        return faqRepository.findAll();
    }

    @Override
    public Faq updateFaq(Faq faq) {
        return faqRepository.save(faq);
    }

    @Override
    public void deleteFaq(Long faqId) {
        faqRepository.deleteById(faqId);
    }

    @Override
    public Optional<Faq> getFaq(Long faqId) {
        return faqRepository.findById(faqId);
    }

    @Override
    public Optional<Faq> getFaqByName(String faqName) {
        return faqRepository.findFaqByFaqName(faqName);
    }

    @Override
    public void deleteFaqByName(String faqName) {
        faqRepository.deleteFaqByFaqName(faqName);
    }
}
