package ms5000.gui.mainframe.center.eventhandler;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.gui.mainframe.center.CenterGridPane.TextFieldKey;
import ms5000.gui.mainframe.center.CenterTable;
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
			CenterGridPane.getTitlename_textField().setEditable(true);
			CenterGridPane.getTitleNumber_textField().setEditable(true);
			CenterGridPane.getFilePath_TextField().setEditable(true);

			addSingleEntryToDetails(table.getSelectionModel().getSelectedItem());
		} else if (table.getSelectionModel().getSelectedIndices().size() > 1) {
			MusicTag[] tags = new MusicTag[0];
			tags = BorderPane_CENTER.getCentertable().getSelectionModel().getSelectedItems().toArray(tags);
			addMultipleEntryToDetails(tags,
					BorderPane_CENTER.getCentertable().getSelectionModel().getSelectedIndices());
		} else if (table.getSelectionModel().getSelectedIndices().get(0) == -1) {
			refershTextFieldColorProfile();
			clearTextFields();
			CenterGridPane.setArtWorkImage(null);
		}
	}

	private void addSingleEntryToDetails(MusicTag tag) {
		refershTextFieldColorProfile();

		CenterGridPane.getAlbum_textField().setText(tag.getAlbum());
		CenterGridPane.getAlbumArtist_textField().setText(tag.getAlbumArtist());
		CenterGridPane.getGenre_textField().setText(tag.getGenre());
		CenterGridPane.getTitlename_textField().setText(tag.getTitlename());
		CenterGridPane.getComposer_textField().setText(tag.getComposer());
		CenterGridPane.getComment_textField().setText(tag.getComment());
		CenterGridPane.getTitleNumber_textField().setText("" + tag.getTitlenumber());
		CenterGridPane.getTotalTitleNumbers_textField().setText("" + tag.getTotal_titles());
		CenterGridPane.getDiscNumber_textField().setText("" + tag.getDisc_number());
		CenterGridPane.getTotalDiscNumbers_textField().setText("" + tag.getTotal_discs());
		CenterGridPane.getYear_textField().setText("" + tag.getYear());
		CenterGridPane.getArtist_textField().setText(tag.getArtist());
		CenterGridPane.getFilePath_TextField().setText(tag.getMusicFile().getOriginalFilePath());

		if (tag.getArtwork() != null) {
			try {
				CenterGridPane.getArtwork_ImageView().setImage(SwingFXUtils.toFXImage(tag.getArtwork().getImage(), null));
			} catch (IOException e) {
				CenterGridPane.setArtWorkImage(null);
			}
		} else {
			CenterGridPane.setArtWorkImage(null);
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
		
		CenterGridPane.getTitlename_textField().setText("");
		CenterGridPane.getTitleNumber_textField().setText("");

		CenterGridPane.getAlbum_textField().setText(album);
		CenterGridPane.getAlbumArtist_textField().setText(albumArtist);
		CenterGridPane.getGenre_textField().setText(genre);
		CenterGridPane.getTitlename_textField().setEditable(false);
		CenterGridPane.getComposer_textField().setText(composer);
		CenterGridPane.getComment_textField().setText(comment);
		CenterGridPane.getTitleNumber_textField().setEditable(false);
		CenterGridPane.getTotalTitleNumbers_textField().setText(maxTitleNumber);
		CenterGridPane.getDiscNumber_textField().setText(discNumber);
		CenterGridPane.getTotalDiscNumbers_textField().setText(maxDiscNumber);
		CenterGridPane.getYear_textField().setText(year);
		CenterGridPane.getArtist_textField().setText(artist);
		CenterGridPane.getFilePath_TextField().setEditable(false);

		CenterGridPane.setArtWorkImage(null);
	}

	private String getSameEntries(MusicTag[] tags, Tags tagType) {
		System.out.println(tags.length);
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
				BorderPane_CENTER.getCenterGridPane();
				CenterGridPane.getFilePath_TextField().setText("");
			}
		}
	}
}
