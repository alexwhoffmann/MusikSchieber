package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;

public class HBox_TOP_LEFT extends HBox {
	private Button btn_Stop;
	private static final String icon_button_stop_path = "file:icons/Stop_Disabled.png";
	private static Image icon_button_stop;
	private static final double button_radius = 23;

	public HBox_TOP_LEFT() {
		icon_button_stop = new Image(icon_button_stop_path);
		btn_Stop = new Button();
		btn_Stop.setStyle("-fx-focus-color: transparent;");
		btn_Stop.setStyle("-fx-background-color: transparent;");
		btn_Stop.setGraphic(new ImageView(icon_button_stop));
		btn_Stop.setShape(new Circle(button_radius));
		btn_Stop.setMinSize(2*button_radius, 2*button_radius);
		btn_Stop.setMaxSize(2*button_radius, 2*button_radius);
		btn_Stop.setOnMouseEntered(new ButtonStartStop_EventHandler("Stop",btn_Stop));
		btn_Stop.setOnMouseExited(new ButtonStartStop_EventHandler("Stop",btn_Stop));
		btn_Stop.setOnMouseClicked(new ButtonStartStop_EventHandler("Stop",btn_Stop));
		
		this.setPadding(new Insets(35, 12, 15, 12));
		this.setSpacing(10);

		this.getChildren().addAll(new Button_Start(), btn_Stop);
		this.setStyle("-fx-background-color:#5A5A5A");
		
		this.setPrefWidth(220.0);
		this.setMinWidth(126.0);
	}
}
