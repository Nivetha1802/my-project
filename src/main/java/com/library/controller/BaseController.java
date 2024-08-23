package com.library.controller;

import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



public interface BaseController<T> {

    String create(@Valid @ModelAttribute("entity") T entity, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model);

    String update(@Valid @ModelAttribute("entity") T entity, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model);

    String delete(@Valid @ModelAttribute("entity") T entity, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model);

}
