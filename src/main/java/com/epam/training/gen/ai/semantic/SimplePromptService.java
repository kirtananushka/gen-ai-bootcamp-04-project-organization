package com.epam.training.gen.ai.semantic;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.epam.training.gen.ai.config.OpenAIProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimplePromptService {

    public static final String GREETING_MESSAGE = "Say hi";

    private final OpenAIAsyncClient aiClientService;
    private final OpenAIProperties properties;

    public List<String> getChatCompletions() {

        ChatCompletions completions = aiClientService
                .getChatCompletions(
                        properties.getDeploymentName(),
                        new ChatCompletionsOptions(
                                List.of(new ChatRequestUserMessage(GREETING_MESSAGE))))
                .block();
        List<String> messages = completions.getChoices().stream()
                .map(c -> c.getMessage().getContent())
                .toList();
        log.info(messages.toString());
        return messages;
    }
}
