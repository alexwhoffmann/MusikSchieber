package ms5000.gui.mainframe.center;


import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import ms5000.gui.mainframe.Main_Frame;

public class BorderPane_CENTER extends BorderPane{
	private final static ProgressBar progressBar = new ProgressBar(0);
	private static double width;
	private static final CenterTable centerTable = new CenterTable();
	private static final CenterGridPane centerGridPane = new CenterGridPane();
	
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
		
		System.out.println(centerGridPane.getId());
	}

	public static ProgressBar getProgressBar() {
		return progressBar;
	}

	public static double getWidthOfPane() {
		return width;
	}

	public static CenterTable getCentertable() {
		return centerTable;
	}

	public static CenterGridPane getCenterGridPane() {
		return centerGridPane;
	}
	
	
}
