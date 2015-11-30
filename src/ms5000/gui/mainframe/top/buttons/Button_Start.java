package ms5000.gui.mainframe.top.buttons;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ms5000.audio.player.AudioPlayer;
import ms5000.audio.player.PlayerStatus;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_MouseEventHandler;

/**
 * This class implements the functionalities of the start button
 */
public class Button_Start extends Button{
	
	/**
	 * The start disabled icon
	 */
	private final String icon_button_start_disabled_path = "file:icons/Play_Disabled.png";
	
	/**
	 * The start enabled icon
	 */
	private final String icon_button_start_enabled_path = "file:icons/Play.png";
	
	/**
	 * The pause enabled icon
	 */
	private final String icon_button_pause_enabled_path = "file:icons/Pause.png";
	
	/**
	 * The pause disabled icon
	 */
	private final String icon_button_pause_disabled_path = "file:icons/Pause_Disabled.png";
	
	/**
	 * The icon images
	 */
	private final Image icon_button_start_disabled;
	private final Image icon_button_pause_disabled;
	private final Image icon_button_start_enabled;
	private final Image icon_button_pause_enabled;
	
	/**
	 * The drop shadow
	 */
	private final DropShadow shadow = new DropShadow();
	
	/**
	 * The button radius
	 */
	private final double button_radius = 22;
	
	/**
	 * Constructs the button
	 */
	public Button_Start() {
		// Applying the style sheet
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_borderpane_top_center.css").toExternalForm());
		
		icon_button_start_enabled = new Image(icon_button_start_enabled_path);
		icon_button_pause_enabled = new Image(icon_button_pause_enabled_path);
		icon_button_start_disabled = new Image(icon_button_start_disabled_path);
		icon_button_pause_disabled = new Image(icon_button_pause_disabled_path);
		
		// Styling the button
		this.setGraphic(new ImageView(icon_button_start_disabled));
		this.setShape(new Circle(button_radius));
		
		// Applying the eventhandler
		this.setOnAction(new ButtonStartStop_EventHandler());
		this.setOnMouseEntered(new ButtonStartStop_MouseEventHandler());
		this.setOnMouseExited(new ButtonStartStop_MouseEventHandler());
	}
	
	/**
	 * Method to change the button icon
	 * @param stopPressed Boolean indicating whether the stop button was pressed
	 */
	public void changeButtonIcon(boolean stopPressed) {
		if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PLAYING && !stopPressed) {
			this.setGraphic(new ImageView(icon_button_pause_enabled));
			shapeButtonRectangle();
		} else if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PAUSED && !stopPressed) {
			this.setGraphic(new ImageView(icon_button_start_enabled));
			shapeButtonCircle();
		} else if (stopPressed) {
			this.setGraphic(new ImageView(icon_button_start_disabled));
			shapeButtonCircle();
		}
	}
	
	/**
	 * Method to shape the button as circle
	 */
	private void shapeButtonCircle() {
		this.setShape(new Circle(button_radius));
	}

	/**
	 * Method to shape the button as rectangle
	 */
	private void shapeButtonRectangle() {
		this.setShape(new Rectangle(2*button_radius,2*button_radius));
	}
	
	/**
	 * Method to apply the rollover icon
	 * 
	 * @param eventType the occurred event
	 */
	public void changeButtonIcon_Rollover(String eventType) {
		if (eventType.equals("MOUSE_ENTERED")) {
			this.setEffect(shadow);
			if (AudioPlayer.getInstance().getStatus() != PlayerStatus.PLAYING) {
				this.setGraphic(new ImageView(icon_button_start_enabled));
				shapeButtonCircle();
			} else if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PLAYING) {
				this.setGraphic(new ImageView(icon_button_pause_enabled));
				shapeButtonRectangle();
			}
		} else if (eventType.toString().equals("MOUSE_EXITED")) {
				this.setEffect(null);
				
				if (AudioPlayer.getInstance().getStatus() != PlayerStatus.PLAYING) {
					this.setGraphic(new ImageView(icon_button_start_disabled));
					shapeButtonCircle();
				} else {
					this.setGraphic(new ImageView(icon_button_pause_disabled));
					shapeButtonRectangle();
				}
			} 
		}
}
