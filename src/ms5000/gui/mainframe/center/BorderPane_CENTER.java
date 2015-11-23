package ms5000.gui.mainframe.center;


import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import ms5000.gui.mainframe.Main_Frame;

/**
 * BorderPane which holds the upper progress bar, the table and the detail view
 *
 */
public class BorderPane_CENTER extends BorderPane{
	
	/**
	 * the progress bar
	 */
	private final static ProgressBar progressBar = new ProgressBar(0);
	
	/**
	 * the width of the border pane
	 */
	private static double width;
	
	/**
	 * the center table which holds the music file entries
	 */
	private static final CenterTable centerTable = new CenterTable();
	
	/**
	 * the detail view to each entry
	 */
	private static final CenterGridPane centerGridPane = new CenterGridPane();
	
	/**
	 * instantiates the borderPane
	 */
	public BorderPane_CENTER() {
		
		// Setting the progressbar to the top
		this.setTop(progressBar);
		
		// Setting the progress bar style
		progressBar.setStyle("-fx-control-inner-background: #999999;-fx-accent: #A7FFA7");
		
		// Setting the Size of the progress bar
		progressBar.setPrefWidth(Main_Frame.getPrefFrameWidth());
		progressBar.setMinWidth(Main_Frame.getMinFrameWidth());
		
		
		this.setLeft(centerTable);
		this.setCenter(centerGridPane);
	}

	/**
	 * Returns the instance of the progress bar
	 * 
	 * @return the progress bar
	 */
	public static ProgressBar getProgressBar() {
		return progressBar;
	}
	
	/**
	 * Returns the width of the pane
	 *  
	 * @return the width
	 */
	public static double getWidthOfPane() {
		return width;
	}
	
	/**
	 * Returns the instance of the center table
	 *  
	 * @return the instance of the center table
	 */
	public static CenterTable getCentertable() {
		return centerTable;
	}
	
	/**
	 * Returns the instance of the center grid pane
	 * 
	 * @return the instance of the center grid pane
	 */
	public static CenterGridPane getCenterGridPane() {
		return centerGridPane;
	}
	
	
}
