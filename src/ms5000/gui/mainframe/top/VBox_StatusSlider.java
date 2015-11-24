package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ms5000.gui.mainframe.top.eventhandler.Slider_ValueChange_Listener;
import ms5000.gui.mainframe.top.sliderbar.SliderBar;

public class VBox_StatusSlider extends VBox{
	private TextField status_text;
	private static SliderBar progress_slider;
	
	public VBox_StatusSlider() {
		this.getStylesheets().add(this.getClass().getResource("css/mainframetop_vbox_statusslider.css").toExternalForm());
		
		status_text = new TextField("");
		status_text.setEditable(false);
		status_text.setAlignment(Pos.CENTER);
		status_text.setMaxSize(400, 30);
		status_text.setMinSize(200, 30);
		
		progress_slider = new SliderBar(0);
		progress_slider.getSlider().setDisable(true);
		progress_slider.setMinWidth(500);
		progress_slider.sliderValueProperty().addListener(new Slider_ValueChange_Listener(progress_slider.getSlider()));
		
		
		this.getChildren().add(status_text);
		this.getChildren().add(progress_slider);
		
		this.setPadding(new Insets(5, 12, 15, 12));
		this.setSpacing(5);
		
		this.setAlignment(Pos.CENTER);
		this.setMinSize(600, 100);
		
		progress_slider.setProgressBarWidth();
		
		
	}
	
	public void setStatusText(String text) {
		this.status_text.setText(text);
	}
	
	public static SliderBar getSlider() {
		return progress_slider;
	}
}
