package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.ContactMessageRequest;
import com.priyanshu.portfolio.dto.ContactMessageResponse;
import com.priyanshu.portfolio.email.EmailService;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.ContactMessage;
import com.priyanshu.portfolio.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
     private final EmailService emailService;

    public ContactMessageResponse submitMessage(ContactMessageRequest request) {
        ContactMessage message = new ContactMessage();
        message.setName(request.getName());
        message.setEmail(request.getEmail());
        message.setSubject(request.getSubject());
        message.setMessage(request.getMessage());

        ContactMessage saved = contactMessageRepository.save(message);

        try {
            emailService.sendContactNotification(
                    request.getName(),
                    request.getEmail(),
                    request.getSubject(),
                    request.getMessage()
            );
        } catch (Exception e) {
            log.error("Email notification failed (message was saved): {}", e.getMessage());
        }

        return toResponse(saved);
    }
    
    public List<ContactMessageResponse> getAllMessages() {
        return contactMessageRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ContactMessage::getCreatedAt).reversed())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ContactMessageResponse markAsRead(UUID id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
        message.setRead(true);
        ContactMessage updated = contactMessageRepository.save(message);
        return toResponse(updated);
    }

    public void deleteMessage(UUID id) {
        if (!contactMessageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
        contactMessageRepository.deleteById(id);
    }

    private ContactMessageResponse toResponse(ContactMessage message) {
        return new ContactMessageResponse(
                message.getId(),
                message.getName(),
                message.getEmail(),
                message.getSubject(),
                message.getMessage(),
                message.getRead(),
                message.getCreatedAt()
        );
    }
}