package ms5000.gui.mainframe.bottom.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ms5000.gui.profile.ProfileSettings;

/**
 * This class implements the event handler for the button that shows the properties
 */
public class ButtonProperties_EventHandler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		// Showing the properties
		ProfileSettings settings = new ProfileSettings();
		settings.showPropertiesPage();
	}
}
