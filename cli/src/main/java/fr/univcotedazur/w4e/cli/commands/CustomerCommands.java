package fr.univcotedazur.w4e.cli.commands;

import fr.univcotedazur.w4e.cli.CliContext;
import fr.univcotedazur.w4e.cli.model.Booking;
import fr.univcotedazur.w4e.cli.model.CliCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@ShellComponent
public class CustomerCommands {

    public static final String BASE_URI = "/customers";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public CustomerCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a customer in the CoD backend (register CUSTOMER_NAME CREDIT_CARD_NUMBER)")
    public CliCustomer register(String name, String creditCard) {
        CliCustomer res = restTemplate.postForObject(BASE_URI, new CliCustomer(name, creditCard), CliCustomer.class);
        cliContext.getCustomers().put(Objects.requireNonNull(res).getName(), res);
        return res;
    }

    @ShellMethod("List all known customers")
    public void customers() {
        ResponseEntity<CliCustomer[]> response = restTemplate.getForEntity(BASE_URI, CliCustomer[].class);
        System.out.println("Voici la liste de tous les clients :");
        for (CliCustomer a: response.getBody()) {
            System.out.println(a);
        }
    }

    @ShellMethod("Update all known customers from server")
    public String updateCustomers() {
        Map<String, CliCustomer> customerMap = cliContext.getCustomers();
        customerMap.clear();
        customerMap.putAll(Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(BASE_URI, CliCustomer[].class)
                .getBody())).collect(toMap(CliCustomer::getName, Function.identity())));
        return customerMap.toString();
    }

}
