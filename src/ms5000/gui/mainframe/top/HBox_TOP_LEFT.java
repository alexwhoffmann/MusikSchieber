package ms5000.gui.mainframe.top;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import ms5000.gui.mainframe.top.buttons.Button_Start;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_EventHandler;
import ms5000.gui.mainframe.top.eventhandler.ButtonStartStop_MouseEventHandler;

public class HBox_TOP_LEFT extends HBox {
	private static Button btn_Stop;
	private static Button_Start btn_Start;
	private final String icon_button_stop_path = "file:icons/Stop_Disabled.png";
	private Image icon_button_stop;
	private final double button_radius = 23;

	public HBox_TOP_LEFT() {
		icon_button_stop = new Image(icon_button_stop_path);
		btn_Stop = new Button();
		btn_Stop.setStyle("-fx-focus-color: transparent;");
		btn_Stop.setStyle("-fx-background-color: transparent;");
		btn_Stop.setGraphic(new ImageView(icon_button_stop));
		btn_Stop.setShape(new Circle(button_radius));
		btn_Stop.setMinSize(2*button_radius, 2*button_radius);
		btn_Stop.setMaxSize(2*button_radius, 2*button_radius);
		btn_Stop.setOnAction(new ButtonStartStop_EventHandler());
		btn_Stop.setOnMouseEntered(new ButtonStartStop_MouseEventHandler());
		btn_Stop.setOnMouseExited(new ButtonStartStop_MouseEventHandler());
		btn_Start = new Button_Start();
		
		
		this.setPadding(new Insets(35, 12, 15, 12));
		this.setSpacing(10);

		this.getChildren().addAll(btn_Start, btn_Stop);
		this.setStyle("-fx-background-color:#5A5A5A");
		
		btn_Start.setDisable(true);
		btn_Stop.setDisable(true);
		
		this.setPrefWidth(220.0);
		this.setMinWidth(126.0);
	}

	public static Button getBtn_Stop() {
		return btn_Stop;
	}

	public static Button_Start getBtn_Start() {
		return btn_Start;
	}
}
