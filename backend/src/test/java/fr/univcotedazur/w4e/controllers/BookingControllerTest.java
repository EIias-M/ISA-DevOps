package fr.univcotedazur.w4e.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.w4e.components.BookingManager;
import fr.univcotedazur.w4e.dto.BookingRequestDTO;
import fr.univcotedazur.w4e.dto.BookingResultDTO;
import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.entities.Booking;
import fr.univcotedazur.w4e.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingManager bookingManager;

    @Mock
    private Customer customer;

    @Mock
    private Activity activity;

    @InjectMocks
    private Booking booking;

    private BookingResultDTO bookingResultDTO;

    @BeforeEach
    void setUp() {
        // Setup mock behavior here
        customer = new Customer(1L, "John Doe", "1234567890", 100);
        activity = new Activity(1, 50,"Yoga", 20);
        booking = new Booking(1, customer,  LocalDate.now(),activity, "PAY123");

        bookingResultDTO = new BookingResultDTO(1, "John Doe", "Yoga", LocalDate.now(), 100, "PAY123");
    }

    @Test
    void createBookingAndPayment_ShouldReturnBookingResultDTO() throws Exception {


        when(bookingManager.createBookingPayement(any(Long.class), any(Long.class), any(Integer.class))).thenReturn(booking);
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO("1", "1", 1);

        given(bookingManager.createBookingPayement(1L, 1L, 1)).willReturn(booking);


        mockMvc.perform(post("/bookings/create/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookingResultDTO)));
    }
}
