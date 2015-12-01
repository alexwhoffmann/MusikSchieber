package ms5000.gui.profile;

import java.io.File;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.properties.ProfileProperties;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.icons.IconProperties;
import ms5000.properties.library.OrderingProperty;
import ms5000.properties.playlist.Header;

/**
 * This class implements the profile view after pressing the settings button
 */
public class ProfileSettings extends GridPane {
	/**
	 * Dialog texts
	 */
	private final String header_text = "You are about to change the library settings. Do you want to continue?";
	private final String header = "Changing library properties";
	
	/**
	 * The labels
	 */
	private Label musicLibrarySettings_Label;
	private Label importSettings_Label;
	private Label playlistSettings_Label;	
	private Label musicLibraryPath_Label;
	private Label orderingMode_Label;
	private Label keepFiles_Label;
	private Label justTagFiles_Label;
	private Label playListExportPath_Label;
	private Label playListExport_Label;
	private Label playListName_Label;
	private Label playListHeader;
	
	/**
	 * Music library text field and open folder button
	 */
	private TextField musicLibraryPath;
	private Button chooseDirToMusicLibrary;
	
	/**
	 * The choice box for the ordering mode
	 */
	private ChoiceBox<String> orderingMode;
	private final String AAA = "A - Artist - Album";
	private final String AA = "Artist - Album";
	private final String GAA = "Genre - Artist - Album";
	
	/**
	 * Radio button for keeping the files
	 */
	private RadioButton keepFiles;
	
	/**
	 * Radio button for just tagging the files
	 */
	private RadioButton justTagFiles;
	
	/**
	 * Text fields and radio button for playlist export configuration
	 */
	private TextField playListExportPath;
	private RadioButton playListExport;
	private Button chooseDirToExportPlayList;
	private TextField playListName;
	
	private ChoiceBox<String> playListHeaderMode;
	private final String germanHeader = "German Header";
	private final String englishHeader = "English Header";
	
	/**
	 * Instance of this
	 */
	private static ProfileSettings profileSettings = new ProfileSettings();
	
	/**
	 * Scene of the properties settings
	 */
	private Scene scene = new Scene(this);
	
	/**
	 * The apply button
	 */
	private Button applyButton;
	
	/**
	 * The restore button
	 */
	private Button restoreButton;
	
	/**
	 * The stage on which the grid pane gets shown
	 */
	private Stage propertiesStage;
	
