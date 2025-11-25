package com.jaberrantisi.piagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PiAgentApplication {

	public static void main(String[] args) {
		System.setProperty("diozero.devicefactory", "linuxfs");
		SpringApplication.run(PiAgentApplication.class, args);
	}

}
