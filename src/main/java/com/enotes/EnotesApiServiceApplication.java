package com.enotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef="auditAware")
@EnableScheduling
@EnableCaching
public class EnotesApiServiceApplication {

	public static void main(String[] args) {
		
		
		SpringApplication.run(EnotesApiServiceApplication.class, args);
		
		System.out.println("================================================================================================================================");
		
		System.out.println("=================================================== my applicatoin is running ==================================================");
		
		System.out.println("================================================================================================================================");
		
		
		
	}
	

}
