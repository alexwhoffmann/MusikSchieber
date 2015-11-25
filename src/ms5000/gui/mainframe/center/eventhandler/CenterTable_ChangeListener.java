package ms5000.gui.mainframe.center.eventhandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ms5000.audio.player.AudioPlayer;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.gui.mainframe.center.CenterTable;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.MusicTag.Tags;

/**
 * This class implements the functionalities which appear if there was a change in center table
 */
public class CenterTable_ChangeListener implements ChangeListener<MusicTag> {
	
	/**
	 * Instance of the center table
	 */
	private CenterTable centerTable;
	
	/**
	 * Instance of the center grid pane with the detail view
	 */
	private CenterGridPane centerGridPane;
	
	

	@Override
	public void changed(ObservableValue<? extends MusicTag> observable, MusicTag oldValue, MusicTag newValue) {
		
		// Getting the instances
		centerGridPane = Main_Frame.getBorderPane_Center().getCenterGridPane();
		centerTable = Main_Frame.getBorderPane_Center().getCentertable();
		
		// Case: only one entry was choosen
		if (centerTable.getSelectionModel().getSelectedIndices().size() == 1) {
			// Reverting changes made
			centerGridPane.enableTextFields();
			
			// Adding the information of a single entry to the detail view
			addSingleEntryToDetails(centerTable.getSelectionModel().getSelectedItem());
			
			// Enabling the player and setting the audio file
			AudioPlayer.getInstance().enablePlayer();
			AudioPlayer.getInstance().setMedia(centerTable.getSelectionModel().getSelectedItem().getMusicFile());

		// Case: More than one entry was choosen	
		} else if (centerTable.getSelectionModel().getSelectedIndices().size() > 1) {
			// Disabling the audio player
			AudioPlayer.getInstance().disablePlayer();
			
			// Getting the selected tags
			MusicTag[] tags = new MusicTag[0];
			tags = Main_Frame.getBorderPane_Center().getCentertable().getSelectionModel().getSelectedItems().toArray(tags);
			
			// Adding the common information of the tags to the detail view
			addMultipleEntryToDetails(tags);
			
		// Case: No entry was choosen	
		} else if (centerTable.getSelectionModel().getSelectedIndices().get(0) == -1) {
			// Reseting the detail view and disabling the player
			AudioPlayer.getInstance().disablePlayer();
			centerGridPane.refershTextFieldColorProfile();
			centerGridPane.clearTextFields();
			centerGridPane.setArtWorkImage(null);
		}
	}

	/**
	 * Method to add a single entry to the detail view
	 * 
	 * @param tag tag that will be red
	*/
	private void addSingleEntryToDetails(MusicTag tag) {
		// Refreshing the color profile
		centerGridPane.refershTextFieldColorProfile();
		
		// Adding the entries
		centerGridPane.getAlbum_textField().setText(tag.getAlbum());
		centerGridPane.getAlbumArtist_textField().setText(tag.getAlbumArtist());
		centerGridPane.getGenre_textField().setText(tag.getGenre());
		centerGridPane.getTitlename_textField().setText(tag.getTitlename());
		centerGridPane.getComposer_textField().setText(tag.getComposer());
		centerGridPane.getComment_textField().setText(tag.getComment());
		centerGridPane.getTitleNumber_textField().setText("" + tag.getTitlenumber());
		centerGridPane.getTotalTitleNumbers_textField().setText("" + tag.getTotal_titles());
		centerGridPane.getDiscNumber_textField().setText("" + tag.getDisc_number());
		centerGridPane.getTotalDiscNumbers_textField().setText("" + tag.getTotal_discs());
		centerGridPane.getYear_textField().setText("" + tag.getYear());
		centerGridPane.getArtist_textField().setText(tag.getArtist());
		centerGridPane.getFilePath_TextField().setText(tag.getMusicFile().getOriginalFilePath());

		if (tag.getArtwork() != null) {
			centerGridPane.setArtWorkImage(tag.getArtwork());
		} else {
			centerGridPane.setArtWorkImage(null);
		}
		
		// Setting the color profile of the text fields
		centerGridPane.setTextFieldColorProfile();
	}
	
	/**
	 * Method to add more than one entry to the detail view
	 * 
	 * @param tags the tags which will be added
	 */
	private void addMultipleEntryToDetails(MusicTag[] tags) {
		centerGridPane.refershTextFieldColorProfile();
		OriginalState state = OriginalState.getInstance();

		// Getting the entries that are the same
		String album = getSameEntries(tags, Tags.ALBUM);
		String artist = getSameEntries(tags, Tags.ARTIST);
		String genre = getSameEntries(tags, Tags.GENRE);
		String albumArtist = getSameEntries(tags, Tags.ALBUMARTIST);
		String maxTitleNumber = getSameEntries(tags, Tags.TOTALTRACKNUMBER);
		String discNumber = getSameEntries(tags, Tags.DISCNUMBER);
		String maxDiscNumber = getSameEntries(tags, Tags.TOTALDISCNUMBER);
		String comment = getSameEntries(tags, Tags.COMMENT);
		String composer = getSameEntries(tags, Tags.COMPOSER);
		String year = getSameEntries(tags, Tags.YEAR);

		// Writing the original state into a singleton, to determine what has changed
		state.setAlbum(album);
		state.setAlbum_artist(albumArtist);
		state.setGenre(genre);
		state.setArtist(artist);
		state.setTitlesTotal(maxTitleNumber);
		state.setDiscNumber(discNumber);
		state.setComment(comment);
		state.setComposer(composer);
		state.setYear(year);
		state.setTotalDiscNumber(maxDiscNumber);

		// Setting the entries
		centerGridPane.getTitlename_textField().setText("");
		centerGridPane.getTitleNumber_textField().setText("");
		centerGridPane.getAlbum_textField().setText(album);
		centerGridPane.getAlbumArtist_textField().setText(albumArtist);
		centerGridPane.getGenre_textField().setText(genre);
		centerGridPane.getComposer_textField().setText(composer);
		centerGridPane.getComment_textField().setText(comment);
		centerGridPane.getTotalTitleNumbers_textField().setText(maxTitleNumber);
		centerGridPane.getDiscNumber_textField().setText(discNumber);
		centerGridPane.getTotalDiscNumbers_textField().setText(maxDiscNumber);
		centerGridPane.getYear_textField().setText(year);
		centerGridPane.getArtist_textField().setText(artist);
		
		centerGridPane.disableTextFields();
		
		// Setting the artwork (If there is a common album)
		if (album.equals("")) {
			centerGridPane.setArtWorkImage(null);
		} else {
			boolean thereIsArtwork = false;
			int tagWithArtwork = 0;

			for (int i = 0; i < tags.length; i++) {
				if (tags[i].getArtwork() != null) {
					thereIsArtwork = true;
					tagWithArtwork = i;
				}
			}

			if (thereIsArtwork) {
				centerGridPane.setArtWorkImage(tags[tagWithArtwork].getArtwork());
			} else {
				centerGridPane.setArtWorkImage(null);
			}

		}

	}
	
	/**
	 * Method to return the common string if there is one 
	 * 
	 * @param tags Tags, which will be compared
	 * @param tagType the tag type (needed for iteration)
	 * 
	 * @return nothing or the common string
	 */
	private String getSameEntries(MusicTag[] tags, Tags tagType) {
		String tmp = tags[0].getString(tagType);

		for (int i = 1; i < tags.length; i++) {
			if (!tags[i].getString(tagType).equals(tmp)) {
				return "";
			}
		}

		return tmp;
	}
	
}
