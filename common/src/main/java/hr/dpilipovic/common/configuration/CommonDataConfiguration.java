package hr.dpilipovic.common.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("hr.dpilipovic.common")
@EntityScan("hr.dpilipovic.common.model")
@EnableJpaRepositories("hr.dpilipovic.common.repository")
public class CommonDataConfiguration {

}
