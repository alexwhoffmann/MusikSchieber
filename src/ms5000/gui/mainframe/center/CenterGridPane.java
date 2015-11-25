package ms5000.gui.mainframe.center;

import java.io.IOException;

import org.jaudiotagger.tag.datatype.Artwork;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.eventhandler.CenterGridPane_ButtonSave_EventHandler;
import ms5000.gui.mainframe.center.eventhandler.Import_Artwork_EventHandler;

/**
 * This class implements the detail view on the right hand side of the main frame
 *
 */
public class CenterGridPane extends GridPane {
	
	/**
	 * With this enum it is possible to iterate through the values of the textfields
	 * in the detail view 
	 */
	public enum TextFieldKey {
		TITLENAME, ALBUM, ARTIST, ALBUM_ARTIST, TITLENUMBER, TITLENUMBER_TOTAL, 
		DISCNUMBER, DISCNUMBER_TOTAL, COMPOSER, COMMENT, GENRE, YEAR
	}

	/**
	 * The general information
	 */
	private Label tagInformation_Label;
	private Label artWork_Label;
	
	private Label fileInformation_Label;
	private Label filePath_Label;
	private TextField filePath_TextField;
	
	/**
	 * The buttons of the view
	 */
	private Button button_openFolder;
	private Button button_getInfo_Artist;
	private Button button_getInfo_Album;
	private Button button_save_Tag;
	private Button button_open_ImageFile;
	
	/**
	 * Artwork
	 */
	private ImageView artWork_ImageView;
	private Artwork artworkTemp;
	
	/**
	 * Tag information textfields and labels
	 */
	private TextField titlename_textField;
	private TextField artist_textField;
	private TextField genre_textField;
	private TextField album_textField;
	private TextField year_textField;
	private TextField titleNumber_textField;
	private TextField totalTitleNumbers_textField;
	private TextField albumArtist_textField;
	private TextField composer_textField;
	private TextField comment_textField;
	private TextField discNumber_textField;
	private TextField totalDiscNumbers_textField;

	private Label titlename_Label;
	private Label artist_Label;
	private Label album_Label;
	private Label genre_Label;
	private Label year_Label;
	private Label titleNumber_Label;
	private Label totalTitleNumbers_Label;
	private Label albumArtist_Label;
	private Label composer_Label;
	private Label comment_Label;
	private Label discNumber_Label;
	private Label totalDiscNumbers_Label;
	
	/**
	 * Paths to the icon files and the correspondig image objects
	 */
	private final String openFolder_Image_Path = "file:icons/Folder_Open.png";
	private final String getInfo_Image_Path = "file:icons/question_mark.png";
	private final String save_Image_Path = "file:icons/save_icon.png";
	private final String default_Image_Path = "file:icons/artwork_icon.png";
	private final String open_File_Image_Path = "file:icons/load-image-icon.png";
	private final Image getInfo_Image = new Image(getInfo_Image_Path);
	private final Image openFolder_Image = new Image(openFolder_Image_Path);
	private final Image save_Image = new Image (save_Image_Path);
	private final Image open_File_Image = new Image (open_File_Image_Path);
	private final Image artworkDefault = new Image(default_Image_Path);
	
	/**
	 * The default format for the text fields
	 */
	private final String default_format = "-fx-control-inner-background: #ffffff";
	
	/**
	 * The formats for the different cases 
	 */
	private final String missingCritical = "-fx-control-inner-background: rgb(255, 153, 153,1)";
	private final String missingNonCritical = "-fx-control-inner-background: rgb(255, 255, 179,1)";
	private final String missingWeak = "-fx-control-inner-background: rgb(255, 255, 229,1)";
	
	/**
	 * Initializes the grid pane
	 */
	public CenterGridPane() {
		init();
	}
	
