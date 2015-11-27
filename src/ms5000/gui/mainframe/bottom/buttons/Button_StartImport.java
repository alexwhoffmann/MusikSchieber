package ms5000.gui.mainframe.bottom.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class Button_StartImport extends Button{
	private final String icon_button_start_import_path = "file:icons/start_import_button.png";
	private final Image icon_button_start_import;
	private final double button_radius = 23;
	
	public Button_StartImport() {
		this.getStylesheets().add(this.getClass().getResource("../css/mainframetop_borderpane_bottom.css").toExternalForm());
		this.setId("button");
		icon_button_start_import = new Image(icon_button_start_import_path);
		ImageView view = new ImageView(icon_button_start_import);
		view.setFitWidth(70);
		view.setFitHeight(70);
		this.setGraphic(view);
		this.setShape(new Rectangle(button_radius, button_radius));
	}
}
