package fr.univcotedazur.w4e.cli;

import fr.univcotedazur.w4e.cli.CliContext;
import fr.univcotedazur.w4e.cli.commands.BookingCommands;
import fr.univcotedazur.w4e.cli.model.Activity;
import fr.univcotedazur.w4e.cli.model.CliCustomer;
import fr.univcotedazur.w4e.cli.model.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static fr.univcotedazur.w4e.cli.commands.BookingCommands.BASE_URI;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(BookingCommands.class)
@Import(CliContext.class)
public class BookingCommandsTest {

    @Autowired
    private BookingCommands bookingCommands;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private CliContext cliContext;

    @Test
    public void testListBookings() {

        String responseBody = "[{\"id\":1,\"customer\":\"Alice\",\"dateReservation\":\"2023-01-01\",\"activity\":\"Hiking\",\"paymentReceiptId\":\"abc123\", \"fidelityPoint\":100}]";
        server.expect(requestTo(BASE_URI))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        bookingCommands.listBookings();

        String expectedOutput = "Voici la liste de tous les bookings :\n" +
                "Booking{id=1, customer=Alice, dateReservation=2023-01-01, activity=Hiking, paymentReceiptId=abc123, pointfidelité=100}\n";

        System.setOut(System.out);
        String actualOutput = outContent.toString();

        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n");
        String normalizedActualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(normalizedExpectedOutput.trim(), normalizedActualOutput.trim());
    }

    @Test
    public void testBookActivity() {
        String activityName = "Hiking";
        String customerName = "Alice";
        long customerId = 4L;
        long activityId = 3L;
        Activity hikingActivity = new Activity(activityId, 30, activityName, 20);
        CliCustomer aliceCustomer = new CliCustomer(customerId, customerName, "33333333", 100);
        cliContext.getActivities().put("Hiking", hikingActivity);
        cliContext.getCustomers().put("Alice", aliceCustomer);
        String responseBody = "{\"id\":1,\"customer\":\"Alice\",\"dateReservation\":\"2023-01-01\",\"activity\":\"Hiking\",\"paymentReceiptId\":\"abc123\",\"fidelityPoint\":100}";
        server.expect(requestTo(BASE_URI + "/create/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        Booking result = bookingCommands.bookActivity("Hiking", "Alice");

        assertNotNull(result, "The result should not be null.");
        assertEquals(1L, result.getId().longValue(), "The booking ID should match");
        assertEquals(customerName, result.getCustomer(), "The customer name should match");
        assertEquals("Hiking", result.getActivity(), "The activity name should match");
        assertEquals(LocalDate.of(2023, 1, 1), result.getDateReservation(), "The reservation date should match");
        assertEquals("abc123", result.getPaymentReceiptId(), "The payment receipt ID should match");
        assertEquals(100, result.getFidelityPoint(), "The fidelity points should match");

    }
        @Test
        public void testBookGroupActivity() {
            long customerIdAlice = 4L;
            long customerIdBob = 3L;
            long activityId = 3L;
            String activityName = "Hiking";
            String customerNameAlice = "Alice";
            String customerNameBob = "Bob";

            Activity hikingActivity = new Activity(activityId,30,activityName,30);
            CliCustomer aliceCustomer = new CliCustomer(customerIdAlice,customerNameAlice,"33333333",100);
            CliCustomer bobCustomer = new CliCustomer(customerIdBob,customerNameBob,"4444444",0);

            cliContext.getActivities().put("Hiking", hikingActivity);
            cliContext.getCustomers().put("Alice", aliceCustomer);
            cliContext.getCustomers().put("Bob", bobCustomer);
            String responseBody1 = "{\"id\":1,\"customer\":\"Alice\",\"dateReservation\":\"2023-01-01\",\"activity\":\"Hiking\",\"paymentReceiptId\":\"abc123\",\"fidelityPoint\":100}";
            String responseBody2 = "{\"id\":2,\"customer\":\"Bob\",\"dateReservation\":\"2023-01-01\",\"activity\":\"Hiking\",\"paymentReceiptId\":\"def456\",\"fidelityPoint\":150}";
            server.expect(requestTo(BASE_URI + "/create/"))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withSuccess(responseBody1, MediaType.APPLICATION_JSON));

            server.expect(requestTo(BASE_URI + "/create/"))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withSuccess(responseBody2, MediaType.APPLICATION_JSON));

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            List<Booking> bookings = bookingCommands.bookGroupActivity("Hiking", "Alice,Bob");
            System.setOut(System.out);

            assertEquals(2, bookings.size());

            assertEquals(100, cliContext.getCustomers().get("Alice").getPointfidele());
            assertEquals(150, cliContext.getCustomers().get("Bob").getPointfidele());



        }
}








