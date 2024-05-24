package com.dbh.controller;

import com.dbh.entity.Employee;
import com.dbh.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<Employee> employees = employeeService.findAll(page, size);
        int totalPages = employees.getTotalPages();
        model.addAttribute("employees", employees);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("title", "Home Page");
        return "home";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/save")
    public String save(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id).get();
        model.addAttribute("employee", employee);
        return "details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id).get();
        model.addAttribute("employee", employee);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/";
    }
}