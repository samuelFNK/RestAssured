package com.restassured.restassured.services;

import com.restassured.restassured.entities.BlogPost;
import com.restassured.restassured.exceptions.ResourceNotFoundException;
import com.restassured.restassured.repositories.BlogPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService implements BlogPostServiceInterface {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public List<BlogPost> getAllBlogPosts() {

        List<BlogPost> allCurrentBlogPosts = blogPostRepository.findAll();

        if (allCurrentBlogPosts.isEmpty()){
            throw new ResourceNotFoundException("Blog Posts", "blogPostRepository.findAll()");
        }

        return allCurrentBlogPosts;
    }

    @Override
    public BlogPost getBlogPostById(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost", "blogPostRepository.findById(" + id +")"));
    }

    @Override
    public BlogPost editBlogPostById(Long id, BlogPost blogPost) {

        BlogPost selectedBlogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost id", "blogPostRepository.findById(" + id + ")"));

        if (blogPost.getTitle() != null && blogPost.getTitle() != selectedBlogPost.getTitle()){
            selectedBlogPost.setTitle(blogPost.getTitle());
        }
        if (blogPost.getBody() != null && blogPost.getBody() != selectedBlogPost.getBody()){
            selectedBlogPost.setBody(blogPost.getBody());
        }

        return blogPostRepository.save(selectedBlogPost);
    }

    @Override
    public BlogPost addBlogPost(BlogPost blogPost) {

        return blogPostRepository.save(blogPost);
    }

    @Override
    public void deleteBlogPostById(Long id) {

        if (!blogPostRepository.existsById(id)){
            throw new ResourceNotFoundException("BlogPost", "blogPostRepository.existsById(" + id +")");
        }

        blogPostRepository.deleteById(id);
    }
}
