package itmo.abroskin.wst.core;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@ComponentScan("itmo.abroskin.wst.itmo.abroskin.wst.core")
@EntityScan("itmo.abroskin.wst.itmo.abroskin.wst.core.models")
public class CoreConfiguration {
}
