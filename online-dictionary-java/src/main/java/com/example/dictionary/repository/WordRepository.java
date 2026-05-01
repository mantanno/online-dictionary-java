package com.example.dictionary.repository;

import com.example.dictionary.model.Language;
import com.example.dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    Optional<Word> findByTextIgnoreCaseAndLanguage(String text, Language language);
    List<Word> findByTextContainingIgnoreCaseAndLanguage(String text, Language language);
    List<Word> findByLanguageOrderByTextAsc(Language language);
}
