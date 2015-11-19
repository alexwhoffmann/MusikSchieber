package ms5000.gui.mainframe.center;

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

public class CenterGridPane extends GridPane {
	public enum TextFieldKey {
		TITLENAME,ALBUM,ARTIST,ALBUM_ARTIST,TITLENUMBER,TITLENUMBER_TOTAL,DISCNUMBER,DISCNUMBER_TOTAL,COMPOSER,COMMENT,GENRE,YEAR
	}
	
	// General
	private Label tagInformation_Label;
	private Label fileInformation_Label;
	private Label artWork_Label;
	
	private Label filePath_Label;
	private static TextField filePath_TextField;
	
	private Button openFolder;
	private Button getInfo_Artist;
	private Button getInfo_Album;
	private Button save_Tag;
	
	private static ImageView artWork_ImageView;
	
	// Tag informations
	private static TextField titlename_textField;
	private static TextField artist_textField;
	private static TextField genre_textField;
	private static TextField album_textField;
	private static TextField year_textField;
	private static TextField titleNumber_textField;
	private static TextField totalTitleNumbers_textField;
	private static TextField albumArtist_textField;
	private static TextField composer_textField;
	private static TextField comment_textField;
	private static TextField discNumber_textField;
	private static TextField totalDiscNumbers_textField;

	private Label titlename_Label;
	private Label artist_Label;
	private Label album_Label;
	private Label genre_Label;
	private Label year_Label;
	private Label titleNumber_Label;
	private Label  totalTitleNumbers_Label;
	private Label albumArtist_Label;
	private Label composer_Label;
	private Label comment_Label;
	private Label discNumber_Label;
	private Label totalDiscNumbers_Label;
	
	private final String openFolder_Image_Path = "file:icons/Folder_Open.png";
	private final String getInfo_Image_Path = "file:icons/question_mark.png";
	private final String save_Image_Path = "file:icons/save_icon.png";
	private final Image getInfo_Image = new Image(getInfo_Image_Path);
	private final Image openFolder_Image = new Image(openFolder_Image_Path);
	private final Image save_Image = new Image (save_Image_Path);
	
	
	// For Testing
	private static final Image artworkDefault = new Image("file:icons/artwork_icon.png");
	
	public CenterGridPane() {
		init();
	}

	private void init() {
		// Styling the grid pane
		this.setHgap(15);
		this.setVgap(15);
		this.setPadding(new Insets(0, 10, 0, 10));

		this.setPrefWidth(Main_Frame.getPrefFrameWidth() / 2);
		this.setMinWidth(Main_Frame.getMinFrameWidth() / 2);
		this.setMaxWidth(Main_Frame.getPrefFrameWidth() / 2);

		// General
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
		
		openFolder = new Button();
		openFolder.setGraphic(new ImageView(openFolder_Image));
		
		save_Tag = new Button();
		
		save_Tag.setOnAction(new CenterGridPane_ButtonSave_EventHandler());
		save_Tag.setGraphic(new ImageView(save_Image));
		
		artWork_ImageView = new ImageView(artworkDefault);
		artWork_ImageView.setFitWidth(200);
		artWork_ImageView.setPreserveRatio(true);
		artWork_ImageView.setSmooth(true);
		artWork_ImageView.setCache(true);
		
		// The TextFields
		titlename_textField = new TextField("");
		titlename_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

		artist_textField = new TextField("");
		artist_textField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		getInfo_Artist = new Button();
		getInfo_Artist.setGraphic(new ImageView(getInfo_Image));
		
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
		getInfo_Album = new Button();
		getInfo_Album.setGraphic(new ImageView(getInfo_Image));
		
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

		// The Labels
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

		// Tag Information
		CenterGridPane.setConstraints(tagInformation_Label, 1, 1, 2, 2);
		CenterGridPane.setConstraints(save_Tag,3,1,1,2);

		// Titlename
		CenterGridPane.setConstraints(titlename_Label, 1, 3);
		CenterGridPane.setConstraints(titlename_textField, 2, 3, 3, 1);

		// Artist
		CenterGridPane.setConstraints(artist_Label, 5, 3);
		CenterGridPane.setConstraints(artist_textField, 6, 3, 2, 1);
		CenterGridPane.setConstraints(getInfo_Artist, 8, 3);
		
		// Album
		CenterGridPane.setConstraints(album_Label, 1, 4);
		CenterGridPane.setConstraints(album_textField, 2, 4, 2, 1);
		CenterGridPane.setConstraints(getInfo_Album, 4, 4);
		
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
		
		CenterGridPane.setConstraints(artWork_Label,1,9, 8, 2);
		CenterGridPane.setConstraints(artWork_ImageView,1,11,8,1);
				
		// File Information
		CenterGridPane.setConstraints(fileInformation_Label, 1, 12, 8, 2);
		CenterGridPane.setConstraints(filePath_Label, 1, 14);
		CenterGridPane.setConstraints(filePath_TextField, 2, 14, 6, 1);
		CenterGridPane.setConstraints(openFolder,8,14);

		this.getChildren().addAll(titlename_Label, titlename_textField, artist_Label, artist_textField, album_Label,
				album_textField, year_Label, year_textField, titleNumber_Label, titleNumber_textField,
				totalTitleNumbers_textField, discNumber_Label, discNumber_textField, totalDiscNumbers_textField,
				composer_Label, composer_textField, comment_textField, comment_Label, albumArtist_Label,
				albumArtist_textField, genre_textField, genre_Label, totalTitleNumbers_Label, totalDiscNumbers_Label,
				tagInformation_Label, fileInformation_Label, filePath_Label, filePath_TextField, openFolder,
				getInfo_Artist, getInfo_Album,artWork_Label,artWork_ImageView,save_Tag);
	}
	
