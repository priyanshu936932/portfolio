package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.BlogPostRequest;
import com.priyanshu.portfolio.dto.BlogPostResponse;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.BlogPost;
import com.priyanshu.portfolio.model.Tag;
import com.priyanshu.portfolio.repository.BlogPostRepository;
import com.priyanshu.portfolio.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock BlogPostRepository blogPostRepository;
    @Mock TagRepository tagRepository;
    @InjectMocks BlogPostService blogPostService;

    // --- publishedAt once-only rule ---

    @Test
    void createPost_setsPublishedAtOnFirstPublish() {
        BlogPostRequest request = buildRequest("Test", "test", true);
        when(blogPostRepository.save(any(BlogPost.class))).thenAnswer(inv -> inv.getArgument(0));

        blogPostService.createPost(request);

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(captor.capture());
        assertThat(captor.getValue().getPublishedAt()).isNotNull();
    }

    @Test
    void createPost_doesNotSetPublishedAtWhenSavedAsDraft() {
        BlogPostRequest request = buildRequest("Draft", "draft", false);
        when(blogPostRepository.save(any(BlogPost.class))).thenAnswer(inv -> inv.getArgument(0));

        blogPostService.createPost(request);

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(captor.capture());
        assertThat(captor.getValue().getPublishedAt()).isNull();
    }

    @Test
    void updatePost_doesNotOverwritePublishedAtWhenAlreadyPublished() {
        UUID id = UUID.randomUUID();
        LocalDateTime originalPublishedAt = LocalDateTime.of(2024, 1, 1, 12, 0);

        BlogPost existing = new BlogPost();
        existing.setId(id);
        existing.setPublished(true);
        existing.setPublishedAt(originalPublishedAt);
        existing.setTags(new HashSet<>());

        BlogPostRequest request = buildRequest("Updated", "updated", true);

        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existing));
        when(blogPostRepository.save(any(BlogPost.class))).thenAnswer(inv -> inv.getArgument(0));

        blogPostService.updatePost(id, request);

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(captor.capture());
        assertThat(captor.getValue().getPublishedAt()).isEqualTo(originalPublishedAt);
    }

    @Test
    void updatePost_setsPublishedAtWhenDraftIsFirstPublished() {
        UUID id = UUID.randomUUID();

        BlogPost existing = new BlogPost();
        existing.setId(id);
        existing.setPublished(false);
        existing.setPublishedAt(null);
        existing.setTags(new HashSet<>());

        BlogPostRequest request = buildRequest("Now Live", "now-live", true);

        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existing));
        when(blogPostRepository.save(any(BlogPost.class))).thenAnswer(inv -> inv.getArgument(0));

        blogPostService.updatePost(id, request);

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(captor.capture());
        assertThat(captor.getValue().getPublishedAt()).isNotNull();
    }

    // --- tag many-to-many linking ---

    @Test
    void createPost_withTagIds_linksTagsCorrectly() {
        UUID tagId1 = UUID.randomUUID();
        UUID tagId2 = UUID.randomUUID();

        Tag tag1 = new Tag(); tag1.setId(tagId1); tag1.setName("Java");
        Tag tag2 = new Tag(); tag2.setId(tagId2); tag2.setName("Spring");

        BlogPostRequest request = buildRequest("Tagged", "tagged", false);
        request.setTagIds(Set.of(tagId1, tagId2));

        when(tagRepository.findById(tagId1)).thenReturn(Optional.of(tag1));
        when(tagRepository.findById(tagId2)).thenReturn(Optional.of(tag2));
        when(blogPostRepository.save(any(BlogPost.class))).thenAnswer(inv -> inv.getArgument(0));

        blogPostService.createPost(request);

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(captor.capture());
        assertThat(captor.getValue().getTags()).hasSize(2);
        assertThat(captor.getValue().getTags()).extracting(Tag::getName)
                .containsExactlyInAnyOrder("Java", "Spring");
    }

    @Test
    void createPost_withUnknownTagId_throwsResourceNotFoundException() {
        UUID badTagId = UUID.randomUUID();
        BlogPostRequest request = buildRequest("Bad Tag", "bad-tag", false);
        request.setTagIds(Set.of(badTagId));

        when(tagRepository.findById(badTagId)).thenReturn(Optional.empty());
        when(blogPostRepository.save(any(BlogPost.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThatThrownBy(() -> blogPostService.createPost(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // --- error paths ---

    @Test
    void getPostBySlug_throwsWhenNotFound() {
        when(blogPostRepository.findBySlug("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> blogPostService.getPostBySlug("missing"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void deletePost_throwsAndNeverCallsDeleteWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(blogPostRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> blogPostService.deletePost(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(blogPostRepository, never()).deleteById(any());
    }

    // --- helpers ---

    private BlogPostRequest buildRequest(String title, String slug, boolean published) {
        BlogPostRequest r = new BlogPostRequest();
        r.setTitle(title);
        r.setSlug(slug);
        r.setContent("Some content");
        r.setPublished(published);
        return r;
    }
}
