package fr.univcotedazur.w4e.repositories;

import fr.univcotedazur.w4e.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository  extends JpaRepository<Activity,Long> {
    Activity findBynameActivity(String nom);
    @Override
    List<Activity> findAll();
}
