package io.github.yilengyao.openai.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.yilengyao.openai.client.OpenAiClient;
import io.github.yilengyao.openai.client.OpenAiClientImpl;

/**
 * Configuration class for OpenAI client.
 * <p>
 * This configuration is responsible for setting up the WebClient instances used
 * to interact with the OpenAI API.
 * </p>
 */
@Configuration
public class ClientConfiguration {
  private static final String OPENAI_BASE_URI = "https://api.openai.com";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String CONTENT_TYPE_VALUE = "application/json";
  private static final String AUTHORIZATION_HEADER = "Authorization";

  /**
   * Provides an instance of the OpenAI client.
   * 
   * @param openAiApiKey The API key for OpenAI from environment variables.
   * @return An instance of the OpenAI client.
   */
  @Bean
  public OpenAiClient getOpenAiClient(@Value("${OPENAI_API_KEY}") String openAiApiKey) {
    final int size = 100 * 1024 * 1024;
    final String authorizationValue = "Bearer " + openAiApiKey;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();

    WebClient openAiWebClient = WebClient.builder()
        .baseUrl(OPENAI_BASE_URI)
        .defaultHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
        .defaultHeader(AUTHORIZATION_HEADER, authorizationValue)
        .build();
    WebClient largerBufferOpenAiWebClient = WebClient.builder()
        .exchangeStrategies(strategies)
        .baseUrl(OPENAI_BASE_URI)
        .defaultHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
        .defaultHeader(AUTHORIZATION_HEADER, authorizationValue)
        .build();

    return new OpenAiClientImpl(openAiWebClient, largerBufferOpenAiWebClient);
  }

}
