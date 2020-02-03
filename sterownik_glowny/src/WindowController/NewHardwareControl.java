package WindowController;

import java.sql.SQLException;

import Communication.EventManager;
import Data_Structures.Controller_Comfort;
import Data_Structures.Controller_Fireplace;
import Data_Structures.Controller_Heating;
import Data_Structures.Controller_Vent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

public class NewHardwareControl {

	private static final int maxNumberController = 15;
	
	EventManager eventManager;
	Controller_Heating[] controller_Heating;
	Controller_Fireplace[] controller_Fireplace;
	Controller_Vent[] controller_Vent;
	Controller_Comfort[] controller_Comfort;

    @FXML
    private Pane newHardwarePane;

    @FXML
    private ChoiceBox<String> choiceTyp;

    @FXML
    private ChoiceBox<Integer> choiceNumer;

    @FXML
    private Button buttonDodaj;

	@FXML
	private void initialize() {
		choiceTyp.getItems().clear();		
		choiceTyp.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			setAvailableChoiceNumber();
	    });
	}
	private void setAvailableChoiceTyp() {
		if (controller_Vent[0] == null) choiceTyp.getItems().add(EventManager.Wentylacja);
		if (controller_Heating[0] == null) choiceTyp.getItems().add(EventManager.Ogrzewanie);
		if (controller_Fireplace[0] == null) choiceTyp.getItems().add(EventManager.Kominek);
		if (controller_Comfort[0] == null) choiceTyp.getItems().add(EventManager.Komfort);
		
	}

	private void setAvailableChoiceNumber() {
		choiceNumer.getItems().clear();
	}
	
	public void setControllers(EventManager eventManager) {
		this.eventManager = eventManager;
		
		this.controller_Heating = this.eventManager.controller_Heating;
		this.controller_Fireplace = this.eventManager.controller_Fireplace;
		this.controller_Vent = this.eventManager.controller_Vent;
		this.controller_Comfort = this.eventManager.controller_Comfort;
		//TMP
		// set another controllers when ready
		setAvailableChoiceTyp();
	}

    @FXML
    void buttonDodajOnAction(ActionEvent event) throws NumberFormatException, SQLException {
    	if (choiceNumer.getValue()==null) {
			choiceNumer.getItems().add(0);
    		choiceNumer.setValue(0);
    	}
    	eventManager.dataBaseController.addNewHadware((String) choiceTyp.getValue(), choiceNumer.getValue());//TMPInteger.parseInt((String) choiceNumer.getValue()));
    	setAvailableChoiceNumber();
    	
    	//TMP restart after new module was added
		System.exit(0);
    }
}