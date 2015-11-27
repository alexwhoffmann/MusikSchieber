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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.properties.ProfileProperties;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.OrderingProperty;

public class ProfileSettings  extends GridPane {
	private final String openFolder_Image_Path = "file:icons/Folder_Open.png";
	private final Image openFolder_Image = new Image(openFolder_Image_Path);
	
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
	
	private TextField musicLibraryPath;
	private Button chooseDirToMusicLibrary;
	private ChoiceBox<String> orderingMode;
	private final String AAA = "A - Artist - Album";
	private final String AA = "Artist - Album";
	private final String GAA = "Genre - Artist - Album";
	
	private RadioButton keepFiles;
	private RadioButton justTagFiles;
	
	private TextField playListExportPath;
	private RadioButton playListExport;
	private Button chooseDirToExportPlayList;
	private TextField playListName;
	
	private Button applyButton;
	private Button restoreButton;
	
	private Stage propertiesStage;
	
	// Toggle group for the radio buttons
	private final ToggleGroup toggleGroup = new ToggleGroup();
	
	public ProfileSettings(){
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
		chooseDirToMusicLibrary.setGraphic(new ImageView(openFolder_Image));
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
		keepFiles.setToggleGroup(toggleGroup);
		
		justTagFiles_Label = new Label("Just tag files:");
		justTagFiles_Label.setId("LabelSmall");
		justTagFiles = new RadioButton();
		justTagFiles.setToggleGroup(toggleGroup);
		
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
		chooseDirToExportPlayList.setGraphic(new ImageView(openFolder_Image));
		chooseDirToExportPlayList.setOnAction(getButtonSetPlayListDirEventHandler());
		
		CenterGridPane.setConstraints(playlistSettings_Label, 0, 8, 3, 1);
		CenterGridPane.setConstraints(playListExport_Label, 0, 9, 1, 1);
		CenterGridPane.setConstraints(playListExport, 1, 9, 1, 1);
		CenterGridPane.setConstraints(playListName_Label, 0, 10, 1, 1);
		CenterGridPane.setConstraints(playListName, 1, 10, 1, 1);
		CenterGridPane.setConstraints(playListExportPath_Label, 0, 11, 1, 1);
		CenterGridPane.setConstraints(playListExportPath, 1, 11, 1, 1);
		CenterGridPane.setConstraints(chooseDirToExportPlayList, 2, 11, 1, 1);
		
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
				chooseDirToExportPlayList, restoreButton, applyButton);
		
		readProperties();
	}
	
	public void showPropertiesPage() {
		propertiesStage = new Stage();
		propertiesStage.setTitle("Settings");
		Scene scene = new Scene(this);
		propertiesStage.initModality(Modality.APPLICATION_MODAL);
		propertiesStage.setScene(scene);
		propertiesStage.show();
	}
	
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
		
		// Import properties
		keepFiles.setSelected(profile.isKeepOriginalFiles());
		keepFiles.setToggleGroup(toggleGroup);
		justTagFiles.setSelected(profile.isJustTagFiles());
		justTagFiles.setToggleGroup(toggleGroup);
		
		// Playlist properties
		playListExport.setSelected(profile.isPlayListExport());
		playListExportPath.setText(profile.getPlayListExportDir());
	}
	
	private EventHandler<ActionEvent> getButtonApplyEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProfileProperties profile = PropertiesUtils.getProfile();

				Boolean proceed;

				if (!musicLibraryPath.getText().equals(profile.getPathToMusicLibrary())
						|| differentOrderingMode(profile)) {
					proceed = showAlert();
				} else {
					proceed = true;
				}

				if (proceed) {
					profile.setPathToMusicLibrary(musicLibraryPath.getText());

					if (orderingMode.getValue().equals(GAA)) {
						profile.setOrderingMode(OrderingProperty.GAA);
					} else if (orderingMode.getValue().equals(AA)) {
						profile.setOrderingMode(OrderingProperty.AA);
					} else {
						profile.setOrderingMode(OrderingProperty.AAA);
					}

					profile.setKeepOriginalFiles(keepFiles.isSelected());
					profile.setJustTagFiles(justTagFiles.isSelected());

					profile.setPlayListExport(playListExport.isSelected());
					profile.setPlayListExportDir(playListExportPath.getText());

					PropertiesUtils.saveProfile();
				} else {
					restoreButton.fire();
				}

			}

		};
	}
	
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
	
	private EventHandler<ActionEvent> getButtonRestoreEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				readProperties();
			}
			
		};
	}
	
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
	
	private boolean showAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		String header = "Changing library properties";
		// Setting the dialog text
		alert.setTitle(header);
		String header_text = "You are about to change the library settings. Do you want to continue?";
		alert.setHeaderText(header_text );
		
		
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
	
	
}
