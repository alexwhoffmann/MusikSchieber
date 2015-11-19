package ms5000.gui.mainframe.top;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;

public class Button_Start extends Button{
	private static final String icon_button_start_disabled_path = "file:icons/Play_Disabled.png";
	private static final double button_radius = 23;
	
	private boolean isPlaying = false;
	
	public Button_Start() {
		this.setStyle("-fx-focus-color: transparent;");
		this.setGraphic(new ImageView(icon_button_start_disabled_path));
		this.setShape(new Circle(button_radius));
		this.setMinSize(2*button_radius, 2*button_radius);
		this.setMaxSize(2*button_radius, 2*button_radius);
		
		this.setOnMouseEntered(new ButtonStartStop_EventHandler("Start",this));
		this.setOnMouseExited(new ButtonStartStop_EventHandler("Start",this));
		this.setOnMouseClicked(new ButtonStartStop_EventHandler("Start",this));
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
}
