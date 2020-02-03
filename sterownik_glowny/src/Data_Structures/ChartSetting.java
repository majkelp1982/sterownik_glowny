package Data_Structures;

import java.sql.SQLException;
import WindowController.HandModeViewController;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class ChartSetting {
	HandModeViewController handModeViewController;
	
    private TreeView<String> treeViewChart = new TreeView<>(); 
	private TreeItem<String> rootItemChart = new TreeItem<>();
	private boolean enabled;
	private int chartNo;
    
    //Basic functions
 	public void setHandModeViewController(HandModeViewController handModeViewController) {
 		this.handModeViewController = handModeViewController;
 	}
 	
   public ChartSetting () {
	    treeViewChart.setRoot(rootItemChart);
    	treeViewChart.setShowRoot(false);
    	treeViewChart.getSelectionModel().select(0);
    	treeViewChart.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle); 
    }    	
    
    EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
    	// After double click remove selected series
    	if (event.getClickCount() == 2) {
    		TreeItem<String> parent = treeViewChart.getSelectionModel().getSelectedItem().getParent();
    		
    		// if parent is child from root return.
    		if (parent.getParent() == null) return;
    		
    		parent.getChildren().remove(treeViewChart.getSelectionModel().getSelectedItem());		
     		// if no more items in parent remove parent    	
    		if (parent.getChildren().isEmpty()) parent.getParent().getChildren().remove(parent);
    		
    		try {
				handModeViewController.LineChartRefreshSeries(chartNo, treeViewChart);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	};
    
    public TreeView<String> getTreeViewChart() {
		return treeViewChart;
	}
    
   public void setTreeViewChart(TreeView<String> treeViewChart) {
		this.treeViewChart = treeViewChart;
	}
   
   public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public TreeItem<String> getRootItemChart() {
		return rootItemChart;
	}

	public int getChartNo() {
		return chartNo;
	}

	public void setChartNo(int chartNo) {
		this.chartNo = chartNo;
	}

}
