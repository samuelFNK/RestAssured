package com.restassured.restassured.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class BlogPostController {

    @GetMapping("/posts")
    public String getAllPosts(){
        return "Test String For Admins";
    }

    @GetMapping("/post/{id}")
    public String getPostById(){
        return "Test String single post by id";
    }

}