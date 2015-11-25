package ms5000.gui.mainframe.top.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_MouseEventHandler;

public class Button_Start extends Button{
	private static final String icon_button_start_disabled_path = "file:icons/Play_Disabled.png";
	private static final double button_radius = 22;
	
	private boolean isPlaying = false;
	
	public Button_Start() {
		this.setStyle("-fx-focus-color: transparent;");
		this.setGraphic(new ImageView(icon_button_start_disabled_path));
		this.setShape(new Circle(button_radius));
		this.setMinSize(2*button_radius, 2*button_radius);
		this.setMaxSize(2*button_radius, 2*button_radius);
		this.setOnAction(new ButtonStartStop_EventHandler());
		this.setOnMouseEntered(new ButtonStartStop_MouseEventHandler());
		this.setOnMouseExited(new ButtonStartStop_MouseEventHandler());
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
}
