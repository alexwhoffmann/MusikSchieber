package ms5000.gui.mainframe.bottom.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.bottom.eventhandler.ButtonProperties_EventHandler;


public class Button_Properties extends Button{
	private final String icon_button_properties_path = "file:icons/properties_icon.png";
	private final Image icon_button_properties;
	private final double button_radius = 10;
	
	public Button_Properties() {
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_borderpane_bottom.css").toExternalForm());
		this.setId("button");
		icon_button_properties = new Image(icon_button_properties_path);
		ImageView view = new ImageView(icon_button_properties);
		this.setPrefSize(button_radius, button_radius);
		view.setFitWidth(70);
		view.setFitHeight(70);
		this.setGraphic(view);
		this.setShape(new Circle(button_radius));
		
		this.setOnAction(new ButtonProperties_EventHandler());
	}
}
