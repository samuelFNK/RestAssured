package com.restassured.restassured.controllers;

import com.restassured.restassured.converters.JwtAuthConverter;
import com.restassured.restassured.entities.BlogPost;
import com.restassured.restassured.services.BlogPostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService, JwtAuthConverter jwtAuthConverter){
        this.blogPostService = blogPostService;
    }

    @GetMapping("/posts")
    public List<BlogPost> getAllBlogPosts(){
        return blogPostService.getAllBlogPosts();
    }

    @GetMapping("/post/{id}")
    public BlogPost getBlogPostById(@PathVariable Long id){
        return blogPostService.getBlogPostById(id);
    }

    @GetMapping("/count")
    public Long getBlogPostCount(){
        return blogPostService.getBlogPostCount();
    }

    @PostMapping("/newpost")
    public BlogPost addNewBlogPost(@AuthenticationPrincipal Jwt jwt, @RequestBody BlogPost blogPost){
        return blogPostService.addBlogPost(jwt.getSubject(), blogPost);
    }

    @PutMapping("/updatepost")
    public BlogPost editBlogPost(@AuthenticationPrincipal Jwt jwt, @RequestBody BlogPost blogPost){
        return blogPostService.editBlogPostById(jwt.getSubject(), blogPost);
    }

    @DeleteMapping("/deletepost/{id}")
    public void deleteBlogPostById(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id){

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get("client1");
        List<String> roles = (List<String>) clientRoles.get("roles");


        blogPostService.deleteBlogPostById(jwt.getSubject(), roles, id);
    }

}