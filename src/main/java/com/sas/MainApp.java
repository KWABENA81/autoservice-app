package com.sas;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sas.app.AppUtils;
import com.sas.app.ApplicationSplash;
import com.sas.fxmlviews.ApplicationEntryView;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Modality;

/*
  Following are Implied in @SpringBootApplications Annotation
		@ComponentScan({ "com.sas*" }), @EnableAutoConfiguration & @Configuration
*/
@SpringBootApplication
@EntityScan("com.sas*")
@EnableJpaRepositories("com.sas*")
@EnableJpaAuditing
public class MainApp extends AbstractJavaFxApplicationSupport {

	public static void main(String[] args) {
		AppUtils.getInstance();
		ApplicationSplash splashScreen = new ApplicationSplash();
		splashScreen.getImagePath();
		launchApp(MainApp.class, ApplicationEntryView.class, splashScreen, args);
	}

	public static void showView(Class<? extends AbstractFxmlView> class1, Modality mode) {
	}

}
