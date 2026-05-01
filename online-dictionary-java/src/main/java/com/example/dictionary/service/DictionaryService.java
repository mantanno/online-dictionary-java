package com.example.dictionary.service;

import com.example.dictionary.model.Language;
import com.example.dictionary.model.Translation;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.TranslationRepository;
import com.example.dictionary.repository.WordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class DictionaryService {
    private final WordRepository wordRepository;
    private final TranslationRepository translationRepository;
    private final LanguageService languageService;

    public DictionaryService(WordRepository wordRepository,
                             TranslationRepository translationRepository,
                             LanguageService languageService) {
        this.wordRepository = wordRepository;
        this.translationRepository = translationRepository;
        this.languageService = languageService;
    }

    public List<Word> findAllWords() {
        return wordRepository.findAll()
                .stream()
                .sorted(Comparator.comparing((Word w) -> w.getLanguage().getName()).thenComparing(Word::getText))
                .toList();
    }

    public List<Translation> findAllTranslations() {
        return translationRepository.findAll()
                .stream()
                .sorted(Comparator.comparing((Translation t) -> t.getSourceWord().getText())
                        .thenComparing(t -> t.getTargetWord().getText()))
                .toList();
    }

    public List<Translation> translate(Long sourceLanguageId, Long targetLanguageId, String query) {
        Language sourceLanguage = languageService.findById(sourceLanguageId);
        Language targetLanguage = languageService.findById(targetLanguageId);
        String normalizedQuery = normalize(query);

        if (normalizedQuery.isBlank()) {
            return List.of();
        }

        List<Translation> exact = translationRepository.findExactTranslations(normalizedQuery, sourceLanguage, targetLanguage);
        if (!exact.isEmpty()) {
            return exact;
        }
        return translationRepository.searchTranslations(normalizedQuery, sourceLanguage, targetLanguage);
    }

    @Transactional
    public Word saveWord(String text, Long languageId) {
        Language language = languageService.findById(languageId);
        String normalizedText = normalize(text);
        if (normalizedText.isBlank()) {
            throw new IllegalArgumentException("Слово не може бути порожнім");
        }
        return wordRepository.findByTextIgnoreCaseAndLanguage(normalizedText, language)
                .orElseGet(() -> wordRepository.save(new Word(normalizedText, language)));
    }

    @Transactional
    public Translation saveTranslation(String sourceText,
                                       Long sourceLanguageId,
                                       String targetText,
                                       Long targetLanguageId,
                                       String note) {
        if (sourceLanguageId.equals(targetLanguageId)) {
            throw new IllegalArgumentException("Мови перекладу мають бути різними");
        }
        Word sourceWord = saveWord(sourceText, sourceLanguageId);
        Word targetWord = saveWord(targetText, targetLanguageId);
        Translation translation = new Translation(sourceWord, targetWord, note);
        Translation reverseTranslation = new Translation(targetWord, sourceWord, note);
        Translation saved = translationRepository.save(translation);
        translationRepository.save(reverseTranslation);
        return saved;
    }

    @Transactional
    public void deleteWord(Long id) {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Слово не знайдено"));
        translationRepository.findBySourceWord(word).forEach(translationRepository::delete);
        translationRepository.findByTargetWord(word).forEach(translationRepository::delete);
        wordRepository.delete(word);
    }

    @Transactional
    public void deleteTranslation(Long id) {
        if (!translationRepository.existsById(id)) {
            throw new IllegalArgumentException("Переклад не знайдено");
        }
        translationRepository.deleteById(id);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
