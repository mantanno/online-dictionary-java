package com.example.dictionary.repository;

import com.example.dictionary.model.Language;
import com.example.dictionary.model.Translation;
import com.example.dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findBySourceWord(Word sourceWord);
    List<Translation> findByTargetWord(Word targetWord);

    @Query("""
            select t from Translation t
            where lower(t.sourceWord.text) = lower(:text)
              and t.sourceWord.language = :sourceLanguage
              and t.targetWord.language = :targetLanguage
            order by t.targetWord.text asc
            """)
    List<Translation> findExactTranslations(
            @Param("text") String text,
            @Param("sourceLanguage") Language sourceLanguage,
            @Param("targetLanguage") Language targetLanguage
    );

    @Query("""
            select t from Translation t
            where lower(t.sourceWord.text) like lower(concat('%', :text, '%'))
              and t.sourceWord.language = :sourceLanguage
              and t.targetWord.language = :targetLanguage
            order by t.sourceWord.text asc, t.targetWord.text asc
            """)
    List<Translation> searchTranslations(
            @Param("text") String text,
            @Param("sourceLanguage") Language sourceLanguage,
            @Param("targetLanguage") Language targetLanguage
    );
}
