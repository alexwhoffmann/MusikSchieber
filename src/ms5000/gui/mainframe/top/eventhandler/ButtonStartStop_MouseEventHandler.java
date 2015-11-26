package ms5000.gui.mainframe.top.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.top.buttons.Button_Start;

public class ButtonStartStop_MouseEventHandler implements EventHandler<MouseEvent>{
	
	@Override
	public void handle(MouseEvent event) {
		if (event.getSource() instanceof Button_Start) {
			Main_Frame.gethBox_Left().getBtnStart().changeButtonIcon_Rollover(event.getEventType().toString());
		} else {
			Main_Frame.gethBox_Left().getBtnStop().changeButtonIcon_Rollover(event.getEventType().toString());
		}
	}
}
