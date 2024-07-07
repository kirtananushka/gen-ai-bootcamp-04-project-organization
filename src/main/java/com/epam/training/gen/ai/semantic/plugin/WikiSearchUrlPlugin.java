package com.epam.training.gen.ai.semantic.plugin;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikiSearchUrlPlugin {

    public static final String WIKIPEDIA_SEARCH_URL_PLUGIN = "getWikipediaSearchUrl";
    public static final String WIKIPEDIA_URL_TEMPLATE = "https://wikipedia.org/w/index.php?search=%s";

    @DefineKernelFunction(name = WIKIPEDIA_SEARCH_URL_PLUGIN, description = "Return URL for Wikipedia search query.")
    public String getWikipediaSearchUrl(
            @KernelFunctionParameter(description = "Text to search for", name = "query") String query) {
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return String.format(WIKIPEDIA_URL_TEMPLATE, encoded);
    }
}