	public static ImageView getArtwork_ImageView() {
		return artWork_ImageView;
	}
	
	public static TextField getArtist_textField() {
		return artist_textField;
	}
	
	public static TextField getFilePath_TextField() {
		return filePath_TextField;
	}

	public static TextField getTitlename_textField() {
		return titlename_textField;
	}

	public static TextField getGenre_textField() {
		return genre_textField;
	}

	public static TextField getAlbum_textField() {
		return album_textField;
	}

	public static TextField getYear_textField() {
		return year_textField;
	}

	public static TextField getTitleNumber_textField() {
		return titleNumber_textField;
	}

	public static TextField getTotalTitleNumbers_textField() {
		return totalTitleNumbers_textField;
	}

	public static TextField getAlbumArtist_textField() {
		return albumArtist_textField;
	}

	public static TextField getComposer_textField() {
		return composer_textField;
	}

	public static TextField getComment_textField() {
		return comment_textField;
	}

	public static TextField getDiscNumber_textField() {
		return discNumber_textField;
	}

	public static TextField getTotalDiscNumbers_textField() {
		return totalDiscNumbers_textField;
	}
	
	public static void setArtWorkImage(Image image) {
		if (image == null) {
			CenterGridPane.getArtwork_ImageView().setImage(artworkDefault);
		} else {
			CenterGridPane.getArtwork_ImageView().setImage(image);
		}
	}
	
	public TextField getTextField(TextFieldKey key) {
		switch (key) {
		case ALBUM:
			return CenterGridPane.getAlbum_textField();
		case TITLENAME:
			return CenterGridPane.getTitlename_textField();
		case ARTIST:
			return CenterGridPane.getArtist_textField();
		case ALBUM_ARTIST:
			return CenterGridPane.getAlbumArtist_textField();
		case TITLENUMBER:
			return CenterGridPane.getTitleNumber_textField();
		case TITLENUMBER_TOTAL:
			return CenterGridPane.getTotalTitleNumbers_textField();
		case DISCNUMBER:
			return CenterGridPane.getDiscNumber_textField();
		case DISCNUMBER_TOTAL:
			return CenterGridPane.getTotalDiscNumbers_textField();
		case COMPOSER:
			return CenterGridPane.getComposer_textField();
		case COMMENT:
			return CenterGridPane.getComment_textField();
		case YEAR:
			return CenterGridPane.getYear_textField();
		case GENRE:
			return CenterGridPane.getGenre_textField();

		default:
			return null;
		}
	}
}