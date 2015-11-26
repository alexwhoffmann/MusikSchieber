package ms5000.gui.mainframe.top;

import javafx.scene.layout.HBox;
import ms5000.gui.mainframe.top.buttons.Button_Start;
import ms5000.gui.mainframe.top.buttons.Button_Stop;

/**
 * This HBox holds the items on the top left side of the frame
 */
public class HBox_TOP_LEFT extends HBox {
	/**
	 * Button stop song
	 */
	private final Button_Stop btn_Stop;
	
	/**
	 * Button play song
	 */
	private final Button_Start btn_Start;

	/**
	 * Instantiates the HBox
	 */
	public HBox_TOP_LEFT() {		
		// applying the style sheet
		this.getStylesheets().add(this.getClass().getResource("css/mainframetop_borderpane_top_center.css").toExternalForm());
		this.setId("hboxTopLeft");
		
		// adding the buttons to the frame
		btn_Stop = new Button_Stop();
		btn_Start = new Button_Start();
		this.getChildren().addAll(btn_Start, btn_Stop);
		
		btn_Start.setDisable(true);
		btn_Stop.setDisable(true);
	}
	
	/**
	 * @return Instance of the Button Stop
	 */
	public Button_Stop getBtnStop() {
		return btn_Stop;
	}
	
	/**
	 * @return Instance of the Button Start
	 */
	public Button_Start getBtnStart() {
		return btn_Start;
	}
}
