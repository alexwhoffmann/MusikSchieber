package ms5000.gui.mainframe.top;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class VBox_VolumeSlider extends VBox{
	private Label volume;
	private Slider volume_slider;
	private static final String icon_label_volume_path = "file:icons/Volume.png";
	
	public VBox_VolumeSlider() {
		volume = new Label();
		volume.setGraphic(new ImageView(icon_label_volume_path));
		volume_slider = new Slider();
		
		this.getChildren().add(volume);
		this.getChildren().add(volume_slider);
		this.setAlignment(Pos.CENTER);
	}
}
