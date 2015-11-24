package ms5000.gui.mainframe.top;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import ms5000.audio.player.AudioPlayer;
import ms5000.gui.mainframe.top.sliderbar.SliderBar;

public class VBox_VolumeSlider extends VBox{
	private Label volume;
	private static final String icon_label_volume_path = "file:icons/Volume.png";
	private static SliderBar volumeSlider;
	
	public VBox_VolumeSlider() {
		this.getStylesheets().add(this.getClass().getResource("css/mainframetop_vbox_volumeslider.css").toExternalForm());
		volume = new Label();
		volume.setGraphic(new ImageView(icon_label_volume_path));
		volumeSlider = new SliderBar(50);
		volumeSlider.sliderValueProperty().addListener(getChangeListener());
		this.getChildren().add(volume);
		this.getChildren().add(volumeSlider);
		this.setAlignment(Pos.CENTER);
	}
	
	public static SliderBar getVolumeSlider() {
		return volumeSlider;
	}
	
	private ChangeListener<Number> getChangeListener() {
		return new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number newValue, Number oldValue) {
				MediaPlayer mp = AudioPlayer.getInstance().getMediaPlayer();
				
				if(mp != null) {
					System.out.println(mp.getVolume());
				}
			}
			
		};
	}
}
