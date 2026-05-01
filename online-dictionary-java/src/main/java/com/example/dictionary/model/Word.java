package com.example.dictionary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "words", uniqueConstraints = {
        @UniqueConstraint(name = "uk_word_text_language", columnNames = {"text", "language_id"})
})
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Слово не може бути порожнім")
    @Size(max = 120, message = "Слово занадто довге")
    @Column(nullable = false, length = 120)
    private String text;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    public Word() {
    }

    public Word(String text, Language language) {
        this.text = text;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = normalize(text);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return text + " — " + language;
    }
}
