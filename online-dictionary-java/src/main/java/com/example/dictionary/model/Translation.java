package com.example.dictionary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "translations", uniqueConstraints = {
        @UniqueConstraint(name = "uk_translation_pair", columnNames = {"source_word_id", "target_word_id"})
})
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "source_word_id", nullable = false)
    private Word sourceWord;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "target_word_id", nullable = false)
    private Word targetWord;

    @Size(max = 255, message = "Примітка занадто довга")
    @Column(length = 255)
    private String note;

    public Translation() {
    }

    public Translation(Word sourceWord, Word targetWord, String note) {
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Word getSourceWord() {
        return sourceWord;
    }

    public void setSourceWord(Word sourceWord) {
        this.sourceWord = sourceWord;
    }

    public Word getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(Word targetWord) {
        this.targetWord = targetWord;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}
