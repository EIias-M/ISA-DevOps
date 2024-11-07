package fr.univcotedazur.w4e;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class W4eServer {

    public static void main(String[] args) {
        SpringApplication.run(W4eServer.class, args);
    }

}
