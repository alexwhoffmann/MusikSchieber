package ms5000.gui.mainframe.top.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.top.buttons.Button_Start;

/**
 * Class that implements the mouse event handler for the buttons start and stop
 */
public class ButtonStartStop_MouseEventHandler implements EventHandler<MouseEvent>{
	
	@Override
	public void handle(MouseEvent event) {
		// Changing the rollover icons
		if (event.getSource() instanceof Button_Start) {
			Main_Frame.gethBox_Left().getBtnStart().changeButtonIcon_Rollover(event.getEventType());
		} else {
			Main_Frame.gethBox_Left().getBtnStop().changeButtonIcon_Rollover(event.getEventType());
		}
	}
}
