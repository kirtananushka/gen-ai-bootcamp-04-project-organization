package com.epam.training.gen.ai.semantic.kernel;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.config.OpenAIProperties;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.FunctionResult;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractKernelService {
    private final OpenAIAsyncClient openAIAsyncClient;
    private final OpenAIProperties properties;

    protected abstract KernelPlugin getPlugin();

    protected abstract String getSystemPrompt();

    protected abstract String getPrompt();

    protected abstract String getPluginName();

    protected abstract String getTemplate();

    public String getKernelFunctionalResponse(String message) {
        ChatCompletionService chatCompletionService = OpenAIChatCompletion.builder()
                .withModelId(properties.getDeploymentName())
                .withOpenAIAsyncClient(openAIAsyncClient)
                .build();

        KernelPlugin plugin = getPlugin();

        Kernel kernel = Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .withPlugin(plugin)
                .build();

        KernelFunction<String> getIntent = KernelFunction.<String>createFromPrompt(getPrompt())
                .withTemplateFormat("handlebars")
                .build();

        KernelFunctionArguments arguments = KernelFunctionArguments.builder()
                .withVariable("system", getSystemPrompt())
                .withVariable("request", message)
                .build();

        PromptExecutionSettings promptExecutionSettings = PromptExecutionSettings.builder()
                .withTemperature(0.2)
                .build();

        FunctionResult<String> block = kernel
                .invokeAsync(getIntent)
                .withPromptExecutionSettings(promptExecutionSettings)
                .withArguments(arguments)
                .withToolCallBehavior(ToolCallBehavior.allowOnlyKernelFunctions(true, plugin.get(getPluginName())))
                .block();

        return block.getResult();
    }
}