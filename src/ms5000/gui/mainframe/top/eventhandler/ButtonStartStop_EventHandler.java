package ms5000.gui.mainframe.top.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ms5000.audio.player.AudioPlayer;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.gui.mainframe.top.Button_Start;
import ms5000.gui.mainframe.top.eventhandler.ButtonAdd_EventHandler.TaskStatus;
import ms5000.musicfile.file.MusicFile;

public class ButtonStartStop_EventHandler implements EventHandler<MouseEvent>{
	private static final String icon_button_start_disabled_path = "file:icons/Play_Disabled.png";
	private static final String icon_button_stop_disabled_path = "file:icons/Stop_Disabled.png";
	private static final String icon_button_start_enabled_path = "file:icons/Play.png";
	private static final String icon_button_stop_enabled_path = "file:icons/Stop.png";
	private static final String icon_button_pause_enabled_path = "file:icons/Pause.png";
	private static final String icon_button_pause_disabled_path = "file:icons/Pause_Disabled.png";
	private static Image icon_button_start_enabled;
	private static Image icon_button_stop_enabled;
	private static Image icon_button_start_disabled;
	private static Image icon_button_stop_disabled;
	private static Image icon_button_pause_disabled;
	private static Image icon_button_pause_enabled;
	private static final double button_radius = 23;
	private String buttonName;
	private Button button;
	private DropShadow shadow;
	
	public ButtonStartStop_EventHandler(String buttonName, Button button) {
		this.buttonName = buttonName;
		this.button = button;
		
		icon_button_start_enabled = new Image(icon_button_start_enabled_path);
		icon_button_stop_enabled = new Image(icon_button_stop_enabled_path);
		
		icon_button_start_disabled = new Image(icon_button_start_disabled_path);
		icon_button_stop_disabled = new Image(icon_button_stop_disabled_path);
		
		icon_button_pause_disabled = new Image(icon_button_pause_disabled_path);
		icon_button_pause_enabled = new Image(icon_button_pause_enabled_path);
		shadow = new DropShadow();
	}
	
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType().toString().equals("MOUSE_CLICKED")) {
			if(buttonName.equals("Start")) {
				changeButtonIcon_PlayPressed(button);
				
				if (BorderPane_CENTER.getCentertable().getSelectionModel().getSelectedIndices().size() == 1) {
					playTune(BorderPane_CENTER.getCentertable().getSelectionModel()
							.getSelectedItem().getMusicFile());
				}
			} else if (buttonName.equals("Stop")) {
				if(ButtonAdd_EventHandler.getTaskStatus() == TaskStatus.RUNNING || ButtonAdd_EventHandler.getTaskStatus() == TaskStatus.SCHEDULED) {
					ButtonAdd_EventHandler.setTaskStatus(null);
					ButtonAdd_EventHandler.getTask().cancel();
				}
			}
		} else {
			changeButtonIcon_Rollover(buttonName,button,event.getEventType().toString());
		}
	}
	
	private void playTune(MusicFile musicFile) {
		AudioPlayer.getInstance().setMedia(musicFile);
		AudioPlayer.getInstance().play();
		
		BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Playing: " + musicFile.getTag().getArtist() + " - " + musicFile.getTag().getTitlename());
	}

	private void changeButtonIcon_Rollover(String button_name, Button button, String eventType) {
		if (eventType.equals("MOUSE_ENTERED")) {
			if(buttonName.equals("Start")) {
				button.setEffect(shadow);
				if (!((Button_Start) button).isPlaying()) {
					button.setGraphic(new ImageView(icon_button_start_enabled));
				} else {
					button.setGraphic(new ImageView(icon_button_pause_enabled));
				}
			} else {
				button.setEffect(shadow);
				button.setGraphic(new ImageView(icon_button_stop_enabled));
		
			}
		} else if (eventType.toString().equals("MOUSE_EXITED")) {
			if(buttonName.equals("Start")) {
				button.setEffect(null);
				if (!((Button_Start) button).isPlaying()) {
					button.setGraphic(new ImageView(icon_button_start_disabled));
				} else {
					button.setGraphic(new ImageView(icon_button_pause_disabled));
				}
			} else {
				button.setEffect(null);
				button.setGraphic(new ImageView(icon_button_stop_disabled));
			}
		}
	}
	
	private void changeButtonIcon_PlayPressed(Button button) {
		if (button instanceof Button_Start) {
			if(!((Button_Start) button).isPlaying()) {
				((Button_Start) button).setPlaying(true);
				button.setGraphic(new ImageView(icon_button_pause_enabled));
				shapeButtonRectangle(button);
			} else {
				((Button_Start) button).setPlaying(false);
				button.setGraphic(new ImageView(icon_button_start_enabled));
				shapeButtonCircle(button);
			}
		}	
	}
	
	private void shapeButtonCircle(Button button) {
		button.setShape(new Circle(button_radius));
		button.setMinSize(2*button_radius, 2*button_radius);
		button.setMaxSize(2*button_radius, 2*button_radius);
	}
	
	private void shapeButtonRectangle(Button button) {
		button.setShape(new Rectangle(2*button_radius,2*button_radius));
		button.setMinSize(2*button_radius, 2*button_radius);
		button.setMaxSize(2*button_radius, 2*button_radius);
	}
}
