package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import ms5000.gui.mainframe.top.buttons.Button_Add;
import ms5000.gui.mainframe.top.eventhandler.ButtonAdd_EventHandler;

public class HBox_TOP_RIGHT extends HBox{
	private Button_Add btn_import_data;
	private static VBox_VolumeSlider volumeSlider;
	
	public HBox_TOP_RIGHT() {
		
		ButtonAdd_EventHandler eventHandler = new ButtonAdd_EventHandler();
		
		btn_import_data = new Button_Add();
		btn_import_data.setOnMouseEntered(eventHandler);
		btn_import_data.setOnMouseExited(eventHandler);
		btn_import_data.setOnMouseClicked(eventHandler);
		
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

	public Button_Add getBtn_import_data() {
		return btn_import_data;
	}
}
