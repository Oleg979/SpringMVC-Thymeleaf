package api.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import api.form.PersonForm;
import api.model.Person;

import java.util.List;

@Controller
public class PersonController {
 
    private static List<Person> persons = new ArrayList<>();
 
    static {
        persons.add(new Person("Oleg", "Solovev"));
        persons.add(new Person("Karina", "Dementieva"));
    }
 
   
    @Value("${welcome.message}")
    private String message;
 
    @Value("${error.message}")
    private String errorMessage;
 
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", message);
        return "welcome";
    }
 
    @GetMapping("/personList")
    public String personList(Model model) {
        model.addAttribute("persons", persons);
        return "personList";
    }
 
    @GetMapping("/addPerson")
    public String showAddPersonPage(Model model) {
        PersonForm form = new PersonForm();
        model.addAttribute("personForm", form);
        return "addPerson";
    }
 
    @PostMapping("/addPerson")
    public String savePerson(Model model, @ModelAttribute("personForm") PersonForm form) {
 
        String firstName = form.getFirstName();
        String lastName = form.getLastName();
 
        if (firstName != null && firstName.length() > 0 && lastName != null && lastName.length() > 0) {
            persons.add(new Person(firstName, lastName));
            return "redirect:/personList";
        }
 
        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    }
 
}
