package ms5000.gui.mainframe.top.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ms5000.audio.player.AudioPlayer;
import ms5000.audio.player.PlayerStatus;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.gui.mainframe.top.HBox_TOP_LEFT;
import ms5000.gui.mainframe.top.buttons.Button_Start;
import ms5000.musicfile.tag.MusicTag;

public class ButtonStartStop_EventHandler implements EventHandler<ActionEvent>{
	private final String icon_button_start_enabled_path = "file:icons/Play.png";
	private final String icon_button_pause_enabled_path = "file:icons/Pause.png";
	private Image icon_button_start_enabled;
	private Image icon_button_pause_enabled;
	private final double button_radius = 22;
	
	public ButtonStartStop_EventHandler() {
		icon_button_start_enabled = new Image(icon_button_start_enabled_path);
		icon_button_pause_enabled = new Image(icon_button_pause_enabled_path);
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() instanceof Button_Start) {
			changeButtonIcon_PlayPressed((Button) event.getSource(), false);

			if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PAUSED
					|| AudioPlayer.getInstance().getStatus() == PlayerStatus.READY) {
				playTune();
				HBox_TOP_LEFT.getBtn_Start().setPlaying(true);
			} else if (HBox_TOP_LEFT.getBtn_Start().isPlaying() == true) {
				HBox_TOP_LEFT.getBtn_Start().setPlaying(false);
				pauseTune();
			}

		} else {
			if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PLAYING || AudioPlayer.getInstance().getStatus() == PlayerStatus.PAUSED) {
				AudioPlayer.getInstance().setStatus(PlayerStatus.READY);
				AudioPlayer.getInstance().stop();
				changeButtonIcon_PlayPressed((Button) event.getSource(), true);
			}
		}
	}
	
	private void playTune() {
		AudioPlayer.getInstance().play();
	}
	
	private void pauseTune() {
		AudioPlayer.getInstance().pause();
	}

	
	
	private void changeButtonIcon_PlayPressed(Button button, Boolean stopPressed) {
		if (button instanceof Button_Start && !stopPressed) {
			if(!((Button_Start) button).isPlaying() && !stopPressed) {
				button.setGraphic(new ImageView(icon_button_pause_enabled));
				shapeButtonRectangle(button);
			} else {
				button.setGraphic(new ImageView(icon_button_start_enabled));
				shapeButtonCircle(button);
			}
		} else if(stopPressed){
			HBox_TOP_LEFT.getBtn_Start().setPlaying(false);
			HBox_TOP_LEFT.getBtn_Start().setGraphic(new ImageView(icon_button_start_enabled));
			shapeButtonCircle(HBox_TOP_LEFT.getBtn_Start());
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
