package com.umbrella.insurance.website;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>WebsiteApplication class.</p>
 *
 * @author marcus
 * @version $Id: $Id
 * @since 0.0.21
 */
@SpringBootApplication()
@ComponentScan({"com.umbrella.insurance"})
@EnableJpaRepositories
public class WebsiteApplication extends ServletInitializer {

	@Autowired
	private Flyway flyway;

	public static void main(String[] args) {

		SpringApplication.run(WebsiteApplication.class, args);

	}

}
