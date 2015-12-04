package ms5000.gui.chooser;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.musicfile.file.MusicFileType;
import ms5000.properties.PropertiesUtils;

/**
 * This class implements a file chooser to choose different files from the file system
 */
public class FileChoooser {
	/**
	 * Enumeration used to determine the right extension filter
	 */
	public enum ChooserFileTypes {MUSIC,IMAGE};
	
	/**
	 * The extension filter for including music 
	 */
	private final ExtensionFilter extFilterMusic = new ExtensionFilter(
			PropertiesUtils.getString("center.section.text.table.file.chooser.ext.filter"),
			MusicFileType.getExtensionValues());
	
	/**
	 * The extension filter for the accepted image files 
	 */
	private final ExtensionFilter extFilterImage = new ExtensionFilter(
			PropertiesUtils.getString("center.section.text.image.file.chooser.ext.filter"),
			PropertiesUtils.getArray("util.config.image.types"));
	
	/**
	 * The selected file
	 */
	private File selectedFile = null;
	
	/**
	 * Constructs the chooser
	 * 
	 * @param fileType the extension filter file type
	 * @param initialDir the initial directory
	 * @param title the title of the chooser
	 */
	public FileChoooser(ChooserFileTypes fileType, File initialDir, String title) {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle(title);
		
		if (initialDir.exists() && initialDir.isDirectory()) {
			fileChooser.setInitialDirectory(initialDir);
		} else {
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
		
		if (fileType == ChooserFileTypes.MUSIC) {
			
			fileChooser.getExtensionFilters().add(extFilterMusic);
		} else {
			fileChooser.getExtensionFilters().add(extFilterImage);
		}
		
		this.selectedFile = fileChooser.showOpenDialog(Main_Frame.getPrimaryStage());
			
	}
	
	/**
	 * Returns the selected file
	 * 
	 * @return the selected file
	 */
	public File getSelectedFile() {
		return this.selectedFile;
	}
}
