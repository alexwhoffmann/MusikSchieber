package ms5000.gui.mainframe.bottom;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.bottom.buttons.Button_Properties;
import ms5000.gui.mainframe.bottom.buttons.Button_StartImport;

/**
 * This class bundles all the elements in the bottom of the screen
 */
public class BorderPane_Bottom extends BorderPane{
	/**
	 * the progress bar in the bottom
	 */
	private final ProgressBar progressBar = new ProgressBar(0);
	
	/**
	 * Button to start the music library import
	 */
	private final Button_StartImport buttonStartImport;
	
	/**
	 * Button to open the properties page
	 */
	private final Button_Properties buttonProperties;
	
	/**
	 * The vbox in the center
	 */
	private final VBox vbox_center;
	
	/**
	 * The text field for the status (large)
	 */
	private final TextField statusLarge;
	
	/**
	 * The text field for the status (small)
	 */
	private final TextField statusSmall;
	
	/**
	 * The hbox in the left
	 */
	private final HBox hbox_left;
	
	/**
	 * The hbox in the right
	 */
	private final HBox hbox_right;
	
	public BorderPane_Bottom() {
		// Setting the style sheet
		this.getStylesheets().add(this.getClass().getResource("css/mainframetop_borderpane_bottom.css").toExternalForm());
		this.setId("border-pane");
		
		// Binding the width to the mainfram width
		this.prefWidthProperty().bind(Main_Frame.getScene().widthProperty());
		progressBar.prefWidthProperty().bind(Main_Frame.getScene().widthProperty());
		
		// Adding the progress bar
		this.setTop(progressBar);
		
		// The buttons
		buttonStartImport = new Button_StartImport();
		buttonProperties = new Button_Properties();
		
		// The vbox in the center
		vbox_center = new VBox();
		vbox_center.setId("vbox");
		
		// The text field large
		statusLarge = new TextField("");
		statusLarge.setEditable(false);
		statusLarge.setId("textfield_large");
		statusLarge.setAlignment(Pos.CENTER);
		vbox_center.getChildren().add(statusLarge);
		
		// The text field small
		statusSmall = new TextField("");
		statusSmall.setEditable(false);
		statusSmall.setId("textfield_small");
		vbox_center.getChildren().add(statusSmall);
		
		// Hbox left (empty)
		hbox_left = new HBox();
		hbox_left.prefWidthProperty().bind(Main_Frame.gethBox_Left().widthProperty());
		hbox_left.setId("hbox");
		
		// Hbox right (Button start import, Button properties)
		hbox_right = new HBox();
		hbox_right.prefWidthProperty().bind(Main_Frame.gethBox_Right().widthProperty());
		hbox_right.getChildren().addAll(buttonStartImport,buttonProperties);
		hbox_right.setId("hbox");
		
		this.setRight(hbox_right);
		this.setLeft(hbox_left);
		this.setCenter(vbox_center);
	}
	
	/**
	 * Returns the instance of the progress bar in the bottom
	 * 
	 * @return instance of the progress bar in the bottom
	 */
	public ProgressBar getProgressBar() {
		return progressBar;
	}
}
