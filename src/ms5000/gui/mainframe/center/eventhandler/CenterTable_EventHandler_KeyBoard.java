package ms5000.gui.mainframe.center.eventhandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.gui.mainframe.center.CenterTable;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileType;

public class CenterTable_EventHandler_KeyBoard implements EventHandler<KeyEvent>{
	private CenterTable table;
	private final ExtensionFilter extFilter = new ExtensionFilter("Music files", MusicFileType.getExtensionValues());
	private final String PROPERTIES = "properties/gui.properties";
	private Properties properties;
	private File lastImportedDir;
	private static boolean controlPressed;
	
	public CenterTable_EventHandler_KeyBoard(CenterTable table) {
		this.table = table;
		
		
	}
	
	@Override
	public void handle(KeyEvent event) {
		if(event.getCode() == KeyCode.INSERT) {
			controlPressed = false;
			final FileChooser chooser = new FileChooser();
			chooser.setInitialDirectory(readProperties());
			
			chooser.setTitle("Choose Import File");
			chooser.getExtensionFilters().add(extFilter);
			File selectedFile = chooser.showOpenDialog(Main_Frame.getPrimaryStage());
			
			if (selectedFile != null) {
				//Setting the properties to the last imported Dir
				saveProperties(selectedFile.getParent());
				try {
					table.getItems().add(new MusicFile(selectedFile.getAbsolutePath()).getTag());
				} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
						| InvalidAudioFrameException e) {
					// TODO Auto-generated catch block
					BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Faild to import: " + selectedFile.getName());
				}
			}
		} else if (event.getCode() == KeyCode.DELETE) {
			controlPressed = false;
			table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
			table.refresh();
		} else if (event.getCode() == KeyCode.CONTROL) {
			controlPressed = true;
		} else if (controlPressed == true && event.getCode() == KeyCode.S) {
			CenterGridPane.save_Tag.fire();
			
		} else {
			controlPressed = false;
		}
	}
	
	private void saveProperties(String lastImportDir) {
		FileOutputStream output = null;
		
		try {
			output = new FileOutputStream(PROPERTIES);

			// set the properties value
			properties.setProperty("lastImportDir_Single", lastImportDir);

			// save properties to project root folder
			properties.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

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
