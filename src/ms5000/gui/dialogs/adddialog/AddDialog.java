package ms5000.gui.dialogs.adddialog;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import ms5000.tasks.readdir.ImportMode;

public class AddDialog extends Alert{	
	private ImportMode importMode;
	
	public AddDialog(AlertType confirmation) {
		super(confirmation);
		this.setTitle("Import Options");
		this.setHeaderText("There are already entries in your list.");
		this.setContentText("Do you want to add further entries to the list?");
	
		ButtonType buttonAppend = new ButtonType("Append List");
		ButtonType buttonClear = new ButtonType("Clear List");
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		this.getButtonTypes().setAll(buttonAppend, buttonClear, buttonCancel);
		
		Optional<ButtonType> result = this.showAndWait();
		if (result.get() == buttonAppend){
		    System.out.println("1");
		    importMode = ImportMode.APPEND;
		} else if (result.get() == buttonClear) {
			System.out.println("2");
			importMode = ImportMode.CLEAR;
		} else if (result.get() == buttonCancel) {
			System.out.println("3");
			importMode = ImportMode.CANCEL;
		}
	}
	
	public ImportMode getImportMode() {
		return importMode;
	}
}
