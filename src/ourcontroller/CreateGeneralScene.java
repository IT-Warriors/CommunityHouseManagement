package ourcontroller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateGeneralScene extends Application{
	public static Stage genStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene generalScene = new Scene(FXMLLoader.load(getClass() .getResource("/ourview/GeneralScene.fxml")));
			//loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(generalScene);
			genStage = primaryStage;
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void gen() {
		launch();
	}
	
}
