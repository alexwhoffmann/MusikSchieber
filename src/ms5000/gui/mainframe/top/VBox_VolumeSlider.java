package ms5000.gui.mainframe.top;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ms5000.gui.mainframe.top.sliderbar.SliderBar;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.gui.GuiProperties;
import ms5000.properties.icons.IconProperties;

/**
 * This vbox holds the volume slider
 */
public class VBox_VolumeSlider extends VBox{
	/**
	 * The volume label
	 */
	private final Label volume;
	
	/**
	 * The volume slider
	 */
	private SliderBar volumeSlider;
	
	/**
	 * Instantiates the vbox
	 */
	public VBox_VolumeSlider() {
		// Setting the style sheet
		String cssPath = PropertiesUtils.getString("top.section.config.vbox.volumeslider.css");
		this.getStylesheets().add(this.getClass().getResource(cssPath).toExternalForm());
		
		// Preparing the Label
		volume = new Label();
		volume.setGraphic(new ImageView(PropertiesUtils.getProperty(IconProperties.VOLUME)));
		
		// Preparing the Slider
		try {
			Double volume = Double.parseDouble(PropertiesUtils.getProperty(GuiProperties.VOLUME));
			volumeSlider = new SliderBar(volume);
		} catch (Exception e) {
			volumeSlider = new SliderBar(50.0);
		}
		
		// Adding the ChangeListener 
		volumeSlider.sliderValueProperty().addListener(getChangeListener());
		
		// Adding the contents
		this.getChildren().add(volume);
		this.getChildren().add(volumeSlider);
		this.setAlignment(Pos.CENTER);
	}
	
	/**
	 * @return Instance of the volume slider
	 */
	public SliderBar getVolumeSlider() {
		return volumeSlider;
	}
	
	/**
	 * Returning a new change listener for the volume slider that keeps track of the last used volume level
	 *  
	 * @return the change listener 
	 */
	public ChangeListener<Number> getChangeListener() {
		return new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				PropertiesUtils.setProperty(GuiProperties.VOLUME, newValue.toString());
			}
	    };
	}
	
}
