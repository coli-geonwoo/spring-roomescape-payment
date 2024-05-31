package roomescape.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import roomescape.config.objectmapper.CustomLocalDateDeserializer;
import roomescape.config.objectmapper.CustomLocalDateSerializer;
import roomescape.config.objectmapper.CustomLocalTimeDeserializer;
import roomescape.config.objectmapper.CustomLocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.ApiExceptionHandler;

@Configuration
public class SpringConfig {

    @Bean
    ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new SimpleModule().addDeserializer(LocalDate.class, new CustomLocalDateDeserializer()))
                .addModule(new SimpleModule().addDeserializer(LocalTime.class, new CustomLocalTimeDeserializer()))
                .addModule(new SimpleModule().addSerializer(LocalDate.class, new CustomLocalDateSerializer()))
                .addModule(new SimpleModule().addSerializer(LocalTime.class, new CustomLocalTimeSerializer()))
                .build();
    }

    @Bean
    SimpleClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(1000);
        factory.setConnectTimeout(3000);
        return factory;
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .requestFactory(this::requestFactory)
                .errorHandler(new ApiExceptionHandler())
                .build();
    }
}
