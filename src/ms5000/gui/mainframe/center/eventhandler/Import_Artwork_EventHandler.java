package ms5000.gui.mainframe.center.eventhandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.jaudiotagger.tag.datatype.Artwork;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;

public class Import_Artwork_EventHandler implements EventHandler<ActionEvent> {
	
	private final ExtensionFilter extFilter = new ExtensionFilter("Image files", "*.jpeg", "*.png","*.jpg");
	private final String PROPERTIES = "properties/gui.properties";
	private Properties properties;
	private File lastImportedDir;
	
	
	@Override
	public void handle(ActionEvent arg0) {
		if(BorderPane_CENTER.getCentertable().getSelectionModel().getSelectedIndices().size() > 0) {
			File artwork_file = openFileChooser();
			Artwork artwork;
			
			try {
				artwork = Artwork.createArtworkFromFile(artwork_file);
				
				if (artwork != null) {
					for (Integer index : BorderPane_CENTER.getCentertable().getSelectionModel().getSelectedIndices()) {
						BorderPane_CENTER.getCentertable().getItems().get(index.intValue()).setArtwork(artwork);
					}
				}
				
				BorderPane_CENTER.getCenterGridPane().setArtWorkImage(artwork);
			} catch (IOException e) {
				try {
					BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Failed to import Image file!");
					Thread.sleep(2000);
					BoderPane_TOP_CENTER.getStatusSlider().setStatusText("");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private File openFileChooser() {
		final FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(readProperties());
		
		chooser.setTitle("Choose Image File");
		chooser.getExtensionFilters().add(extFilter);
		File selectedFile = chooser.showOpenDialog(Main_Frame.getPrimaryStage());
		
		if (selectedFile != null) {
			return selectedFile;
		} else {
			return null;
		}
	}
	
	
	private File readProperties() {
		// Reading the properties
		if (this.properties == null) {
			this.properties = new Properties();
			BufferedInputStream stream;
			try {
				stream = new BufferedInputStream(new FileInputStream(PROPERTIES));
				properties.load(stream);
				stream.close();

				// Reading the last Dir which was Imported1
				lastImportedDir = new File(properties.getProperty("lastImportDir_Single"));
				if (!lastImportedDir.exists()) {
					String userDirectoryString = System.getProperty("user.home");
					lastImportedDir = new File(userDirectoryString);
				}
				// In case the Properties coudn't be read
			} catch (FileNotFoundException e) {
				String userDirectoryString = System.getProperty("user.home");
				lastImportedDir = new File(userDirectoryString);
			} catch (IOException e) {
				String userDirectoryString = System.getProperty("user.home");
				lastImportedDir = new File(userDirectoryString);
			}
		} else {
			// Reading the last Dir which was Imported
			lastImportedDir = new File(properties.getProperty("lastImportDir_Single"));
			if (!lastImportedDir.exists()) {
				String userDirectoryString = System.getProperty("user.home");
				lastImportedDir = new File(userDirectoryString);
			}
		}
		return lastImportedDir;
		
	}
}
