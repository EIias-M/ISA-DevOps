package fr.univcotedazur.w4e.connectors;

import fr.univcotedazur.w4e.connectors.externaldto.PaymentReceiptDTO;
import fr.univcotedazur.w4e.connectors.externaldto.PaymentRequestDTO;
import fr.univcotedazur.w4e.entities.Customer;
import fr.univcotedazur.w4e.interfaces.Bank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.util.logging.Logger;
import fr.univcotedazur.w4e.MyLog;

import java.util.Optional;

@Component
public class BankProxy implements Bank {

    @Value("${bank.host.baseurl}")
    private String bankHostandPort;

    private final RestTemplate restTemplate = new RestTemplate();

    Logger logger;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<String> pay(Customer customer, double value) {
       // logger.info("Attempting to process payment of amount: " + value);
        try {
            ResponseEntity<PaymentReceiptDTO> result = restTemplate.postForEntity(
                    bankHostandPort + "/cctransactions",
                    new PaymentRequestDTO(customer.getCreditCard(), value),
                    PaymentReceiptDTO.class

            );
            System.out.println("Payement system : " + PaymentReceiptDTO.class.toString() );

            if (result.getStatusCode().equals(HttpStatus.CREATED) && result.hasBody()) {
              //  logger.info("Paiement réussi pour la carte: {}" + customer.getCreditCard());
                return Optional.of(result.getBody().payReceiptId());
            } else {
                return Optional.empty();
            }

        }
        catch (RestClientResponseException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
              //  logger.info("Échec du paiement pour la carte: {}, réponse: BAD_REQUEST " + creditCardNumber)
                return Optional.empty();
            }
            System.out.println("Payement system : " + PaymentReceiptDTO.class.toString() );
            throw errorException;
        }
    }

}
