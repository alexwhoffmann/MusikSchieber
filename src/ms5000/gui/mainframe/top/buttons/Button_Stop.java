package ms5000.gui.mainframe.top.buttons;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_MouseEventHandler;


public class Button_Stop extends Button{
	private final String icon_button_stop_disabled_path = "file:icons/Stop_Disabled.png";;
	private final String icon_button_stop_enabled_path = "file:icons/Stop.png";
	
	private final Image icon_button_stop_enabled;
	private final Image icon_button_stop_disabled;
	private final DropShadow shadow = new DropShadow();
	private final double button_radius = 23;
	
	public Button_Stop() {
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_borderpane_top_center.css").toExternalForm());
		icon_button_stop_disabled = new Image(icon_button_stop_disabled_path);
		icon_button_stop_enabled = new Image(icon_button_stop_enabled_path);
		this.setGraphic(new ImageView(icon_button_stop_disabled));
		this.setShape(new Circle(button_radius));
		
		this.setOnAction(new ButtonStartStop_EventHandler());
		this.setOnMouseEntered(new ButtonStartStop_MouseEventHandler());
		this.setOnMouseExited(new ButtonStartStop_MouseEventHandler());
	}
	
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
