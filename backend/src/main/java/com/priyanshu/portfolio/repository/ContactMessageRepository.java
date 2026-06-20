package com.priyanshu.portfolio.repository;

import com.priyanshu.portfolio.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, UUID> {
}