package ms5000.gui.mainframe.top.buttons;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonAdd_EventHandler;
import ms5000.tasks.readdir.ReadDirTaskManager;
import ms5000.tasks.readdir.ReadDirTaskManager.TaskStatus;

public class Button_Add extends Button{
	private final String icon_button_add_disabled_path = "file:icons/Add_Disabled.png";
	private final String icon_button_add_enabled_path = "file:icons/Add.png";
	private final String icon_button_add_cancel_path = "file:icons/cancel_icon.png";
	private final double icon_length = 23;
	private final Image icon_button_add_cancel;
	private final Image icon_button_add_disabled;
	private final Image icon_button_add_enabled;
	private final DropShadow shadow;

	public Button_Add() {
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_vbox_volumeslider.css").toExternalForm());

		ButtonAdd_EventHandler eventHandler = new ButtonAdd_EventHandler();
		this.setOnMouseEntered(eventHandler);
		this.setOnMouseExited(eventHandler);
		this.setOnMouseClicked(eventHandler);

		icon_button_add_disabled = new Image(icon_button_add_disabled_path);
		icon_button_add_enabled = new Image(icon_button_add_enabled_path);
		icon_button_add_cancel = new Image(icon_button_add_cancel_path);
		shadow = new DropShadow();

		this.setGraphic(new ImageView(icon_button_add_disabled));
		shapeButtonCircle();
	}
	
	
	public void changeButtonIcon_Rollover(MouseEvent event) {		
		if (ReadDirTaskManager.getTaskStatus() != TaskStatus.RUNNING && ReadDirTaskManager.getTaskStatus() != TaskStatus.SCHEDULED && ReadDirTaskManager.getTaskStatus() != TaskStatus.FAILED) {

			if (event.getEventType().toString().equals("MOUSE_ENTERED")) {
				this.setEffect(shadow);
				this.setGraphic(new ImageView(icon_button_add_enabled));
				shapeButtonCircle();
			} else if (event.getEventType().toString().equals("MOUSE_EXITED")) {
				this.setEffect(null);
				this.setGraphic(new ImageView(icon_button_add_disabled));
				shapeButtonCircle();
			}

		} else {
			if (event.getEventType().toString().equals("MOUSE_ENTERED")) {
				this.setEffect(shadow);
			} else if (event.getEventType().toString().equals("MOUSE_EXITED")) {
				this.setEffect(null);
			}

		}
	}
	
	private void shapeButtonCircle() {
		this.setShape(new Circle(icon_length));
	}
	
	public void changeButtonIcon(TaskStatus taskStatus) {

		if (taskStatus == TaskStatus.RUNNING) {
			this.setGraphic(new ImageView(icon_button_add_cancel));
			shapeButtonCircle();
		} else {
			this.setGraphic(new ImageView(icon_button_add_disabled));
			shapeButtonCircle();
		}

	}
}	
