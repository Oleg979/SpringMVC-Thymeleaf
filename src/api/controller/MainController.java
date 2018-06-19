package com.oleg.mailer.controllers;

import com.oleg.mailer.entities.User;
import com.oleg.mailer.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    final UserRepo userRepo;

    @Autowired
    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("users")
    public String getAll(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @GetMapping("add")
    public String showAddPage() {
        return "add.html";
    }

    @PostMapping("add")
    public String addUser(@RequestParam String name, @RequestParam String password, Model model) {
         if(name.length() == 0 || password.length() == 0) {
            model.addAttribute("msg", "You didn't fill all fields!");
            return "add";
         }
         if(password.length() < 8) {
             model.addAttribute("msg", "Password must contain at least 8 chars!");
             return "add";
         }
        userRepo.save(new User(name, password));
        return "redirect:/users";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepo.deleteById(Integer.valueOf(id));
        return "redirect:/users";
    }

    @GetMapping("edit/{id}")
    public String showEditPage(@PathVariable String id, Model model) {
        model.addAttribute("userId", id);
        User user = userRepo.findById(Integer.valueOf(id)).get();
        model.addAttribute("userName", user.getName());
        model.addAttribute("userPass", user.getPassword());
        return "edit";
    }

    @PostMapping("edit/{id}")
    public String editUser(@PathVariable String id, @RequestParam String name,
                           @RequestParam String password, Model model) {
        if(name.length() == 0 || password.length() == 0) {
            model.addAttribute("msg", "You didn't fill all fields!");
            return "edit";
        }
        if(password.length() < 8) {
            model.addAttribute("msg", "Password must contain at least 8 chars!");
            return "edit";
        }
        User user = userRepo.findById(Integer.valueOf(id)).get();
        user.setName(name);
        user.setPassword(password);
        userRepo.save(user);
        return "redirect:/users";
    }
}