	/**
	 * Constructs the profile grid pane
	 */
	private ProfileSettings(){
		// Setting the style sheet
		this.getStylesheets().add(this.getClass().getResource("css/profile_settings.css").toExternalForm());
		this.getStyleClass().add("grid");
		
		// Library Settings
		musicLibrarySettings_Label = new Label("Music Library");
		musicLibrarySettings_Label.setId("LabelBig");
		
		musicLibraryPath_Label = new Label("Path to Library:");
		musicLibraryPath_Label.setId("LabelSmall");
		orderingMode_Label = new Label("Ordering Mode:");
		musicLibraryPath = new TextField("");
		musicLibraryPath.setEditable(false);
		
		orderingMode_Label = new Label("Ordering Mode");
		orderingMode_Label.setId("LabelSmall");
		orderingMode = new ChoiceBox<String>();
		orderingMode.setItems(FXCollections.observableArrayList(AAA, AA, GAA));	
		
		chooseDirToMusicLibrary = new Button();
		chooseDirToMusicLibrary.setId("OpenFolderButton");
		chooseDirToMusicLibrary.setGraphic(new ImageView(PropertiesUtils.getProperty(IconProperties.OPEN_FOLDER_IMPORT)));
		chooseDirToMusicLibrary.setOnAction(getButtonSetLibraryEventHandler());
		
		CenterGridPane.setConstraints(musicLibrarySettings_Label, 0, 0, 3, 1);
		CenterGridPane.setConstraints(musicLibraryPath_Label, 0, 1, 1, 1);
		CenterGridPane.setConstraints(musicLibraryPath, 1, 1, 1, 1);
		CenterGridPane.setConstraints(chooseDirToMusicLibrary, 2, 1, 1, 1);
		CenterGridPane.setConstraints(orderingMode_Label, 0, 2, 1, 1);
		CenterGridPane.setConstraints(orderingMode, 1, 2, 3, 1);
		
		// Import Settings
		importSettings_Label = new Label("Import Settings");
		importSettings_Label.setId("LabelBig");
		keepFiles_Label = new Label("Keep original files:");
		keepFiles_Label.setId("LabelSmall");
		keepFiles = new RadioButton();
		
		justTagFiles_Label = new Label("Just tag files:");
		justTagFiles_Label.setId("LabelSmall");
		justTagFiles = new RadioButton();
		
		CenterGridPane.setConstraints(importSettings_Label, 0, 4, 3, 1);
		CenterGridPane.setConstraints(keepFiles_Label, 0, 5, 1, 1);
		CenterGridPane.setConstraints(keepFiles, 1, 5, 1, 1);
		CenterGridPane.setConstraints(justTagFiles_Label, 0, 6, 1, 1);
		CenterGridPane.setConstraints(justTagFiles, 1, 6, 1, 1);
		
		// Playlist export settings
		playlistSettings_Label = new Label("Playlist Export Settings");
		playlistSettings_Label.setId("LabelBig");
		
		playListExport_Label = new Label("Playlist Export");
		playListExport_Label.setId("LabelSmall");
		playListExport = new RadioButton();
		
		playListName_Label = new Label("Playlist-Name:");
		playListName_Label.setId("LabelSmall");
		playListName = new TextField();
		
		playListExportPath_Label = new Label("Export Directory");
		playListExportPath_Label.setId("LabelSmall");
		playListExportPath = new TextField("");
		playListExportPath.setEditable(false);
		chooseDirToExportPlayList = new Button();
		chooseDirToExportPlayList.setId("OpenFolderButton");
		chooseDirToExportPlayList.setGraphic(new ImageView(PropertiesUtils.getProperty(IconProperties.OPEN_FOLDER_IMPORT)));
		chooseDirToExportPlayList.setOnAction(getButtonSetPlayListDirEventHandler());
		playListHeader = new Label("Playlist Header-Language");
		playListHeaderMode = new ChoiceBox<String>();
		playListHeaderMode.setItems(FXCollections.observableArrayList(germanHeader, englishHeader));	
		
		CenterGridPane.setConstraints(playlistSettings_Label, 0, 8, 3, 1);
		CenterGridPane.setConstraints(playListExport_Label, 0, 9, 1, 1);
		CenterGridPane.setConstraints(playListExport, 1, 9, 1, 1);
		CenterGridPane.setConstraints(playListName_Label, 0, 10, 1, 1);
		CenterGridPane.setConstraints(playListName, 1, 10, 1, 1);
		CenterGridPane.setConstraints(playListExportPath_Label, 0, 11, 1, 1);
		CenterGridPane.setConstraints(playListExportPath, 1, 11, 1, 1);
		CenterGridPane.setConstraints(chooseDirToExportPlayList, 2, 11, 1, 1);
		CenterGridPane.setConstraints(playListHeader, 0, 12, 1, 1);
		CenterGridPane.setConstraints(playListHeaderMode, 1, 12, 1, 1);
		
		/**
		 * Restore and apply button
		 */
		applyButton = new Button("Apply");
		applyButton.setOnAction(getButtonApplyEventHandler());
		
		restoreButton = new Button("Restore");
		restoreButton.setOnAction(getButtonRestoreEventHandler());
		
		CenterGridPane.setConstraints(restoreButton, 1, 13, 1, 1, HPos.RIGHT, VPos.CENTER);
		CenterGridPane.setConstraints(applyButton, 2, 13, 1, 1);
		
		this.getChildren().addAll(musicLibrarySettings_Label, musicLibraryPath_Label, musicLibraryPath,
				chooseDirToMusicLibrary, orderingMode_Label, orderingMode, importSettings_Label, keepFiles_Label,
				keepFiles, justTagFiles_Label, justTagFiles, playlistSettings_Label, playListExport_Label,
				playListExport, playListName_Label, playListName, playListExportPath_Label, playListExportPath,
				chooseDirToExportPlayList, restoreButton, applyButton,playListHeader,playListHeaderMode);
		
		/**
		 * Reading the properties and adding them to the pane
		 */
		readProperties();
	}
	
	/**
	 * Method to show the profile gridpane
	 */
	public void showPropertiesPage() {
		/**
		 * Reading the properties and adding them to the pane
		 */
		readProperties();
		
		propertiesStage = new Stage();
		propertiesStage.setTitle("Settings");

		propertiesStage.initModality(Modality.APPLICATION_MODAL);
		propertiesStage.setScene(scene);
		propertiesStage.show();
	}
	
	/**
	 * Method to read the properties and adding them to the pane
	 */
	private void readProperties() {
		ProfileProperties profile = PropertiesUtils.getProfile();
		
		// Musiclibrary properties
		musicLibraryPath.setText(profile.getPathToMusicLibrary());
		OrderingProperty orderIngProperty = profile.getOrderingMode();
		
		if (orderIngProperty == OrderingProperty.GAA) {
			orderingMode.setValue(GAA);
		} else if (orderIngProperty == OrderingProperty.AA) {
			orderingMode.setValue(AA);
		} else {
			orderingMode.setValue(AAA);
		}
		
		if (profile.getPlayListHeader().contains("german")) {
			playListHeaderMode.setValue(germanHeader);
		} else {
			playListHeaderMode.setValue(englishHeader);
		}
		
		// Import properties
		keepFiles.setSelected(profile.isKeepOriginalFiles());
		justTagFiles.setSelected(profile.isJustTagFiles());
		
		// Playlist properties
		playListExport.setSelected(profile.isPlayListExport());
		playListExportPath.setText(profile.getPlayListExportDir());
	}
	
	/**
	 * Returns the event handler for the apply button
	 * 
	 * @return the event handler for the apply button
	 */
	private EventHandler<ActionEvent> getButtonApplyEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProfileProperties profile = PropertiesUtils.getProfile();

