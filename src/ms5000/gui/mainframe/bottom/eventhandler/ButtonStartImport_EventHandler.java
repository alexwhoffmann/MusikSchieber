package ms5000.gui.mainframe.bottom.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ms5000.tasks.importfiles.ImportFilesTaskManager;

/**
 * This class implements the event handler for the button which starts the import in to the music library
 */
public class ButtonStartImport_EventHandler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		// Starting the import task
		ImportFilesTaskManager.startTask();
	}
}
