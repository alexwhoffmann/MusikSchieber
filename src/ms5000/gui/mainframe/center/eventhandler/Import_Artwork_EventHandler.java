package ms5000.gui.mainframe.center.eventhandler;

import java.io.File;
import java.io.IOException;
import org.jaudiotagger.tag.datatype.Artwork;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.gui.GuiProperties;
/**
 * This class implements the functionalty of the import artwork from file button
 */
public class Import_Artwork_EventHandler implements EventHandler<ActionEvent> {
	/**
	 * The extension filter for the accepted image files 
	 */
	private final ExtensionFilter extFilter = new ExtensionFilter("Image files", "*.jpeg", "*.png","*.jpg");
	
	/**
	 * The title of the File chooser
	 */
	private final String chooserTitle = "Choose Import File";
	
	/**
	 * The last imported directory
	 */
	private File lastImportedDir;
	
	
	/**
	 * Method to handle the action event
	 */
	@Override
	public void handle(ActionEvent arg0) {
		// Making sure that at least one entry was selected
		if(Main_Frame.getBorderPane_Center().getCentertable().getSelectionModel().getSelectedIndices().size() > 0) {
			// Getting the artwork file
			File artwork_file = openFileChooser();
			
			if (artwork_file != null) {
				try {
					// Creating the artwork
					Artwork artwork = Artwork.createArtworkFromFile(artwork_file);
					PropertiesUtils.setProperty(GuiProperties.LASTIMPORTDIRARTWORK, artwork_file.getParent());
					// Storing the artwork into to selected entries
					for (Integer index : Main_Frame.getBorderPane_Center().getCentertable().getSelectionModel().getSelectedIndices()) {
						Main_Frame.getBorderPane_Center().getCentertable().getItems().get(index.intValue()).setArtwork(artwork);
					}
					
					// Setting the artwork to the screen
					Main_Frame.getBorderPane_Center().getCenterGridPane().setArtWorkImage(artwork);
				} catch (IOException e) {
					// Exception Handling
					try {
						Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("Failed to import Image file!");
						Thread.sleep(2000);
						Main_Frame.getBorderPaneTopCenter().resetStatusFields();
					} catch (InterruptedException e1) {
						Main_Frame.getBorderPaneTopCenter().resetStatusFields();
					}
				}
			}
		}
	}
	
	/**
	 * Method to open the file chooser
	 * 
	 * @return the imported file
	 */
	private File openFileChooser() {
		final FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(readProperties());
		
		chooser.setTitle(chooserTitle);
		chooser.getExtensionFilters().add(extFilter);
		File selectedFile = chooser.showOpenDialog(Main_Frame.getPrimaryStage());
		
		return selectedFile;
	}
	
	/**
	 * Method to read the properties
	 * 
	 * @return returns the last imported dir as file
	 */
	private File readProperties() {
		// Reading the last Dir which was Imported
		lastImportedDir = new File(PropertiesUtils.getProperty(GuiProperties.LASTIMPORTDIRARTWORK));
		System.out.println(lastImportedDir.getAbsolutePath());
		if (!lastImportedDir.exists()) {
			String userDirectoryString = System.getProperty("user.home");
			lastImportedDir = new File(userDirectoryString);
		}
		
		return lastImportedDir;
		
	}
}
