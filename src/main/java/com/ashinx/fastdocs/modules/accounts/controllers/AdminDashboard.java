package com.ashinx.fastdocs.modules.accounts.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AdminDashboard {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String execute() {
        return "You are in admin dashboard";
    }
}
