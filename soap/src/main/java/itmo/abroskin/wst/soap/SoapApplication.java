package itmo.abroskin.wst.soap;

import itmo.abroskin.wst.core.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CoreConfiguration.class)
public class SoapApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoapApplication.class, args);
    }
}
