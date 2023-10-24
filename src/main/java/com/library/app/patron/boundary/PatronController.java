package com.library.app.patron.boundary;

import com.library.app.patron.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/patrons")
public class PatronController {
    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public String listPatrons(Model model) {
        List<PatronDTO> patronDTOs = patronService.getAllPatrons();
        model.addAttribute("patrons", patronDTOs);
        return "patrons-list";
    }

    @GetMapping("/add")
    public String showPatronForm(Model model) {
        model.addAttribute("patronDTO", new PatronDTO());
        return "patrons-add";
    }

    @PostMapping("/add")
    public String addPatron(
            @ModelAttribute("patronDTO") @Valid PatronDTO patronDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "patrons-add";
        }

        patronService.addPatron(patronDTO);

        return "redirect:/patrons";
    }

    @GetMapping("/edit/{id}")
    public String editPatronForm(@PathVariable Long id, Model model) {
        PatronDTO patronDTO = patronService.getPatronById(id);
        model.addAttribute("patronDTO", patronDTO);
        return "patrons-edit";
    }

    @PostMapping("/edit")
    public String editPatron(@ModelAttribute PatronDTO patronDTO) {
        patronService.editPatron(patronDTO);
        return "redirect:/patrons";
    }

    @GetMapping("/delete/{id}")
    public String deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return "redirect:/patrons";
    }

    @GetMapping("/search-patrons")
    public String searchPatrons(@RequestParam(name = "searchText", required = false) String searchText,
                                @RequestParam(name = "member", required = false) boolean member,
                                Model model) {
        List<PatronDTO> patronDTOs;

        if (searchText != null && !searchText.isEmpty()) {
            patronDTOs = patronService.filterPatrons(searchText, member);
        } else {
            patronDTOs = patronService.getAllPatrons();
        }

        model.addAttribute("patrons", patronDTOs);
        return "patrons-list";
    }
}