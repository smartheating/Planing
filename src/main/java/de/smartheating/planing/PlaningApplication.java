package de.smartheating.planing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@ComponentScan(basePackages = {"de.smartheating.SmartHeatingCommons","de.smartheating.planing"})
@EnableDiscoveryClient
public class PlaningApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaningApplication.class, args);
	}

}
