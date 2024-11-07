package fr.univcotedazur.w4e.components;

import fr.univcotedazur.w4e.controllers.ActivityController;
import fr.univcotedazur.w4e.entities.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(ActivityController.class)
public class ActivityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityCreatorC activityService;

    @Test
    public void testCreateActivity() throws Exception {
        Activity activity = new Activity();
        activity.setId(1);
        activity.setnameActivity("Example Activity");
        activity.setPrice(50.0f);
        activity.setNbSlot(10);

        when(activityService.createActivityPackage(activity)).thenReturn(activity);

        mockMvc.perform(MockMvcRequestBuilders.post("/Activity/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"nomActivite\": \"Station de Ski\", \"prix\": 50.0, \"nbPlaces\": 10 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testListAllActivities() throws Exception {
        Activity activity = new Activity();
        activity.setId(1);
        activity.setnameActivity("Example Activity");
        activity.setPrice(50.0f);
        activity.setNbSlot(10);

        when(activityService.getAllActivites()).thenReturn(Collections.singletonList(activity));

        mockMvc.perform(MockMvcRequestBuilders.get("/Activity"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nameActivity").value("Example Activity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(50.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nbSlot").value(10));
    }
}
