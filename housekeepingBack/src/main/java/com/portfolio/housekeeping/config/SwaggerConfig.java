package com.portfolio.housekeeping.config;

import com.portfolio.housekeeping.resource.exceptions.ResourceExceptionHandler;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        Contact contact = new Contact();
        contact.setName("House Keeper");
        contact.setUrl("none");
        contact.setEmail("padilhaklaus@gmail.com");

        return new OpenAPI().
                info(new Info().title("House Keeper")
                        .description("Documentação dos endpoints")
                        .version("v1")
                        .contact(contact))
                .components(new Components().schemas(generateSchemas()));
    }

    private Map<String, Schema> generateSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();
        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(ResourceExceptionHandler.class);
        schemaMap.putAll(schemaMap);
        return schemaMap;
    }

}
