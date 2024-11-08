package fr.univcotedazur.w4e.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.w4e.dto.CustomerDTO;
import fr.univcotedazur.w4e.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Full stack (No class-wide @Transactional here as the controller layer should not be transactional)
class CustomerCareWebValidationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @Transactional
    void setUpContext() throws Exception {
        customerRepository.deleteAll();
    }

    @Test
    void validCustomerTest() throws Exception {
        CustomerDTO validCustomer = new CustomerDTO(null, "john", "1234567890",0);
        mockMvc.perform(MockMvcRequestBuilders.post(CustomerCareController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCustomer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void inValidCustomerTest() throws Exception {
        CustomerDTO badCreditCardCustomer = new CustomerDTO(null, "badCreditCard", "123",0);
        mockMvc.perform(MockMvcRequestBuilders.post(CustomerCareController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badCreditCardCustomer)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

}



