package ms5000.gui.mainframe.bottom;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.bottom.buttons.Button_Properties;
import ms5000.gui.mainframe.bottom.buttons.Button_StartImport;

public class BorderPane_Bottom extends BorderPane{
	/**
	 * the progress bar
	 */
	private final ProgressBar progressBar = new ProgressBar(0.75);
	private Button_StartImport buttonStartImport;
	private Button_Properties buttonProperties;
	private VBox vbox_center;
	private TextField statusLarge;
	private TextField statusSmall;
	private HBox hbox_left;
	private HBox hbox_right;
	
	public BorderPane_Bottom() {
		// Setting the style sheet
		this.getStylesheets().add(this.getClass().getResource("css/mainframetop_borderpane_bottom.css").toExternalForm());
		this.setId("border-pane");
		this.prefWidthProperty().bind(Main_Frame.getScene().widthProperty());
		progressBar.prefWidthProperty().bind(Main_Frame.getScene().widthProperty());
		this.setTop(progressBar);
		
		buttonStartImport = new Button_StartImport();
		buttonProperties = new Button_Properties();
		
		vbox_center = new VBox();
		vbox_center.setId("vbox");
		
		statusLarge = new TextField("Test");
		statusLarge.setEditable(false);
		statusLarge.setId("textfield_large");
		statusLarge.setAlignment(Pos.CENTER);
		vbox_center.getChildren().add(statusLarge);
		
		statusSmall = new TextField("Test");
		statusSmall.setEditable(false);
		statusSmall.setId("textfield_small");
		vbox_center.getChildren().add(statusSmall);
		
		hbox_left = new HBox();
		hbox_left.prefWidthProperty().bind(Main_Frame.gethBox_Left().widthProperty());
		hbox_left.setId("hbox");
		
		
		hbox_right = new HBox();
		hbox_right.prefWidthProperty().bind(Main_Frame.gethBox_Right().widthProperty());
		hbox_right.getChildren().addAll(buttonStartImport,buttonProperties);
		hbox_right.setId("hbox");
		
		this.setRight(hbox_right);
		this.setLeft(hbox_left);
		this.setCenter(vbox_center);
	}
}
