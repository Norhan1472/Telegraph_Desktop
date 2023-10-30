package tgh.desktop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Clob;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.module.SimpleModule;


@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(Clob.class, new ClobSerializer());
        objectMapper.registerModule(module);
        
        return objectMapper;
    }
}


