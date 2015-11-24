package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import ms5000.gui.mainframe.top.eventhandler.ButtonAdd_EventHandler;

public class HBox_TOP_RIGHT extends HBox{
	private Button btn_import_data;
	private final String icon_button_add_disabled_path = "file:icons/Add_Disabled.png";
	private final Image icon_button_add_disabled;
	private final double icon_length = 23;
	private static VBox_VolumeSlider volumeSlider;
	
	public HBox_TOP_RIGHT() {
		btn_import_data = new Button();
		icon_button_add_disabled = new Image(icon_button_add_disabled_path);
		btn_import_data.setStyle("-fx-focus-color: transparent;");
		btn_import_data.setStyle("-fx-background-color: transparent;");
		btn_import_data.setGraphic(new ImageView(icon_button_add_disabled));
		btn_import_data.setShape(new Rectangle(icon_length,icon_length));
		btn_import_data.setMinSize(2*icon_length, 2*icon_length);
		btn_import_data.setMaxSize(2*icon_length, 2*icon_length);
		
		btn_import_data.setOnMouseEntered(new ButtonAdd_EventHandler(btn_import_data));
		btn_import_data.setOnMouseExited(new ButtonAdd_EventHandler(btn_import_data));
		btn_import_data.setOnMouseClicked(new ButtonAdd_EventHandler(btn_import_data));
		
		volumeSlider = new VBox_VolumeSlider();
		this.getChildren().add(volumeSlider);
		this.getChildren().add(btn_import_data);
		
		this.setPadding(new Insets(37, 12, 15, 12));
		this.setSpacing(10);
		this.setStyle("-fx-background-color:#5A5A5A");
	}
	
	public static VBox_VolumeSlider getVolumeSlider() {
		return volumeSlider;
	}
}
