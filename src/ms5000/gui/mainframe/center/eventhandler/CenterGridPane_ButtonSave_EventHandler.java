package ms5000.gui.mainframe.center.eventhandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jaudiotagger.tag.datatype.Artwork;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.gui.mainframe.center.CenterGridPane.TextFieldKey;
import ms5000.gui.mainframe.center.CenterTable;
import ms5000.musicfile.tag.TagUtils;

/**
 * This class implements the main functionality of the button for saving the tag
 * which is located in the grid pane on the right hand side of the frame
 */
public class CenterGridPane_ButtonSave_EventHandler implements EventHandler<ActionEvent> {
	
	/**
	 * The error strings
	 */
	private final String ERROR_NUMBERFORMAT_EXCEPTION = "Please enter numeric values!";
	private final String ERROR_UNREALISTIC_NUMBERS = "Please choose realsitic entries!";
	private final String ERROR_TITLENUMBER = "The Titlenumber should be smaller than the total titlenumber.";
	private final String ERROR_DISCNUMBER = "The Discnumber should be smaller than the total discnumber.";
	private final String ERROR_DISCNUMBER_TITLENUMBER = "Please check title- and discnumber.";
	
	/**
	 * Handle the event when the buttons is pressed
	 */
	@Override
	public void handle(ActionEvent event) {
		CenterTable centerTable = Main_Frame.getBorderPane_Center().getCentertable();
		CenterGridPane centerGrid = Main_Frame.getBorderPane_Center().getCenterGridPane();
		
		// Checking whether a row is selected
		if (event.getEventType().toString().equals("ACTION")
				&& centerTable.getSelectionModel().getSelectedIndices().size() > 0) {
			try {
				boolean discNumberOk = true;
				boolean titleNumberOk = true;
				
				// Checking whether the numeric entries are valid
				int discNumber = parseInteger(centerGrid.getTextField(TextFieldKey.DISCNUMBER).getText());
				int totalDiscs = parseInteger(centerGrid.getTextField(TextFieldKey.DISCNUMBER_TOTAL).getText());
				int totalTitles = parseInteger(centerGrid.getTextField(TextFieldKey.TITLENUMBER_TOTAL).getText());
				int titelNumber = parseInteger(centerGrid.getTextField(TextFieldKey.TITLENUMBER).getText());
				int year = parseInteger(centerGrid.getTextField(TextFieldKey.YEAR).getText());
				
				if (totalDiscs < discNumber) {
					if ((totalDiscs != 0 && discNumber == 0) || (totalDiscs == 0 && discNumber != 0)) {
						discNumberOk = false;
					}
				}

				if (titelNumber > totalTitles) {
					if((totalTitles != 0 && titelNumber == 0) && (totalTitles == 0 && titelNumber != 0)) {
						titleNumberOk = false;
					}
				}
				
				// Calling different methods when one or more entries are selected
				if (checkNumbers(discNumber, totalDiscs, totalTitles, titelNumber, year) && discNumberOk && titleNumberOk) {
					if (centerTable.getSelectionModel().getSelectedIndices().size() == 1) {
						setEntriesSingle(centerTable, centerGrid, discNumber, totalDiscs, totalTitles, titelNumber,year);
						Main_Frame.getBorderPane_Center().getCenterGridPane().setTextFieldColorProfile();
					} else if (centerTable.getSelectionModel().getSelectedIndices().size() > 1) {
						setEntriesMultiple(centerTable, centerGrid, discNumber, totalTitles, totalDiscs, year);
					}
				// Error dialogs 	
				} else if (!discNumberOk && !titleNumberOk) {
					openErrorDialog(ERROR_DISCNUMBER_TITLENUMBER);
				} else if (!discNumberOk) {
					openErrorDialog(ERROR_DISCNUMBER);
				} else if (!titleNumberOk) {
					openErrorDialog(ERROR_TITLENUMBER);
				} else {
					openErrorDialog(ERROR_UNREALISTIC_NUMBERS);
				}

			} catch (NumberFormatException e) {
				openErrorDialog(ERROR_NUMBERFORMAT_EXCEPTION);
			}

		}
	}
	
