package ms5000.gui.mainframe;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ms5000.gui.mainframe.bottom.BorderPane_Bottom;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.gui.mainframe.top.HBox_TOP_LEFT;
import ms5000.gui.mainframe.top.HBox_TOP_RIGHT;
import ms5000.properties.PropertiesUtils;

/**
 * Class with starts the application and shows the mainframe
 */
public class Main_Frame extends Application {

	/**
	 * the primary stage of the frame
	 */
	private static Stage primaryStage;

	/**
	 * HBox with holds the start and stop button
	 */
	private static HBox_TOP_LEFT hBox_Left;

	/**
	 * HBox with holds the import button
	 */
	private static HBox_TOP_RIGHT hBox_Right;

	/**
	 * BorderPane which holds the list and the detail view
	 */
	private static BorderPane_CENTER borderPane_Center;

	/**
	 * The root borderpane
	 */
	private static final BorderPane root = new BorderPane();;

	/**
	 * The borderpane at the top
	 */
	private BorderPane top;

	/**
	 * The BorderPane at the bottom
	 */
	private static BorderPane_Bottom bottom;

	/**
	 * the minimal frame width
	 */
	private final static double minFrameWidth = 1300;

	/**
	 * the scene of the main frame
	 */
	private static final Scene scene = new Scene(root, 400, 400);

	/**
	 * The instance of the Border Pane in the top
	 */
	private static BoderPane_TOP_CENTER borderPaneTopCenter;

	@Override
	/**
	 * Shows the main frame
	 */
	public void start(Stage primaryStage) {
		try {
			// the root pane
			Main_Frame.setPrimaryStage(primaryStage);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			System.out.println(primaryScreenBounds.getWidth());
			
			
			
			// Main-Frame Settings
			primaryStage.setScene(scene);

			scene.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
						Number newSceneWidth) {
					
					if(getBorderPane_Center() != null) {
						getBorderPane_Center().getCenterGridPane().applyCSS(newSceneWidth.doubleValue());
					}
					
				}
			});
			
			// Setting the initial size to screen size
			primaryStage.setMaximized(true);
			
			// Setting the title of the primary stage
			primaryStage.setTitle(PropertiesUtils.getString("top.section.text.mainframe.title"));

			// Setting the Top Section
			this.top = new BorderPane();
			top.prefWidthProperty().bind(Main_Frame.getScene().widthProperty());
			root.setTop(top);
			hBox_Left = new HBox_TOP_LEFT();
			hBox_Right = new HBox_TOP_RIGHT();
			top.setLeft(hBox_Left);
			top.setRight(hBox_Right);

			borderPaneTopCenter = new BoderPane_TOP_CENTER();
			top.setCenter(borderPaneTopCenter);

			// Show the Frame
			primaryStage.show();

			// Setting the Center Section
			borderPane_Center = new BorderPane_CENTER();
			root.setCenter(borderPane_Center);

			// Setting the bottom section
			bottom = new BorderPane_Bottom();
			root.setBottom(bottom);
			bottom.setPrefHeight(200);

			// Setting the minimum size
			Main_Frame.getPrimaryStage().setMinWidth(minFrameWidth);

			// Setting Sizes of components
			borderPaneTopCenter.getStatusSlider().getSlider().setProgressBarWidth();
			hBox_Right.getVolumeSlider().getVolumeSlider().setProgressBarWidth();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to start the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Method to return the primary stage of the mainframe
	 * 
	 * @return the primary stage
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Method to set the primary stage of the mainframe
	 * 
	 * @param primaryStage
	 */
	public static void setPrimaryStage(Stage primaryStage) {
		Main_Frame.primaryStage = primaryStage;
	}

	/**
	 * Method to return the preferred frame width
	 * 
	 * @return the preferred frame with of the mainframe
	 */
	public static double getPrefFrameWidth() {
		return Main_Frame.primaryStage.getWidth();
	}

	/**
	 * Method to return the minimal frame width
	 * 
	 * @return the minimal frame with of the mainframe
	 */
	public static double getMinFrameWidth() {
		return minFrameWidth;
	}

	/**
	 * Method to return the scene of the mainframe
	 *
	 * @return the scene
	 */
	public static Scene getScene() {
		return scene;
	}

	/**
	 * Method to return the instance of the HBox in the top left side
	 * 
	 * @return the instance of the HBox_TOP_LEFT
	 */
	public static HBox_TOP_LEFT gethBox_Left() {
		return hBox_Left;
	}

	/**
	 * Method to return the instance of the HBox in the top right side
	 * 
	 * @return the instance of the HBox_TOP_Right
	 */
	public static HBox_TOP_RIGHT gethBox_Right() {
		return hBox_Right;
	}

	/**
	 * Method to return an instance of the border pane in the top
	 * 
	 * @return an instance of the borderPane in the top
	 */
	public static BoderPane_TOP_CENTER getBorderPaneTopCenter() {
		return borderPaneTopCenter;
	}

	/**
	 * Method to return an instance of the border pane in the center
	 * 
	 * @return an instance of the borderPane in the center
	 */
	public static BorderPane_CENTER getBorderPane_Center() {
		return borderPane_Center;
	}

	/**
	 * Method to return an instance of the border pane at the bottom
	 * 
	 * @return an instance of the borderPane at the bottom
	 */
	public static BorderPane_Bottom getBorderPaneBottom() {
		return bottom;
	}

	/**
	 * Method to return an instance of the root border pane
	 * 
	 * @return an instance of the root border pane
	 */
	public static BorderPane getRoot() {
		return root;
	}
}
