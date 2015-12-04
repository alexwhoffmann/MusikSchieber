package ms5000.gui.chooser;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.properties.PropertiesUtils;

/**
 * This class implements the functionality of the directory chooser
 */
public class DirChooser {
	/**
	 * The selected directory
	 */
	private File selectedDir = null;
	
	/**
	 * Constructs the dir chooser
	 * 
	 * @param initialDir the initial directory 
	 * @param title the title
	 */
	public DirChooser(File initialDir, String title) {
		final DirectoryChooser chooser = new DirectoryChooser();
		
		if (initialDir.exists() && initialDir.isDirectory()) {
			chooser.setInitialDirectory(initialDir);
		} else {
			chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
		
		chooser.setTitle(PropertiesUtils.getString(title));
		
		this.selectedDir = chooser.showDialog(Main_Frame.getPrimaryStage());
	}
	
	/**
	 * Constructs the dir chooser
	 * 
	 * @param initialDir the initial directory 
	 * @param title the title
	 * @param stage the stage the chooser gets viewed on
	 */
	public DirChooser(File initialDir, String title, Stage stage) {
		final DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory(initialDir);
		chooser.setTitle(PropertiesUtils.getString(title));
		
		this.selectedDir = chooser.showDialog(stage);
	}
	
	/**
	 * Returns the selected directory as file
	 * 
	 * @return the selected directory as file
	 */
	public File getSelectedDir() {
		return this.selectedDir;
	}
}
