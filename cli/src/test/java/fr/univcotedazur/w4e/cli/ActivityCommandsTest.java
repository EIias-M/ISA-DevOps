package fr.univcotedazur.w4e.cli;

import fr.univcotedazur.w4e.cli.commands.ActivityCommands;
import fr.univcotedazur.w4e.cli.model.Activity;
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

import static fr.univcotedazur.w4e.cli.commands.ActivityCommands.BASE_URI;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ActivityCommands.class)
@Import(TestConfig.class)
public class ActivityCommandsTest {

    @Autowired
    private ActivityCommands activityCommands;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void testListActivities() {

        String responseBody = "[{\"id\":167,\"price\":150.0,\"nameActivity\":\"Hiking\",\"nbSlot\":20},{\"id\":155,\"price\":200,\"nameActivity\":\"Diving\",\"nbSlot\":10}]";
        server.expect(requestTo(BASE_URI))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        activityCommands.listActivity();

        String expectedOutput = "Voici la liste de toutes les activités :\n" +
                "Activity{id=167, Prix=150.0, nomActivite='Hiking', nbPlaces=20}\n" +
                "Activity{id=155, Prix=200.0, nomActivite='Diving', nbPlaces=10}";

        String actualOutput = outContent.toString();

        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n");
        String normalizedActualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(normalizedExpectedOutput.trim(), normalizedActualOutput.trim());

    }

    @Test
    public void testCreateActivity() {

        String responseBody = "{\"id\":168,\"price\":250.0,\"nameActivity\":\"Skydiving\",\"nbSlot\":5}";
        server.expect(requestTo(BASE_URI + "/create"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        Activity result = activityCommands.createActivity("Skydiving", 5, 250);

        Activity expected = new Activity(250, "Skydiving", 5);
        expected.setId(168L);

        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getnameActivity(), result.getnameActivity());
        assertEquals(expected.getPrice(), result.getPrice());
        assertEquals(expected.getNbSlot(), result.getNbSlot());
    }


}


