package ms5000.gui.mainframe.top.buttons;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_MouseEventHandler;

/**
 * This class implements the functionalities of the stop button
 */
public class Button_Stop extends Button{
	
	/**
	 * The stop disabled icon
	 */
	private final String icon_button_stop_disabled_path = "file:icons/Stop_Disabled.png";;
	
	/**
	 * The stop enabled icon
	 */
	private final String icon_button_stop_enabled_path = "file:icons/Stop.png";
	
	/**
	 * The icon images
	 */
	private final Image icon_button_stop_enabled;
	private final Image icon_button_stop_disabled;
	
	/**
	 * The drop shadow
	 */
	private final DropShadow shadow = new DropShadow();
	
	/**
	 * The button radius
	 */
	private final double button_radius = 23;
	
	public Button_Stop() {
		// Applying the style sheets
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_borderpane_top_center.css").toExternalForm());
		
		// Styling the button
		icon_button_stop_disabled = new Image(icon_button_stop_disabled_path);
		icon_button_stop_enabled = new Image(icon_button_stop_enabled_path);
		this.setGraphic(new ImageView(icon_button_stop_disabled));
		this.setShape(new Circle(button_radius));
		
		// Applying the event handler
		this.setOnAction(new ButtonStartStop_EventHandler());
		this.setOnMouseEntered(new ButtonStartStop_MouseEventHandler());
		this.setOnMouseExited(new ButtonStartStop_MouseEventHandler());
	}
	
	/**
	 * Method to change the button icon corresponding to the occurred event
	 * 
	 * @param eventType the occurred event
	 */
	public void changeButtonIcon_Rollover(String eventType) {
		if (eventType.equals("MOUSE_ENTERED")) {
			this.setEffect(shadow);
			this.setGraphic(new ImageView(icon_button_stop_enabled));
		} else if (eventType.toString().equals("MOUSE_EXITED")) {
			this.setEffect(null);
			this.setGraphic(new ImageView(icon_button_stop_disabled));
		}
	}
}