	/**
	 * Method used in the constructor to initialize the grid pane
	 */
	private void init() {
		// Styling the grid pane
		this.setHgap(15);
		this.setVgap(15);
		this.setPadding(new Insets(0, 10, 0, 10));

		this.setPrefWidth(Main_Frame.getPrefFrameWidth() / 2);
		this.setMinWidth(Main_Frame.getMinFrameWidth() / 2);
		this.setMaxWidth(Main_Frame.getPrefFrameWidth() / 2);

		// General informations
		tagInformation_Label = new Label("Tag-Information");
		tagInformation_Label.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		fileInformation_Label = new Label("File-Information");
		fileInformation_Label.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		artWork_Label = new Label("Artwork");
		artWork_Label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		filePath_Label = new Label("File-Path:");
		filePath_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		filePath_TextField = new TextField("Example");
		filePath_TextField.setEditable(false);
		filePath_TextField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		
		// The buttons
		button_openFolder = new Button();
		button_openFolder.setGraphic(new ImageView(openFolder_Image));
		
		button_save_Tag = new Button();
		button_save_Tag.setOnAction(new CenterGridPane_ButtonSave_EventHandler());
		button_save_Tag.setGraphic(new ImageView(save_Image));
		
		button_getInfo_Artist = new Button();
		button_getInfo_Artist.setGraphic(new ImageView(getInfo_Image));
		
		button_getInfo_Album = new Button();
		button_getInfo_Album.setGraphic(new ImageView(getInfo_Image));
		
		button_open_ImageFile = new Button();
		button_open_ImageFile.setGraphic(new ImageView(open_File_Image));
		button_open_ImageFile.setOnAction(new Import_Artwork_EventHandler());
		
		// Default image of the artwork view
		artWork_ImageView = new ImageView(artworkDefault);
		artWork_ImageView.setFitWidth(200);
		artWork_ImageView.setPreserveRatio(true);
		artWork_ImageView.setSmooth(true);
		artWork_ImageView.setCache(true);
		
		// The TextFields of the tag informations
		titlename_textField = new TextField("");
		titlename_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		artist_textField = new TextField("");
		artist_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		
		genre_textField = new TextField("");
		genre_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		year_textField = new TextField("");
		year_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		titleNumber_textField = new TextField("");
		titleNumber_textField.setMaxWidth(45);
		titleNumber_textField.setMinWidth(45);
		titleNumber_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		totalTitleNumbers_textField = new TextField("");
		totalTitleNumbers_textField.setMaxWidth(45);
		totalTitleNumbers_textField.setMinWidth(45);
		totalTitleNumbers_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		album_textField = new TextField("");
		album_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		
		albumArtist_textField = new TextField("");
		albumArtist_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		composer_textField = new TextField("");
		composer_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		comment_textField = new TextField("");
		comment_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		discNumber_textField = new TextField("");
		discNumber_textField.setMaxWidth(45);
		discNumber_textField.setMinWidth(45);
		discNumber_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		totalDiscNumbers_textField = new TextField("");
		totalDiscNumbers_textField.setMaxWidth(45);
		totalDiscNumbers_textField.setMinWidth(45);
		totalDiscNumbers_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		// The Labels for the tag information text fields
		artist_Label = new Label("Artist:");
		artist_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		titlename_Label = new Label("Titlename:");
		titlename_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		genre_Label = new Label("Genre:");
		genre_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		year_Label = new Label("Year:");
		year_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		titleNumber_Label = new Label("Title-Number:");
		titleNumber_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		album_Label = new Label("Album:");
		album_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		albumArtist_Label = new Label("Album Artist:");
		albumArtist_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		composer_Label = new Label("Composer:");
		composer_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		comment_Label = new Label("Comment:");
		comment_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		discNumber_Label = new Label("Disc-Number:");
		discNumber_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		totalTitleNumbers_Label = new Label(" - ");
		totalTitleNumbers_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		totalDiscNumbers_Label = new Label(" - ");
		totalDiscNumbers_Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		// Putting text fields and labels on to the grid pane
		// Tag Information
		CenterGridPane.setConstraints(tagInformation_Label, 1, 1, 2, 2);
		CenterGridPane.setConstraints(button_save_Tag,3,1,1,2);

		// Titlename
		CenterGridPane.setConstraints(titlename_Label, 1, 3);
		CenterGridPane.setConstraints(titlename_textField, 2, 3, 3, 1);

		// Artist
		CenterGridPane.setConstraints(artist_Label, 5, 3);
		CenterGridPane.setConstraints(artist_textField, 6, 3, 2, 1);
		CenterGridPane.setConstraints(button_getInfo_Artist, 8, 3);
		
		// Album
		CenterGridPane.setConstraints(album_Label, 1, 4);
		CenterGridPane.setConstraints(album_textField, 2, 4, 2, 1);
		CenterGridPane.setConstraints(button_getInfo_Album, 4, 4);
		
		// Year
		CenterGridPane.setConstraints(year_Label, 5, 4);
		CenterGridPane.setConstraints(year_textField, 6, 4, 3, 1);

		// Album Artist
		CenterGridPane.setConstraints(albumArtist_Label, 1, 5);
		CenterGridPane.setConstraints(albumArtist_textField, 2, 5, 3, 1);
		
		// Genre
		CenterGridPane.setConstraints(genre_Label, 5, 5);
		CenterGridPane.setConstraints(genre_textField, 6, 5, 3, 1);

		// Titlenumber
		CenterGridPane.setConstraints(titleNumber_Label, 1, 6);
		CenterGridPane.setConstraints(titleNumber_textField, 2, 6);
		CenterGridPane.setConstraints(totalTitleNumbers_Label, 3, 6);
		CenterGridPane.setConstraints(totalTitleNumbers_textField, 4, 6);

		// DiscNumber
		CenterGridPane.setConstraints(discNumber_Label, 5, 6);
		CenterGridPane.setConstraints(discNumber_textField, 6, 6);
		CenterGridPane.setConstraints(totalDiscNumbers_Label, 7, 6);
		CenterGridPane.setConstraints(totalDiscNumbers_textField, 8, 6);

		// Composer
		CenterGridPane.setConstraints(composer_Label, 1, 7);
		CenterGridPane.setConstraints(composer_textField, 2, 7, 7, 1);

		// Comment
		CenterGridPane.setConstraints(comment_Label, 1, 8);
		CenterGridPane.setConstraints(comment_textField, 2, 8, 7, 1);
		
		// Artwork
		CenterGridPane.setConstraints(artWork_Label,1,9, 1, 2);
		CenterGridPane.setConstraints(artWork_ImageView,1,11,8,1);
		CenterGridPane.setConstraints(button_open_ImageFile,2,9,1,2);
		
		// File Information
		CenterGridPane.setConstraints(fileInformation_Label, 1, 12, 8, 2);
		CenterGridPane.setConstraints(filePath_Label, 1, 14);
		CenterGridPane.setConstraints(filePath_TextField, 2, 14, 6, 1);
		CenterGridPane.setConstraints(button_openFolder,8,14);
		
		// Adding the children to the pane
		this.getChildren().addAll(titlename_Label, titlename_textField, artist_Label, artist_textField, album_Label,
				album_textField, year_Label, year_textField, titleNumber_Label, titleNumber_textField,
				totalTitleNumbers_textField, discNumber_Label, discNumber_textField, totalDiscNumbers_textField,
				composer_Label, composer_textField, comment_textField, comment_Label, albumArtist_Label,
				albumArtist_textField, genre_textField, genre_Label, totalTitleNumbers_Label, totalDiscNumbers_Label,
				tagInformation_Label, fileInformation_Label, filePath_Label, filePath_TextField, button_openFolder,
				button_getInfo_Artist, button_getInfo_Album, artWork_Label, artWork_ImageView, button_save_Tag,
				button_open_ImageFile);
	}
	
