package fr.univcotedazur.w4e.repositories;

import fr.univcotedazur.w4e.entities.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;


    @Test
    public void testIdGenerationAndUnicity(){
        Activity activity = new Activity(1345,10,"hiking",30);
        Assertions.assertNotNull(activity.getId());
        activityRepository.saveAndFlush(activity);
        Assertions.assertNotNull(activity.getId());

        Assertions.assertThrows(DataIntegrityViolationException.class,() -> activityRepository.saveAndFlush(new Activity(1345,10,"hiking",30)));
        Assertions.assertThrows(DataIntegrityViolationException.class,() -> activityRepository.saveAndFlush(new Activity(1346,10,"hiking",30)));

    }

    @Test
    public void testFindActivityByName(){
        Activity activity = new Activity(1345,10,"hiking",30);
        activityRepository.saveAndFlush(activity);
        Assertions.assertEquals(activityRepository.findBynameActivity("hiking"),activity);

    }

    @Test
    public void testFindAllActivities(){
        Activity hiking = new Activity(1345,10,"hiking",30);
        Activity swimming = new Activity();
        swimming.setnameActivity("Swimming");
        swimming.setPrice(15);
        swimming.setNbSlot(25);

        activityRepository.saveAndFlush(hiking);
        activityRepository.saveAndFlush(swimming);

        List<Activity> activities = activityRepository.findAll();
        List<Activity> activities1 = new ArrayList<>();
        activities1.add(hiking);
        activities1.add(swimming);


        assertThat(activities).hasSize(2);
        Assertions.assertEquals(activities,activities1);
    }


}
