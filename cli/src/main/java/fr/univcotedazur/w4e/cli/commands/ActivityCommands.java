package fr.univcotedazur.w4e.cli.commands;

import fr.univcotedazur.w4e.cli.CliContext;
import fr.univcotedazur.w4e.cli.model.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@ShellComponent
public class ActivityCommands {
    public static final String BASE_URI = "/Activity";

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(Activity.class);


    private final CliContext cliContext;

    @Autowired

    public ActivityCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("create of Activity (create NAME NBPLACES PRIX) ")
    public Activity createActivity(String name, int nbPlaces, float prix) {
        Activity test = new Activity(prix, name, nbPlaces);
        Activity res = restTemplate.postForObject(BASE_URI + "/create", test, Activity.class);
        if (res != null) {
            cliContext.getActivities().put(Objects.requireNonNull(res).getnameActivity(), res);

            cliContext.getActivities().put(res.getnameActivity(), res);
        } else {
            logger.error("Failed to create activity. Response is null.");
        }
        return res;
    }


    @ShellMethod("list Activity ")
    public void listActivity() {
        ResponseEntity<Activity[]> response = restTemplate.getForEntity(BASE_URI, Activity[].class);
        System.out.println("Voici la liste de toutes les activités :");
        for (Activity a: response.getBody()) {
            System.out.println(a);
        }
    }
}

