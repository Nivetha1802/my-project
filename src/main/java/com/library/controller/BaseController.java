package com.library.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface BaseController<T> {

        String create(@Valid @ModelAttribute("entity") T entity, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model, @RequestParam("selectedEntities") String selectedEntities,
                        HttpSession session) throws JsonMappingException, JsonProcessingException;

        String update(@Valid @ModelAttribute("entity") T entity, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model, @RequestParam("selectedEntities") String selectedEntities) throws JsonProcessingException;

        String delete(@Valid @ModelAttribute("entity") T entity, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model, @RequestParam("selectedEntities") String selectedEntities,
                        HttpSession session) throws JsonProcessingException;

}
