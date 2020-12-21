package communityhousecontroller;

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
			Scene generalScene;
			if(LoginController.currentUser.getType() == 1){
				generalScene = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/GeneralScene.fxml")));
			} else {
				generalScene = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/RegisterPage2.fxml")));
			}

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
