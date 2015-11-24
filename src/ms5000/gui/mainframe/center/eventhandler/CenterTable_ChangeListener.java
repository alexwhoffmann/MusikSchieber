package ms5000.gui.mainframe.center.eventhandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import ms5000.audio.player.AudioPlayer;
import ms5000.audio.player.PlayerStatus;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.center.CenterGridPane.TextFieldKey;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.gui.mainframe.top.HBox_TOP_LEFT;
import ms5000.gui.mainframe.center.CenterTable;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.MusicTag.Tags;

public class CenterTable_ChangeListener implements ChangeListener<MusicTag> {
	private CenterTable table;
	private final static String missingCritical = "-fx-control-inner-background: rgb(255, 153, 153,1)";
	private final static String missingNonCritical = "-fx-control-inner-background: rgb(255, 255, 179,1)";
	private final static String missingWeak = "-fx-control-inner-background: rgb(255, 255, 229,1)";
	private final static String default_format = "-fx-control-inner-background: #ffffff";

	public CenterTable_ChangeListener(CenterTable table) {
		this.table = table;
	}

	@Override
	public void changed(ObservableValue<? extends MusicTag> observable, MusicTag oldValue, MusicTag newValue) {
		
		if (table.getSelectionModel().getSelectedIndices().size() == 1) {
			// Reverting changes made
			BorderPane_CENTER.getCenterGridPane().getTitlename_textField().setEditable(true);
			BorderPane_CENTER.getCenterGridPane().getTitleNumber_textField().setEditable(true);
			BorderPane_CENTER.getCenterGridPane().getFilePath_TextField().setEditable(true);
			addSingleEntryToDetails(table.getSelectionModel().getSelectedItem());
			
			updatePlayer(table.getSelectionModel().getSelectedItem().getMusicFile());
		} else if (table.getSelectionModel().getSelectedIndices().size() > 1) {
			disablePlayer();
			MusicTag[] tags = new MusicTag[0];
			tags = BorderPane_CENTER.getCentertable().getSelectionModel().getSelectedItems().toArray(tags);
			addMultipleEntryToDetails(tags,BorderPane_CENTER.getCentertable().getSelectionModel().getSelectedIndices());
		} else if (table.getSelectionModel().getSelectedIndices().get(0) == -1) {
			HBox_TOP_LEFT.getBtn_Start().setDisable(true);
			HBox_TOP_LEFT.getBtn_Stop().setDisable(true);
			disablePlayer();
			refershTextFieldColorProfile();
			clearTextFields();
			BorderPane_CENTER.getCenterGridPane().setArtWorkImage(null);
		}
	}

	private void updatePlayer(MusicFile musicFile) {
		if (HBox_TOP_LEFT.getBtn_Stop().isDisable()) {
			HBox_TOP_LEFT.getBtn_Stop().setDisable(false);
		}
		
		HBox_TOP_LEFT.getBtn_Start().setDisable(false);
		
		if (AudioPlayer.getInstance().getMediaPlayer() != null) {
			if (AudioPlayer.getInstance().getStatus() != PlayerStatus.READY) {
				AudioPlayer.getInstance().stop();
			}
		}
		
		BoderPane_TOP_CENTER.getStatusSlider().setStatusText("");
		AudioPlayer.getInstance().setMedia(musicFile);
	}
	
	private void disablePlayer() {
		if (AudioPlayer.getInstance().getMediaPlayer() != null) {
			if (AudioPlayer.getInstance().getStatus() != PlayerStatus.READY) {
				AudioPlayer.getInstance().stop();
			}
		}
		HBox_TOP_LEFT.getBtn_Stop().setDisable(true);
		HBox_TOP_LEFT.getBtn_Start().setDisable(true);
		BoderPane_TOP_CENTER.getStatusSlider().setStatusText("");
		AudioPlayer.getInstance().setMedia(null);
		AudioPlayer.getInstance().setStatus(PlayerStatus.NOMEDIAFILE);
	}

	private void addSingleEntryToDetails(MusicTag tag) {
		refershTextFieldColorProfile();

		BorderPane_CENTER.getCenterGridPane().getAlbum_textField().setText(tag.getAlbum());
		BorderPane_CENTER.getCenterGridPane().getAlbumArtist_textField().setText(tag.getAlbumArtist());
		BorderPane_CENTER.getCenterGridPane().getGenre_textField().setText(tag.getGenre());
		BorderPane_CENTER.getCenterGridPane().getTitlename_textField().setText(tag.getTitlename());
		BorderPane_CENTER.getCenterGridPane().getComposer_textField().setText(tag.getComposer());
		BorderPane_CENTER.getCenterGridPane().getComment_textField().setText(tag.getComment());
		BorderPane_CENTER.getCenterGridPane().getTitleNumber_textField().setText("" + tag.getTitlenumber());
		BorderPane_CENTER.getCenterGridPane().getTotalTitleNumbers_textField().setText("" + tag.getTotal_titles());
		BorderPane_CENTER.getCenterGridPane().getDiscNumber_textField().setText("" + tag.getDisc_number());
		BorderPane_CENTER.getCenterGridPane().getTotalDiscNumbers_textField().setText("" + tag.getTotal_discs());
		BorderPane_CENTER.getCenterGridPane().getYear_textField().setText("" + tag.getYear());
		BorderPane_CENTER.getCenterGridPane().getArtist_textField().setText(tag.getArtist());
		BorderPane_CENTER.getCenterGridPane().getFilePath_TextField().setText(tag.getMusicFile().getOriginalFilePath());

		if (tag.getArtwork() != null) {
			BorderPane_CENTER.getCenterGridPane().setArtWorkImage(tag.getArtwork());
		} else {
			BorderPane_CENTER.getCenterGridPane().setArtWorkImage(null);
		}

		setTextFieldColorProfile();
	}

