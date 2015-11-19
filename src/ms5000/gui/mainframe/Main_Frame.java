package ms5000.gui.mainframe;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.gui.mainframe.top.HBox_TOP_LEFT;
import ms5000.gui.mainframe.top.HBox_TOP_RIGHT;


public class Main_Frame extends Application {
	private static Stage primaryStage;
	private HBox_TOP_LEFT hBox_Left;
	private HBox_TOP_RIGHT hBox_Right;
	private BorderPane_CENTER borderPane_Center;
	private static final BorderPane root = new BorderPane();;
	private BorderPane top;
	public final static double minFrameWidth = 1300; 
	private static final Scene scene = new Scene(root,400,400);;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//the root pane
			Main_Frame.setPrimaryStage(primaryStage);
			
			//Main-Frame Settings
			scene.getStylesheets().add(getClass().getResource("mainframe.css").toExternalForm());
			primaryStage.setScene(scene);
			
			//Setting the inital size to screen size
			primaryStage.setMaximized(true);
			
			//Setting the Top Section
			this.top = new BorderPane();
			root.setTop(top);
			hBox_Left = new HBox_TOP_LEFT();
			hBox_Right = new HBox_TOP_RIGHT();
			top.setLeft(hBox_Left);
			top.setRight(hBox_Right);
			top.setCenter(new BoderPane_TOP_CENTER());
			
			//Show the Frame
			primaryStage.show();
			
			//Setting the Center Section
			borderPane_Center = new BorderPane_CENTER(); 
			root.setCenter(borderPane_Center);
			
			//Setting the minimum size
			Main_Frame.getPrimaryStage().setMinWidth(minFrameWidth);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		Main_Frame.primaryStage = primaryStage;
	}
	
	public static double getPrefFrameWidth() {
		return Main_Frame.primaryStage.getWidth();
	}

	public static double getMinFrameWidth() {
		return minFrameWidth;
	}

	public static Scene getScene() {
		return scene;
	}
}
