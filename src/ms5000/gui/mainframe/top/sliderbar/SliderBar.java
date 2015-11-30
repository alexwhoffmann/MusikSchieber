package ms5000.gui.mainframe.top.sliderbar;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

/**
 * This class implenments the slider which is used for the volume and the song
 * progress slider
 */
public class SliderBar extends StackPane {

	/**
	 * The slider
	 */
	private final Slider slider = new Slider();

	/**
	 * The underlying progress bar
	 */
	private final ProgressBar progressBar = new ProgressBar();
	
	/**
	 * Constructs the slider bar
	 * 
	 * @param init initial state of the sliderbar
	 */
	public SliderBar(double init) {
		getChildren().addAll(progressBar, slider);
		bindValues();
		slider.setValue(init);
	}
	
	/**
	 * Method to bind the progress bar to the slider
	 */
	private void bindValues() {
		progressBar.progressProperty().bind(slider.valueProperty().divide(100));
	}
	
	/**
	 * Method to return the double property of the slider 
	 * 
	 * @return the double property of the slider
	 */
	public DoubleProperty sliderValueProperty() {
		return slider.valueProperty();
	}
	
	/**
	 * Method to return a boolean indicating whether the slider value is changing
	 * 
	 * @return boolean indicating whether the slider value is changing
	 */
	public boolean isTheValueChanging() {
		return slider.isValueChanging();
	}
	
	/**
	 * Method to set the width of the progress bar
	 */
	public void setProgressBarWidth() {
		progressBar.setPrefWidth(slider.getWidth());
	}

	/**
	 * Method to return the slider instance 
	 * 
	 * @return the slider instance
	 */
	public Slider getSlider() {
		return slider;
	}
}