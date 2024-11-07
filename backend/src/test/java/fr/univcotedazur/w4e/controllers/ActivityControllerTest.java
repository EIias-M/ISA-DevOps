package fr.univcotedazur.w4e.controllers;

import fr.univcotedazur.w4e.components.ActivityCreatorC;
import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityCreatorC activityCreatorC;

    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    @Transactional
    void setUpContext() throws Exception {
        activityRepository.deleteAll();
    }

    @Test
    public void activityCreatedAfterPost() throws Exception {
        Activity activity = new Activity();
        activity.setnameActivity("Paragliding");
        activity.setPrice(50);
        activity.setNbSlot(15);
        given(activityCreatorC.createActivityPackage(any(Activity.class))).willReturn(activity);

        mockMvc.perform(post("/Activity/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nameActivity\":\"Paragliding\",\"price\":50,\"nbSlot\":15}"))
                .andExpect(status().isCreated());
    }

}
