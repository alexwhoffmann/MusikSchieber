package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import ms5000.gui.mainframe.top.buttons.Button_Add;

/**
 * This HBox holds the items on the top right side of the frame
 */
public class HBox_TOP_RIGHT extends HBox{
	/**
	 * The ImportButton
	 */
	private Button_Add btn_import_data;
	
	/**
	 * The VolumeSlider
	 */
	private VBox_VolumeSlider volumeSlider;
	
	/**
	 * Instantiates the HBox
	 */
	public HBox_TOP_RIGHT() {
		btn_import_data = new Button_Add();
		volumeSlider = new VBox_VolumeSlider();
		
		this.getChildren().add(volumeSlider);
		this.getChildren().add(btn_import_data);
		
		// Setting the style
		this.setPadding(new Insets(37, 12, 15, 12));
		this.setSpacing(10);
		this.setStyle("-fx-background-color:#5A5A5A");
	}
	
	/**
	 * @return returns the instance of the volume slider
	 */
	public VBox_VolumeSlider getVolumeSlider() {
		return volumeSlider;
	}
	
	/**
	 * @return returns the instance of the button import
	 */
	public Button_Add getBtnImportData() {
		return btn_import_data;
	}
}
