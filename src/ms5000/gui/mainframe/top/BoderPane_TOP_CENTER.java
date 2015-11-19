package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BoderPane_TOP_CENTER extends BorderPane {
	
	private TextField timeToGO;
	private TextField timeLeft;
	private VBox box_TimeToGo;
	private VBox box_TimeLeft;
	private static VBox_StatusSlider statusSlider;
	
	public BoderPane_TOP_CENTER() {
		
		timeToGO = new TextField("-10:00");
		timeLeft = new TextField("-10:00");
		timeToGO.setMaxSize(50, 15);
		timeLeft.setMaxSize(50, 15);
		statusSlider = new VBox_StatusSlider();
		
		box_TimeToGo = new VBox();
		box_TimeLeft = new VBox();
		box_TimeToGo.getChildren().add(timeToGO);
		box_TimeLeft.getChildren().add(timeLeft);
		
		BoderPane_TOP_CENTER.setMargin(box_TimeToGo, new Insets(50,8,8,8));
		BoderPane_TOP_CENTER.setMargin(box_TimeLeft, new Insets(50,8,8,8));
		BoderPane_TOP_CENTER.setAlignment(box_TimeToGo, Pos.BOTTOM_LEFT);
		BoderPane_TOP_CENTER.setAlignment(box_TimeLeft, Pos.BOTTOM_LEFT);
		
		this.setCenter(statusSlider);
		this.setLeft(box_TimeToGo);
		this.setRight(box_TimeLeft);
		this.setStyle("-fx-background-color:#999999");
	}
	
	public static VBox_StatusSlider getStatusSlider() {
		return statusSlider;
	}
}
