package com.ai.gemini_chat.controller;

import com.ai.gemini_chat.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/qna")
@Slf4j
@RequiredArgsConstructor
public class AiController {

    private final QnaService qnaService;

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> payload) {
        String question = payload.get("question");

        if (question == null || question.trim().isEmpty()) {
            log.warn("Received empty or null question.");
            return ResponseEntity.badRequest().body("Question cannot be null or empty.");
        }

        try {
            String answer = qnaService.getAnswer(question);
            log.info("Answer retrieved successfully for question: {}", question);
            return ResponseEntity.ok(answer);
        } catch (Exception e) {
            log.error("Error occurred while processing the question: {}", question, e);
            return ResponseEntity.status(500).body("An error occurred while processing your request.");
        }
    }
}
