package com.example.dictionary.controller;

import com.example.dictionary.model.Translation;
import com.example.dictionary.service.DictionaryService;
import com.example.dictionary.service.LanguageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DictionaryController {
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;

    public DictionaryController(LanguageService languageService, DictionaryService dictionaryService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("languages", languageService.findAll());
        return "index";
    }

    @GetMapping("/translate")
    public String translate(@RequestParam Long sourceLanguageId,
                            @RequestParam Long targetLanguageId,
                            @RequestParam String query,
                            Model model) {
        List<Translation> results = dictionaryService.translate(sourceLanguageId, targetLanguageId, query);
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("results", results);
        model.addAttribute("query", query);
        model.addAttribute("sourceLanguageId", sourceLanguageId);
        model.addAttribute("targetLanguageId", targetLanguageId);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
