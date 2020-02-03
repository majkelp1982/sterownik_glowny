package WindowController;

import java.sql.SQLException;
import Communication.EventManager;
import Data_Structures.ChartSetting;
import Data_Structures.TableColumnProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

//XML Libraries
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

public class ChartsManagerController {
	//Basic variables
	EventManager eventManager;
	HandModeViewController handModeViewController;
	ChartSetting[] chartSetting;
	
	private ObservableList<Data_Structures.Module> modules = FXCollections.observableArrayList();

    @FXML private TabPane tabPane;
    @FXML private TreeView<String> treeView;
   
    @FXML private AnchorPane anchorPaneChart1;
    @FXML private AnchorPane anchorPaneChart2;
    @FXML private AnchorPane anchorPaneChart3;
    @FXML private AnchorPane anchorPaneChart4;

    @FXML private CheckBox chart1enabled;  
    @FXML private CheckBox chart2enabled;  
    @FXML private CheckBox chart3enabled;  
    @FXML private CheckBox chart4enabled;  
    
    @FXML private Tab tab1;
    @FXML private Tab tab2;
    @FXML private Tab tab3;
    @FXML private Tab tab4;
    
    //Basic functions
 	public void setEventManager(EventManager eventManager) {
 		this.eventManager = eventManager;
 	}
 	
    //Basic functions
 	public void setHandModeViewController(HandModeViewController handModeViewController) {
 		this.handModeViewController = handModeViewController;
 	}
 	
 	public void setChartsSetting(ChartSetting[] chartSetting) {
 		this.chartSetting = chartSetting;
	}
	
	public void getModules(ObservableList<Data_Structures.Module> modules) throws SQLException {
		this.modules = modules;
	}
	
