package com.amf.crm;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "flowcrmtutorial")
@PWA(name = "Vaadin CRM", shortName = "CRM", offlinePath = "offline.html", offlineResources = { "images/offline.png" })
public class Application implements AppShellConfigurator {

	private static final long serialVersionUID = 8341500908861668411L;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
