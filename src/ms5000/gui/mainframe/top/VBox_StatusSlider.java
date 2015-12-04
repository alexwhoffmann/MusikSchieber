package ms5000.gui.mainframe.top;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ms5000.gui.mainframe.top.eventhandler.Slider_ValueChange_Listener;
import ms5000.gui.mainframe.top.sliderbar.SliderBar;
import ms5000.properties.PropertiesUtils;

/**
 * This class holds the StatusSlider and the status text field in the middle of the frame
 */
public class VBox_StatusSlider extends VBox{
	/**
	 * Text field that holds the status text
	 */
	private final TextField status_text;
	
	/**
	 * The song - progress slider
	 */
	private final SliderBar progress_slider;
	
	/**
	 * Instantiates the vbox 
	 */
	public VBox_StatusSlider() {
		// Setting the style sheet
		String cssPath = PropertiesUtils.getString("top.section.config.vbox.statusslider.css");
		this.getStylesheets().add(this.getClass().getResource(cssPath).toExternalForm());
				
		// Adding the contents to the vbox 
		status_text = new TextField("");
		status_text.setEditable(false);
		
		progress_slider = new SliderBar(0);
		progress_slider.getSlider().setDisable(true);
		progress_slider.sliderValueProperty().addListener(new Slider_ValueChange_Listener(progress_slider.getSlider()));
	
		this.getChildren().add(status_text);
		this.getChildren().add(progress_slider);
		
		progress_slider.setProgressBarWidth();
	}
	
	/**
	 * Method to set the status text
	 * 
	 * @param text: the text that will be set in the text field
	 */
	public void setStatusText(String text) {
		this.status_text.setText(text);
	}
	
	/**
	 * @return instance of the progress slider
	 */
	public SliderBar getSlider() {
		return progress_slider;
	}
	
	/**
	 * @return instance of the status textfield
	 */
	public TextField getStatusTextField() {
		return status_text;
	}
}
