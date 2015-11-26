package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BoderPane_TOP_CENTER extends BorderPane {
	/**
	 * The text field that displays the time to go on the playing track
	 */
	private TextField timeToGO;
	
	/**
	 * The text field that displays the time left on the playing track
	 */
	private TextField timeLeft;
	
	/**
	 * The VBox box with time to go
	 */
	private VBox box_TimeToGo;
	
	/**
	 * The VBox box with time left
	 */
	private VBox box_TimeLeft;
	
	/**
	 * the VBox that stores all the elements of the top center display
	 */
	private static VBox_StatusSlider statusSlider;
	
	/**
	 * Instantiates the Border pane
	 */
	public BoderPane_TOP_CENTER() {
		// Setting the style sheet
		this.getStylesheets().add(this.getClass().getResource("css/mainframetop_borderpane_top_center.css").toExternalForm());
		
		// Building the border pane
		timeToGO = new TextField();
		timeLeft = new TextField();
	
		timeToGO.setEditable(false);
		timeLeft.setEditable(false);
		
		statusSlider = new VBox_StatusSlider();
		statusSlider.setId("vbox_status_slider");
		
		box_TimeToGo = new VBox();
		box_TimeLeft = new VBox();
		box_TimeToGo.getChildren().add(timeToGO);
		box_TimeLeft.getChildren().add(timeLeft);
		
		BoderPane_TOP_CENTER.setMargin(box_TimeToGo, new Insets(65,8,8,8));
		BoderPane_TOP_CENTER.setMargin(box_TimeLeft, new Insets(65,8,8,8));
		BoderPane_TOP_CENTER.setAlignment(box_TimeToGo, Pos.BOTTOM_LEFT);
		BoderPane_TOP_CENTER.setAlignment(box_TimeLeft, Pos.BOTTOM_LEFT);
		
		this.setCenter(statusSlider);
		this.setLeft(box_TimeToGo);
		this.setRight(box_TimeLeft);
		this.setId("border-pane");
		
	}
	/**
	 * Returns the vbox containing the status slider
	 * 
	 * @return vbox containing the status slider
	 */
	public VBox_StatusSlider getStatusSlider() {
		return statusSlider;
	}
	
	/**
	 * Resets the status fields
	 */
	public void resetStatusFields() {
		this.getStatusSlider().setStatusText("");
		this.timeLeft.setText("");
		this.timeToGO.setText("");
	}
	
	/**
	 * Sets the time fields
	 */
	public void setTimeFields(String timeToGo, String timeLeft) {
		this.timeLeft.setText(timeLeft);
		this.timeToGO.setText(timeToGo);
	}
}
