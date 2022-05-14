package com.example.temperatureconversion.c2f;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient //render the class a Eureka client - i.e. make it discoverable by the Eureka server
@SpringBootApplication //shortcut for @Configuration, @EnableAutoConfiguration, @ComponentScan
public class c2fServer {
	public static void main(String[] args) {
		
		//Change name of config file for this server - default is application.properties
		System.setProperty("spring.config.name", "c2f-server");
		
		/**
			1. Bootstrap a spring application as a stand-alone application
			2. Create an ApplicationContext object (IoC container instance) 
			3. The @EnableAutoConfiguration tells Spring to:
				a. Look for user-defined beans in the classpath, instantiate them, and load the instances in the ApplicationContext object 
				   (these include the two controllers c2fController and HomeController)
				b. Load jar dependencies from the POM file in the ApplicationContext object
		    4. Launch an embedded Tomcat server instance
		**/
		SpringApplication.run(c2fServer.class, args);
	}
}
