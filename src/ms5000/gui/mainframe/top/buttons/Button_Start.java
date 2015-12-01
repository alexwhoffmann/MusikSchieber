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
import ms5000.properties.PropertiesUtils;
import ms5000.properties.icons.IconProperties;

/**
 * This class implements the functionalities of the start button
 */
public class Button_Start extends Button{
	
	/**
	 * The icon images
	 */
	private final Image icon_button_play_disabled = new Image(PropertiesUtils.getProperty(IconProperties.PLAYDISABLED));
	private final Image icon_button_pause_disabled = new Image(PropertiesUtils.getProperty(IconProperties.PAUSEDISABLED));
	private final Image icon_button_play_enabled= new Image(PropertiesUtils.getProperty(IconProperties.PLAYENABLED));
	private final Image icon_button_pause_enabled= new Image(PropertiesUtils.getProperty(IconProperties.PAUSEENABLED));
	
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
		
		// Styling the button
		this.setGraphic(new ImageView(icon_button_play_disabled));
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
			this.setGraphic(new ImageView(icon_button_play_enabled));
			shapeButtonCircle();
		} else if (stopPressed) {
			this.setGraphic(new ImageView(icon_button_play_disabled));
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
				this.setGraphic(new ImageView(icon_button_play_enabled));
				shapeButtonCircle();
			} else if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PLAYING) {
				this.setGraphic(new ImageView(icon_button_pause_enabled));
				shapeButtonRectangle();
			}
		} else if (eventType.toString().equals("MOUSE_EXITED")) {
				this.setEffect(null);
				
				if (AudioPlayer.getInstance().getStatus() != PlayerStatus.PLAYING) {
					this.setGraphic(new ImageView(icon_button_play_disabled));
					shapeButtonCircle();
				} else {
					this.setGraphic(new ImageView(icon_button_pause_disabled));
					shapeButtonRectangle();
				}
			} 
		}
}
