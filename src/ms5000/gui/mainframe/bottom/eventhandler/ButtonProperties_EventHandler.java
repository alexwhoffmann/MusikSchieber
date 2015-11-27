package ms5000.gui.mainframe.bottom.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ms5000.gui.profile.ProfileSettings;

public class ButtonProperties_EventHandler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		ProfileSettings settings = new ProfileSettings();
		settings.showPropertiesPage();

		//hide this current window (if this is whant you want
		//((Node)(event.getSource())).getScene().getWindow().hide();
	}
}
