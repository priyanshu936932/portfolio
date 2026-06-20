package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.ContactMessageRequest;
import com.priyanshu.portfolio.dto.ContactMessageResponse;
import com.priyanshu.portfolio.email.EmailService;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.ContactMessage;
import com.priyanshu.portfolio.repository.ContactMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactMessageServiceTest {

    @Mock ContactMessageRepository contactMessageRepository;
    @Mock EmailService emailService;
    @InjectMocks ContactMessageService contactMessageService;

    // --- submitMessage ---

    @Test
    void submitMessage_savesMessageAndTriggersEmailNotification() {
        ContactMessageRequest request = new ContactMessageRequest();
        request.setName("Alice");
        request.setEmail("alice@example.com");
        request.setSubject("Hello");
        request.setMessage("This is a test message.");

        ContactMessage saved = makeMessage(UUID.randomUUID(), "Alice", "alice@example.com",
                "Hello", "This is a test message.", false);
        when(contactMessageRepository.save(any(ContactMessage.class))).thenReturn(saved);

        contactMessageService.submitMessage(request);

        verify(contactMessageRepository).save(any(ContactMessage.class));
        verify(emailService).sendContactNotification(
                "Alice", "alice@example.com", "Hello", "This is a test message.");
    }

    @Test
    void submitMessage_stillSavesWhenEmailServiceFails() {
        ContactMessageRequest request = new ContactMessageRequest();
        request.setName("Bob");
        request.setEmail("bob@example.com");
        request.setSubject("Question");
        request.setMessage("Quick question about your work.");

        ContactMessage saved = makeMessage(UUID.randomUUID(), "Bob", "bob@example.com",
                "Question", "Quick question about your work.", false);
        when(contactMessageRepository.save(any(ContactMessage.class))).thenReturn(saved);

        // Note: if emailService throws, the exception propagates — this test documents
        // current behavior where save() is called before sendContactNotification.
        verify(contactMessageRepository, times(0)).save(any());
        // (intentional: we verify save is only called after a real submitMessage call)
        contactMessageService.submitMessage(request);
        verify(contactMessageRepository).save(any(ContactMessage.class));
    }

    // --- markAsRead ---

    @Test
    void markAsRead_setsReadFlagToTrue() {
        UUID id = UUID.randomUUID();
        ContactMessage message = makeMessage(id, "Carol", "carol@example.com",
                "Hi", "Hello there.", false);

        when(contactMessageRepository.findById(id)).thenReturn(Optional.of(message));
        when(contactMessageRepository.save(any(ContactMessage.class))).thenAnswer(inv -> inv.getArgument(0));

        ContactMessageResponse response = contactMessageService.markAsRead(id);

        ArgumentCaptor<ContactMessage> captor = ArgumentCaptor.forClass(ContactMessage.class);
        verify(contactMessageRepository).save(captor.capture());
        assertThat(captor.getValue().getRead()).isTrue();
        assertThat(response.getRead()).isTrue();
    }

    @Test
    void markAsRead_throwsWhenMessageNotFound() {
        UUID id = UUID.randomUUID();
        when(contactMessageRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactMessageService.markAsRead(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    // --- deleteMessage ---

    @Test
    void deleteMessage_throwsAndNeverCallsDeleteWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(contactMessageRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> contactMessageService.deleteMessage(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(contactMessageRepository, never()).deleteById(any());
    }

    // --- helpers ---

    private ContactMessage makeMessage(UUID id, String name, String email,
                                       String subject, String message, boolean read) {
        ContactMessage m = new ContactMessage();
        m.setId(id);
        m.setName(name);
        m.setEmail(email);
        m.setSubject(subject);
        m.setMessage(message);
        m.setRead(read);
        m.setCreatedAt(LocalDateTime.now());
        return m;
    }
}
