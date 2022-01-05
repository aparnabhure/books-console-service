package com.example.aparna.booksconsoleservice;

import com.example.aparna.booksconsoleservice.config.DataStaxAstraConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Path;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraConfig.class)
public class BooksConsoleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksConsoleServiceApplication.class, args);
	}

	/** The CqlSession bean is not able to connect when creating it via another package or java @Configuration class, it started working when moved to here */
	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraConfig dataStaxAstraConfig){
		Path bundle = dataStaxAstraConfig.getSecureConnectBundle().toPath();
		return builder->builder.withCloudSecureConnectBundle(bundle);
	}

	@Bean
	WebClient webClient() {
		return WebClient.builder()
			.baseUrl("http://openlibrary.org/search.json")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer ->
					clientCodecConfigurer.defaultCodecs()
						.maxInMemorySize(16*1024*1024))
				.build())
			.build();
	}
}
