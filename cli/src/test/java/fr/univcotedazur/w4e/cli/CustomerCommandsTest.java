package fr.univcotedazur.w4e.cli;

import fr.univcotedazur.w4e.cli.commands.ActivityCommands;
import fr.univcotedazur.w4e.cli.commands.CustomerCommands;
import fr.univcotedazur.w4e.cli.model.Activity;
import fr.univcotedazur.w4e.cli.model.CliCustomer;
import org.hibernate.validator.cfg.defs.EANDef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import static fr.univcotedazur.w4e.cli.commands.CustomerCommands.BASE_URI;

@RestClientTest(CustomerCommands.class)
@Import(TestConfig.class)
public class CustomerCommandsTest {
    @Autowired
    private CustomerCommands customerCommands;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void testListCustomers(){
        String responseBody = "[{\"id\":1,\"name\":\"Alice\",\"creditCard\":\"1234567890\",\"pointfidele\":100},"
                + "{\"id\":2,\"name\":\"Bob\",\"creditCard\":\"0987654321\",\"pointfidele\":200}]";
        server.expect(requestTo(BASE_URI))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        customerCommands.customers();

        String expectedOutput = "Voici la liste de tous les clients :\n"
                + "CliCustomer{id=1, name='Alice', creditCard='1234567890', pointfidele=100, bookings=[]}\n"
                + "CliCustomer{id=2, name='Bob', creditCard='0987654321', pointfidele=200, bookings=[]}";

        String actualOutput = outContent.toString();

        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n");
        String normalizedActualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(normalizedExpectedOutput.trim(), normalizedActualOutput.trim());

        System.setOut(System.out);
    }

    @Test
    public void testRegisterCustomer() {
        String responseBody = "{\"id\":3,\"name\":\"Charlie\",\"creditCard\":\"1122334455\",\"pointfidele\":0}";
        server.expect(requestTo(BASE_URI))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CliCustomer newCustomer = customerCommands.register("Charlie", "1122334455");

        String expectedOutput = "Nouveau client enregistré :\nCliCustomer{id=3, name='Charlie', creditCard='1122334455', pointfidele=0, bookings=[]}";

        System.out.println("Nouveau client enregistré :");
        System.out.println(newCustomer);
        String actualOutput = outContent.toString();
        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n");
        String normalizedActualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(normalizedExpectedOutput.trim(), normalizedActualOutput.trim());

        System.setOut(System.out);
    }
    @Test
    public void testUpdateCustomers() {
        String responseBody = "[{\"id\":1,\"name\":\"Alice\",\"creditCard\":\"1234567890\",\"pointfidele\":100},"
                + "{\"id\":2,\"name\":\"Bob\",\"creditCard\":\"0987654321\",\"pointfidele\":200}]";
        server.expect(requestTo(BASE_URI))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String updateResult = customerCommands.updateCustomers();

        String expectedOutput = "{Bob=CliCustomer{id=2, name='Bob', creditCard='0987654321', pointfidele=200, bookings=[]},"
                + " Alice=CliCustomer{id=1, name='Alice', creditCard='1234567890', pointfidele=100, bookings=[]}}";

        assertEquals(expectedOutput, updateResult);

        System.setOut(System.out);
    }



}