	/**
	 * Method for saving the tags when multiple entries are selected
	 * 
	 * @param centerTable an instance of the center table to access the tag instances
	 * @param centerGrid an instance of the center grid to access the values of the text fields
	 * @param discNumber the checked disc number 
	 * @param totalTitles the checked total title number
	 * @param totalDiscs the checked total disc number
	 * @param year the checked album year
	 */
	private void setEntriesMultiple(CenterTable centerTable, CenterGridPane centerGrid, int discNumber, int totalTitles,
			int totalDiscs, int year) {
		
		String artist = centerGrid.getTextField(TextFieldKey.ARTIST).getText();
		String album = centerGrid.getTextField(TextFieldKey.ALBUM).getText();
		String albumArtist = centerGrid.getTextField(TextFieldKey.ALBUM_ARTIST).getText();
		String comment = centerGrid.getTextField(TextFieldKey.COMMENT).getText();
		String composer = centerGrid.getTextField(TextFieldKey.COMPOSER).getText();
		String genre = centerGrid.getTextField(TextFieldKey.GENRE).getText();
		Artwork artwork = centerGrid.getArtwork();
		
		// Receiving the original state to determine which field has changed
		OriginalState state = OriginalState.getInstance();
		
		// Iterating through the entries
		// If an field was changed, it is getting stored in all instances
		for(Integer position : centerTable.getSelectionModel().getSelectedIndices()) {
			// Writing the entries
			if (!state.getArtist().equals(artist)) {
				centerTable.getItems().get(position.intValue()).setArtist(artist);
			}
			
			if (!state.getAlbum().equals(album)) {
				centerTable.getItems().get(position.intValue()).setAlbum(album);
			}
			
			if (!state.getAlbum_artist().equals(albumArtist)) {
				centerTable.getItems().get(position.intValue()).setAlbumArtist(albumArtist);
			}
			
			if (!state.getComment().equals(comment)) {
				centerTable.getItems().get(position.intValue()).setComment(comment);
			}
			
			if (!state.getComposer().equals(composer)) {
				centerTable.getItems().get(position.intValue()).setComposer(composer);
			}
			
			if (!state.getDiscNumber().equals("" + discNumber)) {
				centerTable.getItems().get(position.intValue()).setDisc_number(discNumber);	
			}
			
			if (!state.getGenre().equals(genre)) {
				centerTable.getItems().get(position.intValue()).setGenre(genre);
			}
			
			if (!state.getTotalDiscNumber().equals("" + totalDiscs)) {
				centerTable.getItems().get(position.intValue()).setTotal_discs(totalDiscs);
			}
			
			if (!state.getTitlesTotal().equals("" + totalTitles)) {
				centerTable.getItems().get(position.intValue()).setTotal_titles(totalTitles);
			}
			
			if(!state.getYear().equals("" + year)) {
				centerTable.getItems().get(position.intValue()).setYear(year);
			}
			
			if(artwork != null) {
				centerTable.getItems().get(position.intValue()).setArtwork(artwork);
			}
			
			// determine new tag state
			TagUtils.determineTagState(centerTable.getItems().get(position.intValue())); 
		}
		
		// Refreshing the table
		centerTable.refresh();
	}
	
	/**
	 * Method to store the tag when only one entry is chosen
	 * 
	 * @param centerTable an instance of the center table to access the tag instances
	 * @param centerGrid an instance of the center grid to access the values of the text fields
	 * @param discNumber the checked disc number 
	 * @param totalTitles the checked total title number
	 * @param totalDiscs the checked total disc number
	 * @param year the checked album year
	 * @param titlenumber the checked title number
	 */
	private void setEntriesSingle(CenterTable centerTable, CenterGridPane centerGrid, int discNumber, int totalDiscs,
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
		
		// determine new tag state
		TagUtils.determineTagState(centerTable.getSelectionModel().getSelectedItem()); 
		
		// Refreshing the table
		centerTable.refresh();
		
		// Refreshing the detail view
		Main_Frame.getBorderPane_Center().getCenterGridPane().refershTextFieldColorProfile();
	}
	
	/**
	 * Method for opening the error dialog 
	 * 
	 * @param dialogText the error message which is displayed to the user
	 */
	private void openErrorDialog(String dialogText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error occured!");
		alert.setContentText(dialogText);

		alert.showAndWait();
	}
	
	/**
	 * Method for checking whether the numeric tag entries have valid values
	 * 
	 * @param discNumber
	 * @param totalDiscs
	 * @param totalTitles
	 * @param titleNumber
	 * @param year
	 * 
	 * @return boolean which indicates whether the numeric entries are valid 
	 */
	private boolean checkNumbers(int discNumber, int totalDiscs, int totalTitles, int titleNumber, int year) {
		if (discNumber > 50 || discNumber < 0) {
			return false;
		}
		
		if (totalDiscs > 50 || totalDiscs < 0) {
			return false;
		}
		
		if (totalTitles > 100 || totalTitles < 0) {
			return false;
		}
		
		if (titleNumber > 100 || titleNumber < 0) {
			return false;
		}
		
		if(year < 1600 || year > getCurrentYear()) {
			if(!(year == 0)) {
				return false;	
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the current year as string for checking the validity of year field
	 * 
	 * @return the current year as string
	 */
	private int getCurrentYear() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return Integer.parseInt(strDate);
	}
	
	/**
	 * Method for parsing an string to an integer
	 * 
	 * @param number the integer
	 * @return the string as integer
	 * @throws NumberFormatException gets thrown, if false entries are entered
	 */
	private int parseInteger(String number) throws NumberFormatException{
		if (number.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(number);
		}
	}
}
