 package Main;

import java.sql.SQLException;

import Communication.EventManager;
import Data_Structures.Controller_Comfort;
import Data_Structures.Controller_Fireplace;
import Data_Structures.Controller_Heating;
import Data_Structures.Controller_Vent;
import WindowController.HandModeViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
		
public class Main extends Application{
	Controller_Heating[] controller_Heating;
	Controller_Fireplace[] controller_Fireplace;
	Controller_Vent[] controller_Vent;
	Controller_Comfort[] controller_Comfort;
	 
	public static void main(String[] args) throws Exception {
		launch(args);	
	}

	@Override
	public void start(Stage primaryStage) throws Exception { 
		EventManager eventManager = new EventManager();
		controller_Heating = eventManager.dataBaseController.getController_Heating();
		controller_Fireplace = eventManager.dataBaseController.getController_Fireplace();
		controller_Vent = eventManager.dataBaseController.getController_Vent();
		controller_Comfort = eventManager.dataBaseController.getController_Comfort();
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(this.getClass().getResource("/fxml/HandModeView.fxml"));	

		Pane pane = loader.load();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("myStyle.css");
		
		HandModeViewController handModeViewController = loader.getController();
		handModeViewController.setEventManager(eventManager);
		if (eventManager.isController_Heating_Available()) handModeViewController.setDataStructure(controller_Heating);
		if (eventManager.isController_Fireplace_Available()) handModeViewController.setDataStructure(controller_Fireplace);
		if (eventManager.isController_Vent_Available()) handModeViewController.setDataStructure(controller_Vent);
		if (eventManager.isController_Comfort_Available()) handModeViewController.setDataStructure(controller_Comfort);
		//TMP here extend neu structures 
		//TODO 
		
		handModeViewController.initialize_Menu();
		handModeViewController.setBackgroundWorker();

		//Listeners to refresh display
		eventManager.reqGUIrefreshHand.addListener(e -> {Platform.runLater(() -> { try {
			handModeViewController.ReqGUIrefreshHand();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});});
		
		primaryStage.setScene(scene);	
		primaryStage.setTitle("Sterowanie rêczne");
		eventManager.GUIInvalid();
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
	}
}
