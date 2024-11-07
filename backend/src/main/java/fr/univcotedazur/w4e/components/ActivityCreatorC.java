package fr.univcotedazur.w4e.components;

import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.interfaces.IActivityCreator;
import fr.univcotedazur.w4e.repositories.ActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityCreatorC implements IActivityCreator {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityCreatorC.class);


    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityCreatorC(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }



    @Override
    public Activity createActivityPackage(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> getAllActivites() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findActiviteByName(String nom) {
        return activityRepository.findBynameActivity(nom);
    }
}
