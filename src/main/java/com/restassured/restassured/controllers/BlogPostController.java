package com.restassured.restassured.controllers;

import com.restassured.restassured.entities.BlogPost;
import com.restassured.restassured.services.BlogPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService){
        this.blogPostService = blogPostService;
    }

    @GetMapping("/posts")
    public List<BlogPost> getAllPosts(){
        return blogPostService.getAllBlogPosts();
    }

    @GetMapping("/post/{id}")
    public BlogPost getPostById(@PathVariable Long id){
        return blogPostService.getBlogPostById(id);
    }




}