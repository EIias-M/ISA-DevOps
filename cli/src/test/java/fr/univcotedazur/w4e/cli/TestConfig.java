package fr.univcotedazur.w4e.cli;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public CliContext cliContext() {
        return Mockito.mock(CliContext.class);
    }
}
