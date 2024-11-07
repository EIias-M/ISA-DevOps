package fr.univcotedazur.w4e.interfaces;

import fr.univcotedazur.w4e.entities.Activity;

import java.util.List;

public interface IActivityCreator {

    Activity createActivityPackage(Activity activity);

    List<Activity> getAllActivites();
    Activity findActiviteByName(String nom);
}
