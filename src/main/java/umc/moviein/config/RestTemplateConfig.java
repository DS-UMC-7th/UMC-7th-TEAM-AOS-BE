package umc.moviein.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List converters = new ArrayList<>(restTemplate.getMessageConverters());
        converters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setMessageConverters(converters);

        return restTemplate;
    }
}
