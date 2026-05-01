package com.example.dictionary.service;

import com.example.dictionary.model.Language;
import com.example.dictionary.repository.LanguageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> findAll() {
        return languageRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Language::getName))
                .toList();
    }

    public Language findById(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Мову не знайдено"));
    }

    @Transactional
    public Language save(Language language) {
        validate(language);
        return languageRepository.save(language);
    }

    @Transactional
    public void delete(Long id) {
        if (!languageRepository.existsById(id)) {
            throw new IllegalArgumentException("Мову не знайдено");
        }
        languageRepository.deleteById(id);
    }

    private void validate(Language language) {
        if (language.getCode() == null || language.getCode().isBlank()) {
            throw new IllegalArgumentException("Код мови є обов'язковим");
        }
        if (language.getName() == null || language.getName().isBlank()) {
            throw new IllegalArgumentException("Назва мови є обов'язковою");
        }
    }
}
