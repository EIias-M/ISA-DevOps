package fr.univcotedazur.w4e.cucumber.activity;

import fr.univcotedazur.w4e.connectors.BankProxy;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest
public class ActivityCucumberConfig {
    @MockBean
    private BankProxy bankProxyMock;
}
