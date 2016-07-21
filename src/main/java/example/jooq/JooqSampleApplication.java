package example.jooq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JooqSampleApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(JooqSampleApplication.class, args);
        System.in.read();
    }
}
