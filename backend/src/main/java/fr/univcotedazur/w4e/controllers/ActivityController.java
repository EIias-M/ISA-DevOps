package fr.univcotedazur.w4e.controllers;

import fr.univcotedazur.w4e.components.ActivityCreatorC;
import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.exceptions.CustomerIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ActivityController {

    public static final String ACT_URI = "/Activity";
    private final ActivityCreatorC activityService;

    public ActivityController(ActivityCreatorC activityService) {
        this.activityService = activityService;
    }


    @PostMapping(path = ACT_URI+"/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Activity> CreateActivity(@RequestBody Activity activity) throws CustomerIdNotFoundException {

       try {

           var stat = ResponseEntity.status(HttpStatus.CREATED)
                   .body(activityService.createActivityPackage(activity));
           Activity activityCreated = activityService.findActiviteByName(activity.getnameActivity());
           ResponseEntity.ok().body(activityCreated);
           return stat;
       }
       catch(Exception e){   return ResponseEntity.status(HttpStatus.CONFLICT).build();}

      //  return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);


    }

    @GetMapping(path = ACT_URI, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Activity[]> listAllActivities() {
        List<Activity> activities = activityService.getAllActivites();
        Activity[] activityArray = activities.toArray(new Activity[0]);
        return ResponseEntity.status(HttpStatus.OK)
                .body(activityArray);
        
    }

}
