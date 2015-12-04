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
import ms5000.properties.PropertiesUtils;

/**
 * This class bundles all the elements in the bottom of the screen
 */
public class BorderPane_Bottom extends BorderPane{
	/**
	 * The path to the css file
	 */
	private final String cssPath = PropertiesUtils.getString("bottom.section.config.border.pane.css.path");
	
	/**
	 * CSS Id for the hbox
	 */
	private final String hBoxId = PropertiesUtils.getString("bottom.section.config.hbox.css.id"); 
	
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
		this.getStylesheets().add(this.getClass().getResource(cssPath).toExternalForm());
		this.setId(PropertiesUtils.getString("bottom.section.config.border.pane.css.id"));
	
		// Binding the width to the mainframe width
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
		statusLarge.setId(PropertiesUtils.getString("bottom.section.config.textfield.large.css.id"));
		statusLarge.setAlignment(Pos.CENTER);
		vbox_center.getChildren().add(statusLarge);
		
		// The text field small
		statusSmall = new TextField("");
		statusSmall.setEditable(false);
		statusSmall.setId(PropertiesUtils.getString("bottom.section.config.textfield.small.css.id"));
		vbox_center.getChildren().add(statusSmall);
		
		// Hbox left (empty)
		hbox_left = new HBox();
		hbox_left.prefWidthProperty().bind(Main_Frame.gethBox_Left().widthProperty());
		hbox_left.setId(hBoxId);
		
		// Hbox right (Button start import, Button properties)
		hbox_right = new HBox();
		hbox_right.prefWidthProperty().bind(Main_Frame.gethBox_Right().widthProperty());
		hbox_right.getChildren().addAll(buttonStartImport,buttonProperties);
		hbox_right.setId(hBoxId);
		
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
	
	/**
	 * Sets the text in the small status text field
	 * 
	 * @param text the status text
	 */
	public void setStatusTextSmall(String text) {
		this.statusSmall.setText(text);
	}
	
	/**
	 * Sets the text in the large status text field
	 * 
	 * @param text the status text
	 */
	public void setStatusTextLarge(String text) {
		this.statusLarge.setText(text);
	}
}
