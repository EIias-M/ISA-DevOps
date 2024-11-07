package fr.univcotedazur.w4e.connectors;

import fr.univcotedazur.w4e.connectors.externaldto.ValidateReceiptDTO;
import fr.univcotedazur.w4e.connectors.externaldto.ValidateRequestDTO;
import fr.univcotedazur.w4e.interfaces.Scheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class SchedulerProxy implements Scheduler {

    @Value("${scheduler.host.baseurl}")
    private String schedulerHostandPort;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<Boolean> validate(LocalDate date) {
        try {
            ResponseEntity<ValidateReceiptDTO> result = restTemplate.postForEntity(
                    schedulerHostandPort + "/apischeduling",
                    new ValidateRequestDTO(date.toString()),
                    ValidateReceiptDTO.class
            );
            if (result.getStatusCode().equals(HttpStatus.CREATED) && result.hasBody()) {
                return Optional.of(result.getBody().validation());
            } else {
                return Optional.empty();
            }
        }
        catch (RestClientResponseException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return Optional.empty();
            }
            throw errorException;
        }
    }
}
