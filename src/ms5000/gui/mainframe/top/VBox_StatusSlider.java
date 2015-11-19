package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class VBox_StatusSlider extends VBox{
	private TextField status_text;
	private Slider progress_slider;
	
	public VBox_StatusSlider() {
		status_text = new TextField("test");
		status_text.setEditable(false);
		status_text.setAlignment(Pos.CENTER);
		status_text.setMaxSize(400, 30);
		status_text.setMinSize(200, 30);
		status_text.setStyle("-fx-background-color:#A7FFA7");
		
		progress_slider = new Slider();
		progress_slider.setMinWidth(500);
		
		this.getChildren().add(status_text);
		this.getChildren().add(progress_slider);
		
		this.setPadding(new Insets(5, 12, 15, 12));
		this.setSpacing(5);
		
		this.setAlignment(Pos.CENTER);
		this.setMinSize(600, 100);
		
	}
	
	public void setStatusText(String text) {
		this.status_text.setText(text);
	}
}
