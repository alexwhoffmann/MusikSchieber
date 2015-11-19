package ms5000.gui.mainframe.center.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.gui.mainframe.center.CenterGridPane.TextFieldKey;
import ms5000.gui.mainframe.center.CenterTable;

public class CenterGridPane_ButtonSave_EventHandler implements EventHandler<ActionEvent> {
	private final String ERROR_NUMBERFORMAT_EXCEPTION = "Please enter numeric values!"; 
	
	@Override
	public void handle(ActionEvent event) {
		if (event.getEventType().toString().equals("ACTION")) {
			CenterTable centerTable = BorderPane_CENTER.getCentertable();
			CenterGridPane centerGrid = BorderPane_CENTER.getCenterGridPane();

			if (centerTable.getSelectionModel().getSelectedIndices().size() == 1) {

				try {
					int discNumber = Integer.parseInt(centerGrid.getTextField(TextFieldKey.DISCNUMBER).getText());
					int totalDiscs = Integer.parseInt(centerGrid.getTextField(TextFieldKey.DISCNUMBER_TOTAL).getText());
					int totalTitles = Integer.parseInt(centerGrid.getTextField(TextFieldKey.TITLENUMBER_TOTAL).getText());
					int titelNumber = Integer.parseInt(centerGrid.getTextField(TextFieldKey.TITLENUMBER).getText());
					int year = Integer.parseInt(centerGrid.getTextField(TextFieldKey.YEAR).getText());

					setEntries(centerTable, centerGrid, discNumber, totalDiscs, totalTitles, titelNumber, year);
				} catch (NumberFormatException e) {
					openErrorDialog(ERROR_NUMBERFORMAT_EXCEPTION);
				}

			}
		}
	}

	private void setEntries(CenterTable centerTable, CenterGridPane centerGrid, int discNumber, int totalDiscs,
			int totalTitles, int titelNumber, int year) {
		// Writing the entries
		centerTable.getSelectionModel().getSelectedItem().setArtist(centerGrid.getTextField(TextFieldKey.ARTIST).getText());
		centerTable.getSelectionModel().getSelectedItem().setAlbum(centerGrid.getTextField(TextFieldKey.ALBUM).getText());
		centerTable.getSelectionModel().getSelectedItem().setAlbumArtist(centerGrid.getTextField(TextFieldKey.ALBUM_ARTIST).getText());
		centerTable.getSelectionModel().getSelectedItem().setComment(centerGrid.getTextField(TextFieldKey.COMMENT).getText());
		centerTable.getSelectionModel().getSelectedItem().setComposer(centerGrid.getTextField(TextFieldKey.COMPOSER).getText());
		centerTable.getSelectionModel().getSelectedItem().setDisc_number(discNumber);
		centerTable.getSelectionModel().getSelectedItem().setGenre(centerGrid.getTextField(TextFieldKey.GENRE).getText());
		centerTable.getSelectionModel().getSelectedItem().setTitlename(centerGrid.getTextField(TextFieldKey.TITLENAME).getText());
		centerTable.getSelectionModel().getSelectedItem().setTitlenumber(titelNumber);
		centerTable.getSelectionModel().getSelectedItem().setTotal_discs(totalDiscs);
		centerTable.getSelectionModel().getSelectedItem().setTotal_titles(totalTitles);
		centerTable.getSelectionModel().getSelectedItem().setYear(year);
	}
	
	private void openErrorDialog(String dialogText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error occured!");
		alert.setContentText(dialogText);

		alert.showAndWait();
	}

}
