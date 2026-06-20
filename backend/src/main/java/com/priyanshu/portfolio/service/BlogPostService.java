package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.BlogPostRequest;
import com.priyanshu.portfolio.dto.BlogPostResponse;
import com.priyanshu.portfolio.dto.TagResponse;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.BlogPost;
import com.priyanshu.portfolio.model.Tag;
import com.priyanshu.portfolio.repository.BlogPostRepository;
import com.priyanshu.portfolio.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final TagRepository tagRepository;

    public List<BlogPostResponse> getPublishedPosts() {
        return blogPostRepository.findByPublishedTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<BlogPostResponse> getAllPosts() {
        return blogPostRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BlogPostResponse getPostBySlug(String slug) {
        BlogPost post = blogPostRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found with slug: " + slug));
        return toResponse(post);
    }

    public BlogPostResponse createPost(BlogPostRequest request) {
        BlogPost post = new BlogPost();
        applyRequest(post, request);
        BlogPost saved = blogPostRepository.save(post);
        return toResponse(saved);
    }

    public BlogPostResponse updatePost(UUID id, BlogPostRequest request) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found with id: " + id));
        applyRequest(post, request);
        BlogPost updated = blogPostRepository.save(post);
        return toResponse(updated);
    }

    public void deletePost(UUID id) {
        if (!blogPostRepository.existsById(id)) {
            throw new ResourceNotFoundException("Blog post not found with id: " + id);
        }
        blogPostRepository.deleteById(id);
    }

    private void applyRequest(BlogPost post, BlogPostRequest request) {
        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setSummary(request.getSummary());
        post.setContent(request.getContent());
        post.setCoverImageUrl(request.getCoverImageUrl());

        boolean wasPublished = Boolean.TRUE.equals(post.getPublished());
        boolean nowPublished = request.getPublished() != null && request.getPublished();

        post.setPublished(nowPublished);

        if (!wasPublished && nowPublished) {
            post.setPublishedAt(LocalDateTime.now());
        }

        if (request.getTagIds() != null) {
            Set<Tag> tags = request.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId)))
                    .collect(Collectors.toSet());
            post.setTags(tags);
        }
    }

    private BlogPostResponse toResponse(BlogPost post) {
        Set<TagResponse> tagResponses = post.getTags().stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                .collect(Collectors.toSet());

        return new BlogPostResponse(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getSummary(),
                post.getContent(),
                post.getCoverImageUrl(),
                post.getPublished(),
                post.getPublishedAt(),
                post.getCreatedAt(),
                tagResponses
        );
    }
}