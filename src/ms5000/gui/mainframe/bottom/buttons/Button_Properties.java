package ms5000.gui.mainframe.bottom.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.bottom.eventhandler.ButtonProperties_EventHandler;

/**
 * This class implements the functionalities of the button to show the properties
 */
public class Button_Properties extends Button{
	/**
	 * The icon path
	 */
	private final String icon_button_properties_path = "file:icons/properties_icon.png";
	
	/**
	 * The icon image
	 */
	private final Image icon_button_properties;
	
	/**
	 * The radius of the button
	 */
	private final double button_radius = 10;
	
	/**
	 * Constructs the button
	 */
	public Button_Properties() {
		// Applying the stylesheet
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_borderpane_bottom.css").toExternalForm());
		this.setId("button");
		
		// Adding the icon
		icon_button_properties = new Image(icon_button_properties_path);
		ImageView view = new ImageView(icon_button_properties);
		this.setPrefSize(button_radius, button_radius);
		view.setFitWidth(70);
		view.setFitHeight(70);
		this.setGraphic(view);
		this.setShape(new Circle(button_radius));		
		
		// Applying the event handler
		this.setOnAction(new ButtonProperties_EventHandler());
	}
}
