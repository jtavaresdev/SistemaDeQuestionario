package org.example.repository;

import org.example.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
