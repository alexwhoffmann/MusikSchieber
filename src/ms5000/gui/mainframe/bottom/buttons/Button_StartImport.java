package ms5000.gui.mainframe.bottom.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import ms5000.gui.mainframe.bottom.eventhandler.ButtonStartImport_EventHandler;

/**
 * This class implements the functionalities of the button to start the music library import
 */
public class Button_StartImport extends Button{
	/**
	 * The icon path
	 */
	private final String icon_button_start_import_path = "file:icons/start_import_button.png";
	
	/**
	 * The icon image
	 */
	private final Image icon_button_start_import;
	
	/**
	 * The button radius
	 */
	private final double button_radius = 23;
	
	/**
	 * Constructs the button
	 */
	public Button_StartImport() {
		// Applying the style sheet
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_borderpane_bottom.css").toExternalForm());
		this.setId("button");
		
		// Setting the button image
		icon_button_start_import = new Image(icon_button_start_import_path);
		ImageView view = new ImageView(icon_button_start_import);
		view.setFitWidth(70);
		view.setFitHeight(70);
		this.setGraphic(view);
		this.setShape(new Rectangle(button_radius, button_radius));
		
		this.setOnAction(new ButtonStartImport_EventHandler());
	}
}
