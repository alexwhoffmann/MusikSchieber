package ms5000.gui.mainframe.center;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.eventhandler.CenterTable_ChangeListener;
import ms5000.gui.mainframe.center.eventhandler.CenterTable_EventHandler_DragNDrop;
import ms5000.gui.mainframe.center.eventhandler.CenterTable_EventHandler_KeyBoard;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.MusicTag.TagState;

public class CenterTable extends TableView<MusicTag> {
	private TableColumn<MusicTag, String> artistCol;
	private TableColumn<MusicTag, String> albumCol;
	private TableColumn<MusicTag, String> trackCol;
	private TableColumn<MusicTag, String> genreCol;
	private final String placeholder_icon = "file:icons/green_music_icon_table.png";
	private final String missingCritical = "-fx-background-color: rgb(255, 153, 153,0.3)";
	private final String missingNonCritical = "-fx-background-color: rgb(255, 255, 179,0.3)";
	private final String missingWeak = "-fx-background-color: rgb(255, 255, 229,0.45)";
	private final String duplicate = "-fx-background-color: rgb(179, 255, 179,0.3)";
	
	@SuppressWarnings({ "unchecked"})
	public CenterTable() {
		// Setting the Columns
		Image placeHolder = new Image(placeholder_icon);
		
		artistCol = new TableColumn<MusicTag, String>("Artist");
		artistCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("artist"));
		setWidth(artistCol);
		artistCol.setCellFactory(getCellFactory());
		
		albumCol = new TableColumn<MusicTag, String>("Album");
		albumCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("album"));
		setWidth(albumCol);
		albumCol.setCellFactory(getCellFactory());
		
		trackCol = new TableColumn<MusicTag, String>("Track");
		trackCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("titlename"));
		setWidth(trackCol);
		trackCol.setCellFactory(getCellFactory());
		
		genreCol = new TableColumn<MusicTag, String>("Genre");
		genreCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("genre"));
		setWidth(genreCol);
		genreCol.setCellFactory(getCellFactory());
		
		this.getColumns().addAll(artistCol, albumCol, trackCol, genreCol);
		
		
		
		// Setting the table properties
		this.setColumnResizePolicy(CenterTable.CONSTRAINED_RESIZE_POLICY);
		this.setPlaceholder(new ImageView(placeHolder));
		this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.setOnKeyPressed(new CenterTable_EventHandler_KeyBoard(this));
		CenterTable_EventHandler_DragNDrop dragAndDropHandler = new CenterTable_EventHandler_DragNDrop();
		this.setOnDragOver(dragAndDropHandler);
		this.setOnDragDropped(dragAndDropHandler);
		this.getSelectionModel().selectedItemProperty().addListener(new CenterTable_ChangeListener(this));
		
	}

	private void setWidth(TableColumn<MusicTag, String> col) {
		col.setPrefWidth(Main_Frame.getPrefFrameWidth() / 8);
		col.setMinWidth(Main_Frame.getMinFrameWidth() / 16);
		col.setMaxWidth(Main_Frame.getPrefFrameWidth() / 8);
		col.setResizable(true);
	}
	
	public CenterTable getInstace() {
		return this;
	}
	
	public Callback<TableColumn<MusicTag, String>, TableCell<MusicTag, String>> getCellFactory() {
		Callback<TableColumn<MusicTag, String>, TableCell<MusicTag, String>> cellFactory =
		        new Callback<TableColumn<MusicTag, String>, TableCell<MusicTag, String>>() {
		            @SuppressWarnings("rawtypes")
					public TableCell call(TableColumn p) {
		                TableCell<MusicTag, String> cell = new TableCell<MusicTag, String>() {
		                    @Override
		                    public void updateItem(String item, boolean empty) {
		                        super.updateItem(item, empty);
		                        
		                        
		                        if (!isEmpty() && this.getTableRow().getItem() != null) {
		                        	MusicTag tag = ((MusicTag) this.getTableRow().getItem());
		                        	if (tag.getStatus() == TagState.DUPLICATE) {
		                        		this.setStyle(duplicate);
		                        	} else if(tag.getStatus() == TagState.MISSINGCRITICAL) {
		                        		this.setStyle(missingCritical);
		                        	} else if (tag.getStatus() == TagState.MISSINGNONCRITICAL) {
		                        		this.setStyle(missingNonCritical);
		                        	} else if (tag.getStatus() == TagState.MISSINGWEAKINFOS) {
		                        		this.setStyle(missingWeak);
		                        	}
		                            
		                            setText(item);
		                        }
		                        
		                    }
		                };

		                return cell;
		            }
		        };
		
		
		return cellFactory;
	}
}