	private void addMultipleEntryToDetails(MusicTag[] tags, ObservableList<Integer> observableList) {
		refershTextFieldColorProfile();
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

		BorderPane_CENTER.getCenterGridPane().getTitlename_textField().setText("");
		BorderPane_CENTER.getCenterGridPane().getTitleNumber_textField().setText("");

		BorderPane_CENTER.getCenterGridPane().getAlbum_textField().setText(album);
		BorderPane_CENTER.getCenterGridPane().getAlbumArtist_textField().setText(albumArtist);
		BorderPane_CENTER.getCenterGridPane().getGenre_textField().setText(genre);
		BorderPane_CENTER.getCenterGridPane().getTitlename_textField().setEditable(false);
		BorderPane_CENTER.getCenterGridPane().getComposer_textField().setText(composer);
		BorderPane_CENTER.getCenterGridPane().getComment_textField().setText(comment);
		BorderPane_CENTER.getCenterGridPane().getTitleNumber_textField().setEditable(false);
		BorderPane_CENTER.getCenterGridPane().getTotalTitleNumbers_textField().setText(maxTitleNumber);
		BorderPane_CENTER.getCenterGridPane().getDiscNumber_textField().setText(discNumber);
		BorderPane_CENTER.getCenterGridPane().getTotalDiscNumbers_textField().setText(maxDiscNumber);
		BorderPane_CENTER.getCenterGridPane().getYear_textField().setText(year);
		BorderPane_CENTER.getCenterGridPane().getArtist_textField().setText(artist);
		BorderPane_CENTER.getCenterGridPane().getFilePath_TextField().setEditable(false);

		if (album.equals("")) {
			BorderPane_CENTER.getCenterGridPane().setArtWorkImage(null);
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
				BorderPane_CENTER.getCenterGridPane().setArtWorkImage(tags[tagWithArtwork].getArtwork());
			} else {
				BorderPane_CENTER.getCenterGridPane().setArtWorkImage(null);
			}

		}

	}

	private String getSameEntries(MusicTag[] tags, Tags tagType) {
		String init = tags[0].getString(tagType);

		for (int i = 1; i < tags.length; i++) {
			if (!tags[i].getString(tagType).equals(init)) {
				return "";
			}
		}

		return init;
	}

	public static void setTextFieldColorProfile() {
		for (TextFieldKey key : TextFieldKey.values()) {
			if (key == TextFieldKey.ARTIST || key == TextFieldKey.ALBUM || key == TextFieldKey.TITLENAME) {
				if (BorderPane_CENTER.getCenterGridPane().getTextField(key).getText().equals("")) {
					BorderPane_CENTER.getCenterGridPane().getTextField(key).setStyle(missingCritical);
				}
			} else if (key == TextFieldKey.TITLENUMBER || key == TextFieldKey.TITLENUMBER_TOTAL) {
				if (BorderPane_CENTER.getCenterGridPane().getTextField(key).getText().equals("0")) {
					BorderPane_CENTER.getCenterGridPane().getTextField(key).setStyle(missingNonCritical);
				}
			} else if (key == TextFieldKey.YEAR || key == TextFieldKey.ALBUM_ARTIST) {
				if (BorderPane_CENTER.getCenterGridPane().getTextField(key).getText().equals("")
						|| BorderPane_CENTER.getCenterGridPane().getTextField(key).getText().equals("0")) {
					BorderPane_CENTER.getCenterGridPane().getTextField(key).setStyle(missingWeak);
				}
			}
		}
	}

	public static void refershTextFieldColorProfile() {
		for (TextFieldKey key : TextFieldKey.values()) {
			if(BorderPane_CENTER.getCenterGridPane().getTextField(key) != null) {
				BorderPane_CENTER.getCenterGridPane().getTextField(key).setStyle(default_format);	
			}
		}
	}
	
	private void clearTextFields() {
		for (TextFieldKey key : TextFieldKey.values()) {
			if(BorderPane_CENTER.getCenterGridPane().getTextField(key) != null) {
				BorderPane_CENTER.getCenterGridPane().getTextField(key).setText("");
				BorderPane_CENTER.getCenterGridPane().getFilePath_TextField().setText("");
			}
		}
	}
}