	/**
	 * Getter and Setter methods for this class
	 */
	
	/**
	 * @return Instance of the filepath textfield
	 */
	public TextField getFilePath_TextField() {
		return filePath_TextField;
	}
	
	/**
	 * @return Instance of the artist textfield
	 */
	public TextField getArtist_textField() {
		return artist_textField;
	}
	
	/**
	 * @return Instance of the titlename textfield
	 */
	public TextField getTitlename_textField() {
		return titlename_textField;
	}
	
	/**
	 * @return Instance of the genre textfield
	 */
	public TextField getGenre_textField() {
		return genre_textField;
	}
	
	/**
	 * @return Instance of the album textfield
	 */
	public TextField getAlbum_textField() {
		return album_textField;
	}
	
	/**
	 * @return Instance of the album year textfield
	 */
	public TextField getYear_textField() {
		return year_textField;
	}
	
	/**
	 * @return Instance of the title number textfield
	 */
	public TextField getTitleNumber_textField() {
		return titleNumber_textField;
	}
	
	/**
	 * @return Instance of the total title number textfield
	 */
	public TextField getTotalTitleNumbers_textField() {
		return totalTitleNumbers_textField;
	}
	
	/**
	 * @return Instance of the album artist textfield
	 */
	public TextField getAlbumArtist_textField() {
		return albumArtist_textField;
	}
	
	/**
	 * @return Instance of the composer textfield
	 */
	public TextField getComposer_textField() {
		return composer_textField;
	}
	
	/**
	 * @return Instance of the comment textfield
	 */
	public TextField getComment_textField() {
		return comment_textField;
	}
	
