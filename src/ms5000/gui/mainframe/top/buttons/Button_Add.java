package ms5000.gui.mainframe.top.buttons;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonAdd_EventHandler;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.icons.IconProperties;
import ms5000.tasks.readdir.ReadDirTaskManager;
import ms5000.tasks.readdir.ReadDirTaskManager.TaskStatus;

/**
 * This class implements the functionalities of the add button
 */
public class Button_Add extends Button{
	
	/**
	 * The icon images
	 */
	private final Image icon_button_add_cancel = new Image(PropertiesUtils.getProperty(IconProperties.CANCELIMPORT));
	private final Image icon_button_add_disabled = new Image(PropertiesUtils.getProperty(IconProperties.ADDDISABLED));
	private final Image icon_button_add_enabled = new Image(PropertiesUtils.getProperty(IconProperties.ADDENABLED));
	
	/**
	 * The drop shadow
	 */
	private final DropShadow shadow;
	
	/**
	 * The icon width
	 */
	private final double icon_width = 23;
	
	/**
	 * The button event handler
	 */
	private final ButtonAdd_EventHandler eventHandler = new ButtonAdd_EventHandler();
	
	/**
	 * Construction the button
	 */
	public Button_Add() {
		// Applying the stylesheet
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_vbox_volumeslider.css").toExternalForm());
		
		// Adding the event handler
		this.setOnMouseEntered(eventHandler);
		this.setOnMouseExited(eventHandler);
		this.setOnMouseClicked(eventHandler);
		
		shadow = new DropShadow();
		
		// Applying the icons and shaping the button
		this.setGraphic(new ImageView(icon_button_add_disabled));
		shapeButtonCircle();
	}
	
	/**
	 * Method to change the button icon corresponding to the received event
	 * 
	 * @param event Event
	 */
	public void changeButtonIcon_Rollover(MouseEvent event) {		
		// Task ins't running
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
		// Task is running	
		} else {
			if (event.getEventType().toString().equals("MOUSE_ENTERED")) {
				this.setEffect(shadow);
			} else if (event.getEventType().toString().equals("MOUSE_EXITED")) {
				this.setEffect(null);
			}

		}
	}
	
	/**
	 * Method to shape the button as circle
	 */
	private void shapeButtonCircle() {
		this.setShape(new Circle(icon_width));
	}
	
	/**
	 * Method to switch between the button icon
	 * 
	 * @param taskStatus the task status
	 */
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
