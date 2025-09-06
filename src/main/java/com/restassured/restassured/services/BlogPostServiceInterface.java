package com.restassured.restassured.services;

import com.restassured.restassured.entities.BlogPost;

import java.util.List;

public interface BlogPostServiceInterface {

    List<BlogPost>getAllBlogPosts();

    BlogPost getBlogPostById(Long id);

    BlogPost editBlogPostById(String callerSUB, BlogPost blogPost);

    BlogPost addBlogPost(String callerSUB, BlogPost blogPost);

    void deleteBlogPostById(String callerSUB, List callerRoles, Long id);

    Long getBlogPostCount();
}
