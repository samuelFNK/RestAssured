package com.restassured.restassured.services;

import com.restassured.restassured.entities.BlogPost;

import java.util.List;

public interface BlogPostServiceInterface {

    List<BlogPost>getAllBlogPosts();

    BlogPost getBlogPostById(Long id);

    BlogPost editBlogPostById(Long id, BlogPost blogPost);

    BlogPost addBlogPost(BlogPost blogPost);

    void deleteBlogPostById(Long id);

}
