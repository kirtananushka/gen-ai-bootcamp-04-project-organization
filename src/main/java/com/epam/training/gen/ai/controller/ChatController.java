package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.model.ChatRequest;
import com.epam.training.gen.ai.model.ChatResponse;
import com.epam.training.gen.ai.semantic.kernel.KernelAgeCalculationService;
import com.epam.training.gen.ai.semantic.kernel.KernelBingSearchService;
import com.epam.training.gen.ai.semantic.kernel.KernelWikiSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatController {
    private final ObjectMapper objectMapper;
    private final KernelAgeCalculationService ageCalcService;
    private final KernelWikiSearchService wikiSearchService;
    private final KernelBingSearchService bingSearchService;

    @PostMapping("/calculate-age")
    public ResponseEntity<String> calculateAge(@Valid @RequestBody ChatRequest chatRequest) throws JsonProcessingException {
        String response = ageCalcService.getKernelFunctionalResponse(chatRequest.query());
        return new ResponseEntity<>(objectMapper.writeValueAsString(new ChatResponse(response)), HttpStatus.OK);
    }

    @PostMapping("/search-wiki")
    public ResponseEntity<String> searchWiki(@Valid @RequestBody ChatRequest chatRequest) throws JsonProcessingException {
        String response = wikiSearchService.getKernelFunctionalResponse(chatRequest.query());
        return new ResponseEntity<>(objectMapper.writeValueAsString(new ChatResponse(response)), HttpStatus.OK);
    }

    @PostMapping("/search-bing")
    public ResponseEntity<String> searchBing(@Valid @RequestBody ChatRequest chatRequest) throws JsonProcessingException {
        String response = bingSearchService.getKernelFunctionalResponse(chatRequest.query());
        return new ResponseEntity<>(objectMapper.writeValueAsString(new ChatResponse(response)), HttpStatus.OK);
    }
}