	public void initializeCharts() throws SQLException {
		
		//Left tree //MAIN/
        TreeItem<String> rootItem = new TreeItem<>();
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
        
        for (Data_Structures.Module mod : modules) {
        	String name = mod.getModuleName();
        	TreeItem<String> module = new TreeItem<String> (name);
	        //Add Module get from Hardware list to treeList
	        rootItem.getChildren().add(module);
	        
	        //Get module column names from DB
			ObservableList<TableColumnProperty> columnNames = FXCollections.observableArrayList();
			columnNames = eventManager.dataBaseController.getTableColumnNames(mod.getModuleName());
			for (TableColumnProperty columnName : columnNames) {
				if ((!columnName.getColumnTyp().equals("TEXT")) && (!(columnName.getColumnTyp().equals("DATETIME")))) {
					TreeItem<String> subItem = new TreeItem<String> (columnName.getColumnName() + "_"+columnName.getColumnTyp());
					module.getChildren().add(subItem);		
				}
			}
		}
        
        // Right side from split panel
        if (chartSetting[0].isEnabled()) chart1enabled.setSelected(true);
  	    //Property for char1enabled
  		chart1enabled.selectedProperty().addListener(new ChangeListener<Boolean>() {
              public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                if (chart1enabled.isSelected()) {
  					handModeViewController.LineChartAddChart(0); 
  					chartSetting[0].setEnabled(true);
                }
  			else {
  				handModeViewController.LineChartRemoveChart(0);
  				chartSetting[0].setEnabled(false);
  			}
             }
          });	
        if (chartSetting[1].isEnabled()) chart2enabled.setSelected(true);
  	    //Property for char1enabled
  		chart2enabled.selectedProperty().addListener(new ChangeListener<Boolean>() {
              public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                if (chart2enabled.isSelected()) {
  					handModeViewController.LineChartAddChart(1); 
  					chartSetting[1].setEnabled(true);
                }
  			else {
  				handModeViewController.LineChartRemoveChart(1);
  				chartSetting[1].setEnabled(false);
  			}
             }
          });
  		
        if (chartSetting[2].isEnabled()) chart3enabled.setSelected(true);
   	    //Property for char1enabled
   		chart3enabled.selectedProperty().addListener(new ChangeListener<Boolean>() {
               public void changed(ObservableValue<? extends Boolean> ov,
                 Boolean old_val, Boolean new_val) {
                 if (chart3enabled.isSelected()) {
   					handModeViewController.LineChartAddChart(2); 
   					chartSetting[2].setEnabled(true);
                 }
   			else {
   				handModeViewController.LineChartRemoveChart(2);
   				chartSetting[2].setEnabled(false);
   			}
              }
           });	
        if (chartSetting[3].isEnabled()) chart4enabled.setSelected(true);
   	    //Property for char1enabled
   		chart4enabled.selectedProperty().addListener(new ChangeListener<Boolean>() {
               public void changed(ObservableValue<? extends Boolean> ov,
                 Boolean old_val, Boolean new_val) {
                 if (chart4enabled.isSelected()) {
   					handModeViewController.LineChartAddChart(3); 
   					chartSetting[3].setEnabled(true);
                 }
   			else {
   				handModeViewController.LineChartRemoveChart(3);
   				chartSetting[3].setEnabled(false);
   			}
              }
           });	
   		AddTreeViewToPane(anchorPaneChart1, chartSetting[0]);	    
   		AddTreeViewToPane(anchorPaneChart2, chartSetting[1]);	    
   		AddTreeViewToPane(anchorPaneChart3, chartSetting[2]);	    
   		AddTreeViewToPane(anchorPaneChart4, chartSetting[3]);
   		
   		//Initialize diagrams from saved XML file
   		XMLread();		
	}
	
    @FXML
    void treeViewClick(MouseEvent event) throws SQLException {
    	if (treeView.getSelectionModel().equals(null)) return;
     	
    	// Number of active tab
    	int activeTab = tabPane.getSelectionModel().getSelectedIndex();
    	
    	if (event.getClickCount() == 2) {
    		String parentName = treeView.getSelectionModel().getSelectedItem().getParent().getValue();
    		String childName = treeView.getSelectionModel().getSelectedItem().getValue();
    		
    		// if module name selected return!
    		if (parentName == null) return;
 		
    		TreeItem<String> module = null;
    		for (TreeItem<String> mod : chartSetting[activeTab].getTreeViewChart().getRoot().getChildren()) {
    			if (mod.getValue().equals(parentName))
    				module = mod;
    		}
    		if (module == null) {
    			module = new TreeItem<String> (parentName);
    			chartSetting[activeTab].getTreeViewChart().getRoot().getChildren().add(module);
    		}
    	
    		// if series already exist return
    		for (TreeItem<String> tmpSerie : module.getChildren())
    			if (tmpSerie.getValue().equals(childName)) return; 
    		
    		TreeItem<String> series = new TreeItem<String> (childName);
    		module.getChildren().add(series);
    		
    		// Add series to chart
    		int controllerNo = 0;
    		String tableName = null;
       		if (parentName.indexOf(EventManager.Wentylacja) != -1) tableName = "Controller_Vent_Archive";
       	    if (parentName.indexOf(EventManager.Ogrzewanie) != -1) tableName = "Controller_Heating_Archive";
       	    if (parentName.indexOf(EventManager.Kominek) != -1) tableName = "Controller_Fireplace_Archive";
       	    if (parentName.indexOf(EventManager.Komfort) != -1) tableName = "Controller_Comfort_Archive";

    		// Column name and dataTyp
    		String[] stringSplit = childName.split("_");
    		
    		String columnName = stringSplit[0];
    		String dataTyp = stringSplit[1];	
    		handModeViewController.LineChartAddSeries(activeTab,tableName, columnName, dataTyp, controllerNo);
    		
    		// if nothing is selected. Set selection on first position
    		if (chartSetting[activeTab].getTreeViewChart().getSelectionModel().getSelectedIndex() < 0)
    			chartSetting[activeTab].getTreeViewChart().getSelectionModel().select(0);
    		
    		//Save last diagrams settings
    		XMLwrite ();
    	      
		}
    }
    
    private void AddTreeViewToPane(AnchorPane anchorPaneChart, ChartSetting chartSetting) {
	    anchorPaneChart.getChildren().add(chartSetting.getTreeViewChart());
	    anchorPaneChart.setTopAnchor(chartSetting.getTreeViewChart(), 37.0);
	    anchorPaneChart.setBottomAnchor(chartSetting.getTreeViewChart(), 0.0);
	    anchorPaneChart.setLeftAnchor(chartSetting.getTreeViewChart(), 0.0);
	    anchorPaneChart.setRightAnchor(chartSetting.getTreeViewChart(), 0.0);
    }
    
    public void XMLread () {
   		//Get item list saved in XML file to recreate diagrams
 		try {
		    File inputFile = new File("diagrams.xml");
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		    Document doc = docBuilder.parse(inputFile);
		    
		    Element root = doc.getDocumentElement();
		    NodeList diagram = root.getChildNodes();
		    for (int i=0; i<diagram.getLength(); i++) {
		    	
		    	//delete old item
		    	chartSetting[i].getTreeViewChart().getRoot().getChildren().clear();
		    	
		    	String tabName = diagram.item(i).getNodeName();
		    	switch (i) {
			    	// Set tab/diagram name 
			    	case 0: tab1.setText(tabName); break;
			    	case 1: tab2.setText(tabName); break;
			    	case 2: tab3.setText(tabName); break;
			    	case 3: tab4.setText(tabName); break;
		    	}	    	
		    	NodeList modul = diagram.item(i).getChildNodes();		    	
		    	for (int j=0; j<modul.getLength(); j++) {
		    		
		    		//Adding module to Tree in each active search diagram
		    		TreeItem<String> module = new TreeItem<String> (modul.item(j).getNodeName());
		    		chartSetting[i].getTreeViewChart().getRoot().getChildren().add(module);
			    	NodeList item = modul.item(j).getChildNodes();		    		
			    	for (int k=0; k<item.getLength(); k++) {
			    		TreeItem<String> element = new TreeItem<String> (item.item(k).getNodeName());
			    		module.getChildren().add(element);
			    	}
		    	}
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
  
    public void XMLwrite () {
		// Save results to XML file
		try {
			DocumentBuilderFactory dbFactory =
	          DocumentBuilderFactory.newInstance();
	          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	          Document doc = dBuilder.newDocument();
	          
	          // root element
	          Element rootElement = doc.createElement("Diagrams");
	          doc.appendChild(rootElement);
	          
	          //add first diagram element    
	          for (int i=0; i<4; i++) {
	        	  //delete old items
	        	  chartSetting[i].getTreeViewChart().getRoot().getChildren();
	        	  
	        	  String tabName =" ";
	        	  switch (i) {
	        	  case 0 : tabName = tab1.getText(); break;
	        	  case 1 : tabName = tab2.getText(); break;
	        	  case 2 : tabName = tab3.getText(); break;
	        	  case 3 : tabName = tab4.getText(); break;
	        	  }
		          Element diagram = doc.createElement(tabName);
		          rootElement.appendChild(diagram);	          

		          for (TreeItem<String> mod : chartSetting[i].getTreeViewChart().getRoot().getChildren()) {
		        	  Element diagElement = doc.createElement(mod.getValue());
		        	  for (TreeItem<String> item : mod.getChildren()) {
		        		  Element diagItem = doc.createElement(item.getValue());
		        		  diagElement.appendChild(diagItem);
		        	  }
	        	  diagram.appendChild(diagElement);
		          }		        	  
	          }
	          
	          // write the content into xml file
	          TransformerFactory transformerFactory = TransformerFactory.newInstance();
	          Transformer transformer = transformerFactory.newTransformer();
	          DOMSource source = new DOMSource(doc);
	          StreamResult result = new StreamResult(new File("diagrams.xml"));
	          transformer.transform(source, result);
	          
	          // Output to console for testing
	          StreamResult consoleResult = new StreamResult(System.out);
	          transformer.transform(source, consoleResult);
	       } catch (Exception e) {
	          e.printStackTrace();
	       }    	
    }

}
