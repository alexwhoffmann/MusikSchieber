package ms5000.gui.mainframe.top.buttons;

import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_MouseEventHandler;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.icons.IconProperties;

/**
 * This class implements the functionalities of the stop button
 */
public class Button_Stop extends Button{
	
	/**
	 * The icon images
	 */
	private final Image icon_button_stop_enabled = new Image(PropertiesUtils.getProperty(IconProperties.STOPENABLED));
	private final Image icon_button_stop_disabled = new Image(PropertiesUtils.getProperty(IconProperties.STOPDISABLED));
	
	/**
	 * The drop shadow
	 */
	private final DropShadow shadow = new DropShadow();
	
	/**
	 * The button radius
	 */
	private final double button_radius = 23;
	
	public Button_Stop() {
		// Setting the style sheet
		String cssPath = "../" + PropertiesUtils.getString("top.section.config.borderpane.top.center.css");
		this.getStylesheets().add(this.getClass().getResource(cssPath).toExternalForm());
		
		// Styling the button
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
	public void changeButtonIcon_Rollover(EventType<? extends MouseEvent> eventType) {
		if (eventType == MouseEvent.MOUSE_ENTERED) {
			this.setEffect(shadow);
			this.setGraphic(new ImageView(icon_button_stop_enabled));
		} else if (eventType == MouseEvent.MOUSE_EXITED) {
			this.setEffect(null);
			this.setGraphic(new ImageView(icon_button_stop_disabled));
		}
	}
}
