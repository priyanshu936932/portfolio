package com.priyanshu.portfolio.controller;

import com.priyanshu.portfolio.dto.ContactMessageRequest;
import com.priyanshu.portfolio.dto.ContactMessageResponse;
import com.priyanshu.portfolio.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<ContactMessageResponse> submitMessage(@Valid @RequestBody ContactMessageRequest request) {
        ContactMessageResponse created = contactMessageService.submitMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ContactMessageResponse>> getAllMessages() {
        return ResponseEntity.ok(contactMessageService.getAllMessages());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ContactMessageResponse> markAsRead(@PathVariable UUID id) {
        return ResponseEntity.ok(contactMessageService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID id) {
        contactMessageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}