				Boolean proceed;
				
				// Ask the user if he wants to proceed if music library properties are changed
				if (!musicLibraryPath.getText().equals(profile.getPathToMusicLibrary())
						|| differentOrderingMode(profile)) {
					proceed = showAlert();
				} else {
					proceed = true;
				}

				if (proceed) {
					// Writing the properties
					profile.setPathToMusicLibrary(musicLibraryPath.getText());

					if (orderingMode.getValue().equals(GAA)) {
						profile.setOrderingMode(OrderingProperty.GAA);
					} else if (orderingMode.getValue().equals(AA)) {
						profile.setOrderingMode(OrderingProperty.AA);
					} else {
						profile.setOrderingMode(OrderingProperty.AAA);
					}
					
					if (playListHeaderMode.getValue().toLowerCase().contains("english")) {
						profile.setPlayListHeader(Header.ENGLISH.toString());
					} else {
						profile.setPlayListHeader(Header.GERMAN.toString());
					}
					
					profile.setKeepOriginalFiles(keepFiles.isSelected());
					profile.setJustTagFiles(justTagFiles.isSelected());

					profile.setPlayListExport(playListExport.isSelected());
					profile.setPlayListExportDir(playListExportPath.getText());

					PropertiesUtils.saveProfile();
				} else {
					// Restores the settings
					restoreButton.fire();
				}

			}

		};
	}
	
	/**
	 * Method to determine if a different ordering mode was chosen
	 * 
	 * @param profile the original profile
	 * @return boolean indicating whether a different ordering mode was chosen
	 */
	private boolean differentOrderingMode(ProfileProperties profile) {
		OrderingProperty newProperty;
		
		if (orderingMode.getValue().equals(GAA)) {
			newProperty = OrderingProperty.GAA;
		} else if (orderingMode.getValue().equals(AA)) {
			newProperty = OrderingProperty.AA;
		} else {
			newProperty = OrderingProperty.AAA;
		}
		
		if (newProperty.equals(profile.getOrderingMode())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the event handler for the restore button
	 * 
	 * @return the event handler for the restore button
	 */
	private EventHandler<ActionEvent> getButtonRestoreEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				readProperties();
			}
			
		};
	}
	
	/**
	 * Returns the event handler for the set library button
	 * 
	 * @return the event handler for the set library button
	 */
	private EventHandler<ActionEvent> getButtonSetLibraryEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				final DirectoryChooser chooser = new DirectoryChooser();
				
				File library = new File(PropertiesUtils.getProfile().getPathToMusicLibrary());
				
				if (library.exists() && library.isDirectory()) {
					chooser.setInitialDirectory(library);
				} else {
					chooser.setInitialDirectory(new File(System.getProperty("user.home")));
				}
	
				chooser.setTitle("Choose Musiclibrary Directory");
				File selectedDir = chooser.showDialog(propertiesStage);

				if (selectedDir != null) {
					musicLibraryPath.setText(selectedDir.getAbsolutePath());
				}
			}
			
		};
	}
	
	/**
	 * Returns the event handler for the set playlist export dir button
	 * 
	 * @return the event handler for the playlist export dir button
	 */
	private EventHandler<ActionEvent> getButtonSetPlayListDirEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				final DirectoryChooser chooser = new DirectoryChooser();
				
				File exportDir = new File(PropertiesUtils.getProfile().getPlayListExportDir());
				
				if (exportDir.exists() && exportDir.isDirectory()) {
					chooser.setInitialDirectory(exportDir);
				} else {
					chooser.setInitialDirectory(new File(System.getProperty("user.home")));
				}
				
				chooser.setTitle("Choose Playlist Export Directory");
				
				File selectedDir = chooser.showDialog(propertiesStage);
				
				if (selectedDir != null) {
					Main_Frame.getPrimaryStage().toBack();
					
					playListExportPath.setText(selectedDir.getAbsolutePath());
				}
			}
			
		};
	}
	
	/**
	 * Method to show a dialog when music library properties are changed
	 * 
	 * @return boolean indicating wheter the user wants to proceed
	 */
	private boolean showAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		// Setting the dialog text
		alert.setTitle(header);
		alert.setHeaderText(header_text);
		
		// Setting the buttons
		ButtonType buttonContinue = new ButtonType("Continue",ButtonData.OK_DONE);
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE); 
		
		// Adding the buttons to the dialog
		alert.getButtonTypes().setAll(buttonContinue, buttonCancel);
		
		// Showing the dialog and waiting for the answer
		Optional<ButtonType> result = alert.showAndWait();
		
		// Determining the choosen import Mode
		if (result.get() == buttonContinue){
		    return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the name of the playlist
	 * 
	 * @return the name of the playlist
	 */
	public String getPlaListyName() {
		return this.playListName.getText();
	}
	
	/**
	 * Returns an instance of the Profile Settings
	 * 
	 * @return instance of the Profile Settings
	 */
	public static ProfileSettings getInstance() {
		return profileSettings;
	}
}
