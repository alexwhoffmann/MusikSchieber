package ms5000.gui.mainframe.top.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ms5000.gui.mainframe.top.Button_Start;
import ms5000.gui.mainframe.top.HBox_TOP_LEFT;

public class ButtonStartStop_MouseEventHandler implements EventHandler<MouseEvent>{
	private final String icon_button_start_disabled_path = "file:icons/Play_Disabled.png";
	private final String icon_button_stop_disabled_path = "file:icons/Stop_Disabled.png";
	private final String icon_button_start_enabled_path = "file:icons/Play.png";
	private final String icon_button_stop_enabled_path = "file:icons/Stop.png";
	private final String icon_button_pause_enabled_path = "file:icons/Pause.png";
	private final String icon_button_pause_disabled_path = "file:icons/Pause_Disabled.png";
	
	private final Image icon_button_stop_enabled;
	private final Image icon_button_start_disabled;
	private final Image icon_button_stop_disabled;
	private final Image icon_button_pause_disabled;
	private final Image icon_button_start_enabled;
	private final Image icon_button_pause_enabled;
	
	private DropShadow shadow;
	public ButtonStartStop_MouseEventHandler() {
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
		
		
		if (event.getSource() instanceof Button_Start) {
			changeButtonIcon_Rollover(HBox_TOP_LEFT.getBtn_Start(),event.getEventType().toString());
		} else {
			changeButtonIcon_Rollover(HBox_TOP_LEFT.getBtn_Stop(),event.getEventType().toString());
		}
	}
	
	private void changeButtonIcon_Rollover(Button button, String eventType) {
		if (eventType.equals("MOUSE_ENTERED")) {
			if(button instanceof Button_Start) {
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
			if(button instanceof Button_Start) {
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

}