	/**
	 * @return Instance of the disc number textfield
	 */
	public TextField getDiscNumber_textField() {
		return discNumber_textField;
	}
	
	/**
	 * @return Instance of the total disc number textfield
	 */
	public TextField getTotalDiscNumbers_textField() {
		return totalDiscNumbers_textField;
	}
	
	/**
	 * @return Returns an instance of the temporarily stored artwork
	 */
	public Artwork getArtwork() {
		return artworkTemp;
	}
	
	/**
	 * @return Returns an instace of the save tag button to enable short key usage
	 */
	public Button getButton_save_Tag() {
		return button_save_Tag;
	}
	
	
	/**
	 * Method for setting the artwork view to an image delivered through the
	 * parameter artWork
	 * 
	 * @param artWork: Artwork which is used to set the artwork field
	 */
	public void setArtWorkImage(Artwork artWork) {
		if (artWork == null) {
			this.artWork_ImageView.setImage(artworkDefault);
			artworkTemp = null;
		} else {
			try {
				this.artWork_ImageView.setImage(SwingFXUtils.toFXImage(artWork.getImage(), null));
				artworkTemp = artWork;
			} catch (IOException e) {
				this.artWork_ImageView.setImage(artworkDefault);
				artworkTemp = null;
			}
		}
	}
	
	/**
	 * Method which delivers an instance of the textfield to the delivered key
	 * Makes it possible to iterate through the textfields of the grid pane
	 * 
	 * @param key Enum TextFieldKey
	 * @return instance of the corresponding text field
	 */
	public TextField getTextField(TextFieldKey key) {
		switch (key) {
		case ALBUM:
			return getAlbum_textField();
		case TITLENAME:
			return getTitlename_textField();
		case ARTIST:
			return getArtist_textField();
		case ALBUM_ARTIST:
			return getAlbumArtist_textField();
		case TITLENUMBER:
			return getTitleNumber_textField();
		case TITLENUMBER_TOTAL:
			return getTotalTitleNumbers_textField();
		case DISCNUMBER:
			return getDiscNumber_textField();
		case DISCNUMBER_TOTAL:
			return getTotalDiscNumbers_textField();
		case COMPOSER:
			return getComposer_textField();
		case COMMENT:
			return getComment_textField();
		case YEAR:
			return getYear_textField();
		case GENRE:
			return getGenre_textField();

		default:
			return null;
		}
	}
	
	/**
	 * Method to refresh the color profile of the detail view
	 */
	public void refershTextFieldColorProfile() {
		for (TextFieldKey key : TextFieldKey.values()) {
			if(this.getTextField(key) != null) {
				this.getTextField(key).setStyle(default_format);	
			}
		}
	}
	
	/**
	 * Method to clear the text fields in the detail view
	 */
	public void clearTextFields() {
		for (TextFieldKey key : TextFieldKey.values()) {
			if(this.getTextField(key) != null) {
				this.getTextField(key).setText("");
				this.getFilePath_TextField().setText("");
			}
		}
	}
	
	/**
	 * Method to enable certain text fields
	 */
	public void enableTextFields() {
		this.getTitlename_textField().setEditable(true);
		this.getTitleNumber_textField().setEditable(true);
		this.getFilePath_TextField().setEditable(true);
	}
	
	/**
	 * Method to disable certain text fields
	 */
	public void disableTextFields() {
		this.getTitlename_textField().setEditable(false);
		this.getTitleNumber_textField().setEditable(false);
		this.getFilePath_TextField().setEditable(false);
	}
	
	/**
	 * Method to set the color profile for the detail view
	 */
	public void setTextFieldColorProfile() {
		
		for (TextFieldKey key : TextFieldKey.values()) {
			if (key == TextFieldKey.ARTIST || key == TextFieldKey.ALBUM || key == TextFieldKey.TITLENAME) {
				if (this.getTextField(key).getText().equals("")) {
					this.getTextField(key).setStyle(missingCritical);
				}
			} else if (key == TextFieldKey.TITLENUMBER || key == TextFieldKey.TITLENUMBER_TOTAL) {
				if (this.getTextField(key).getText().equals("0")) {
					this.getTextField(key).setStyle(missingNonCritical);
				}
			} else if (key == TextFieldKey.YEAR || key == TextFieldKey.ALBUM_ARTIST) {
				if (this.getTextField(key).getText().equals("")
						|| this.getTextField(key).getText().equals("0")) {
					this.getTextField(key).setStyle(missingWeak);
				}
			}
		}
	}
	
}