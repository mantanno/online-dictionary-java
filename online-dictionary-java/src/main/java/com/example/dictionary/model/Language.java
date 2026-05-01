package com.example.dictionary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "languages", uniqueConstraints = {
        @UniqueConstraint(name = "uk_language_code", columnNames = "code"),
        @UniqueConstraint(name = "uk_language_name", columnNames = "name")
})
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Код мови не може бути порожнім")
    @Size(max = 10, message = "Код мови має бути коротким")
    @Column(nullable = false, length = 10)
    private String code;

    @NotBlank(message = "Назва мови не може бути порожньою")
    @Size(max = 60, message = "Назва мови занадто довга")
    @Column(nullable = false, length = 60)
    private String name;

    public Language() {
    }

    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}
