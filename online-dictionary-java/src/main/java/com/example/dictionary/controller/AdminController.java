package com.example.dictionary.controller;

import com.example.dictionary.model.Language;
import com.example.dictionary.service.DictionaryService;
import com.example.dictionary.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;

    public AdminController(LanguageService languageService, DictionaryService dictionaryService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("words", dictionaryService.findAllWords());
        model.addAttribute("translations", dictionaryService.findAllTranslations());
        return "admin/dashboard";
    }

    @GetMapping("/languages/new")
    public String newLanguage(Model model) {
        model.addAttribute("language", new Language());
        return "admin/language-form";
    }

    @PostMapping("/languages")
    public String saveLanguage(@Valid @ModelAttribute Language language,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/language-form";
        }
        try {
            languageService.save(language);
            redirectAttributes.addFlashAttribute("success", "Мову збережено");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/languages/{id}/edit")
    public String editLanguage(@PathVariable Long id, Model model) {
        model.addAttribute("language", languageService.findById(id));
        return "admin/language-form";
    }

    @PostMapping("/languages/{id}/delete")
    public String deleteLanguage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            languageService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Мову видалено");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Неможливо видалити мову, якщо з нею пов'язані слова або переклади");
        }
        return "redirect:/admin";
    }

    @PostMapping("/words")
    public String saveWord(@RequestParam String text,
                           @RequestParam Long languageId,
                           RedirectAttributes redirectAttributes) {
        try {
            dictionaryService.saveWord(text, languageId);
            redirectAttributes.addFlashAttribute("success", "Слово додано");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/words/{id}/delete")
    public String deleteWord(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            dictionaryService.deleteWord(id);
            redirectAttributes.addFlashAttribute("success", "Слово видалено");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/translations")
    public String saveTranslation(@RequestParam String sourceText,
                                  @RequestParam Long sourceLanguageId,
                                  @RequestParam String targetText,
                                  @RequestParam Long targetLanguageId,
                                  @RequestParam(required = false) String note,
                                  RedirectAttributes redirectAttributes) {
        try {
            dictionaryService.saveTranslation(sourceText, sourceLanguageId, targetText, targetLanguageId, note);
            redirectAttributes.addFlashAttribute("success", "Переклад додано у двох напрямках");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/translations/{id}/delete")
    public String deleteTranslation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            dictionaryService.deleteTranslation(id);
            redirectAttributes.addFlashAttribute("success", "Переклад видалено");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }
}
