package com.restassured.restassured.services;

import com.restassured.restassured.entities.BlogPost;
import com.restassured.restassured.exceptions.PostOwnershipViolationException;
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
    public BlogPost editBlogPostById(String callerSUB, BlogPost blogPost) {

        BlogPost selectedBlogPost = blogPostRepository.findById(blogPost.getId())
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost id", "blogPostRepository.findById(" + blogPost.getId() + ")"));

        if (blogPost.getTitle() != null && blogPost.getTitle() != selectedBlogPost.getTitle()){
            selectedBlogPost.setTitle(blogPost.getTitle());
        }
        if (blogPost.getBody() != null && blogPost.getBody() != selectedBlogPost.getBody()){
            selectedBlogPost.setBody(blogPost.getBody());
        }

        return blogPostRepository.save(selectedBlogPost);
    }

    @Override
    public BlogPost addBlogPost(String callerSUB, BlogPost blogPost) {

        if (blogPost.getTitle() == null || blogPost.getTitle().isEmpty()){
            throw new ResourceNotFoundException("title", "Entered BlogPost");
        }
        if (blogPost.getBody() == null || blogPost.getBody().isEmpty()){
            throw new ResourceNotFoundException("body", "Entered BlogPost");
        }

        blogPost.setCallerSUB(callerSUB);

        System.out.println("Assignment request for printed caller SUB: " + callerSUB);

        return blogPostRepository.save(blogPost);
    }

    @Override
    public void deleteBlogPostById(String callerSUB, List callerRoles, Long id) {

        BlogPost selectedBlogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("blog post id", "blogPostRepository.findById(" + id +")"));

        if (callerRoles.contains("client1_ADMIN")){
            blogPostRepository.deleteById(id);
            return;
        }
       System.out.println("DONT WANNA SEEE");

        if (selectedBlogPost.getCallerSUB() != callerSUB){
            throw new PostOwnershipViolationException(callerSUB, selectedBlogPost.getId());
        }

        blogPostRepository.deleteById(id);
    }

    @Override
    public Long getBlogPostCount(){

        return blogPostRepository.count();

    }
}
