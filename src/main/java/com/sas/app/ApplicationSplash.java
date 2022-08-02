package com.sas.app;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ApplicationSplash extends SplashScreen {
	private static String[] images = {"/splash/asksef.png", "/splash/javafx01.png", "/splash/javafx.png" };

	@Override
	public Parent getParent() {
		final ImageView imageView = new ImageView(getClass().getResource(getImagePath()).toExternalForm());
		final ProgressBar splashProgressBar = new ProgressBar();
		splashProgressBar.setPrefWidth(imageView.getImage().getWidth());

		final VBox vbox = new VBox();
		vbox.getChildren().addAll(imageView, splashProgressBar);
		return vbox;
	}

	@Override
	public String getImagePath() {
		return images[0];
	}

}
