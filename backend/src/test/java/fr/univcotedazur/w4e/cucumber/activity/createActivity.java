package fr.univcotedazur.w4e.cucumber.activity;
import fr.univcotedazur.w4e.components.ActivityCreatorC;
import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.repositories.ActivityRepository;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
public class createActivity {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityCreatorC activityCreatorC;

    public static class TestContext {
        public static Activity latestActivity = null;
        public static Activity prelatestActivity = null;

        public static List<Activity> activities = null;
    }

    @When("I ask for an activity {string}")
    public void iAskForForAnActivity(String karting) {
        Activity notExistActivity = activityRepository.findBynameActivity(karting);
        TestContext.latestActivity = notExistActivity;
    }

    @Then("it does not exists")
    public void itDoesNotExis() {
        assertNull(TestContext.latestActivity);
    }

    @Given("an activity named {string} with price {int} and {int} places does not exist")
    public void anActivityNamedWithPriceAndPlacesDoesNotExist(String name, int price, int nbPlace) throws Throwable  {
        //ici on verifie que l'activité n'existe pas
        Activity existActivity = activityRepository.findBynameActivity(name);
        if (existActivity != null) {
            activityRepository.delete(existActivity);
        }

    }

    @When("I create a new activity named {string} with price {int} and {int} places")
    public void iCreateANewActivityNamedWithPriceAndPlaces(String name, int price, int nbPlace) {
        //Ici on créer donc une activité

        Activity paragliding = new Activity();
        paragliding.setId(123);
        paragliding.setNbSlot(nbPlace);
        paragliding.setPrice(price);
        paragliding.setnameActivity(name);

        try {
            Activity createdActivity = activityCreatorC.createActivityPackage(paragliding);
            TestContext.latestActivity = createdActivity;
        } catch (Exception e) {
            System.out.println("Error during the save of created activity : " + e);
        }
    }

    @Then("the activity {string} should be registered in the system")
    public void theActivityShouldBeRegisteredInTheSystem(String name) {
        assertNotNull(TestContext.latestActivity);
        Activity foundActivity = activityRepository.findBynameActivity(name);
        assertThat(foundActivity).isNotNull();
        assertThat(foundActivity.getnameActivity()).isEqualTo(name);
        activityRepository.delete(foundActivity);
    }

    @Given("{string} with price {int} and {int} places")
    public void withPriceAndPlaces(String climbing, int price, int slots) {
        Activity activity = new Activity(435,price,climbing,slots);
        Activity createdActivity = activityCreatorC.createActivityPackage(activity);
        TestContext.prelatestActivity = createdActivity;
        assertNotNull(createdActivity);
    }

    @And("{string} with price {int} and {int} places are already created")
    public void withPriceAndAndPlacesAreAlreadyCreated(String biking, int price, int slots) {
        Activity activity = new Activity(146,price,biking,slots);
        Activity createdActivity = activityCreatorC.createActivityPackage(activity);
        TestContext.latestActivity = createdActivity;
        assertNotNull(TestContext.latestActivity);
    }

    @When("I want to list all activities available")
    public void iWantToListAllActivitiesAvailable() {
        List<Activity> activityList = activityCreatorC.getAllActivites();
        assertNotNull(activityList);
        TestContext.activities=activityList;
    }

    @Then("the activities are shown")
    public void theActivitiesAreShown() {
        Activity activity = new Activity(435,25,"Climbing",20 );
        Activity activity2 = new Activity(146,10,"Biking",30);
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        activities.add(activity2);
        assertEquals(activities,TestContext.activities);
    }


}
