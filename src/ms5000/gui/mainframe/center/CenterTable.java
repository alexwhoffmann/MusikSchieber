package ms5000.gui.mainframe.center;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.eventhandler.CenterTable_ChangeListener;
import ms5000.gui.mainframe.center.eventhandler.CenterTable_EventHandler_DragNDrop;
import ms5000.gui.mainframe.center.eventhandler.CenterTable_EventHandler_KeyBoard;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.TagState;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.icons.IconProperties;

/**
 * This class implements the main features of the table view 
 *
 */
public class CenterTable extends TableView<MusicTag> {
	/**
	 * The columns 
	 */
	private TableColumn<MusicTag, String> artistCol;
	private TableColumn<MusicTag, String> albumCol;
	private TableColumn<MusicTag, String> trackCol;
	private TableColumn<MusicTag, String> genreCol;
	
	/**
	 * Strings to style the row's
	 */
	private final String missingCritical = PropertiesUtils.getString("center.section.config.css.color.table.missing.critical");
	private final String missingNonCritical = PropertiesUtils.getString("center.section.config.css.color.table.missing.non.critical");
	private final String missingWeak = PropertiesUtils.getString("center.section.config.css.color.table.missing.weak");
	private final String duplicate = PropertiesUtils.getString("center.section.config.css.color.table.duplicate");
	
	/**
	 * Boolean indicating whether the list holds entries with incomplete tags
	 */
	private boolean inCompleteTags = false;
	
	@SuppressWarnings("unchecked")
	public CenterTable() {
		
		
		// Setting the Columns
		artistCol = new TableColumn<MusicTag, String>(PropertiesUtils.getString("center.section.text.column.artist"));
		artistCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("artist"));
		setWidth(artistCol);
		artistCol.setCellFactory(getCellFactory());
		
		albumCol = new TableColumn<MusicTag, String>(PropertiesUtils.getString("center.section.text.column.album"));
		albumCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("album"));
		setWidth(albumCol);
		albumCol.setCellFactory(getCellFactory());
		
		trackCol = new TableColumn<MusicTag, String>(PropertiesUtils.getString("center.section.text.column.track"));
		trackCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("titlename"));
		setWidth(trackCol);
		trackCol.setCellFactory(getCellFactory());
		
		genreCol = new TableColumn<MusicTag, String>(PropertiesUtils.getString("center.section.text.column.genre"));
		genreCol.setCellValueFactory(new PropertyValueFactory<MusicTag, String>("genre"));
		setWidth(genreCol);
		genreCol.setCellFactory(getCellFactory());
		
		// Adding the columns to the table
		this.getColumns().addAll(artistCol, albumCol, trackCol, genreCol);
		
		
		// Setting the table properties
		this.setColumnResizePolicy(CenterTable.CONSTRAINED_RESIZE_POLICY);
		this.setPlaceholder(new ImageView(PropertiesUtils.getProperty(IconProperties.TABLE_EMPTY_SHOW)));
		this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// Adding the event handlers
		this.setOnKeyPressed(new CenterTable_EventHandler_KeyBoard());
		CenterTable_EventHandler_DragNDrop dragAndDropHandler = new CenterTable_EventHandler_DragNDrop();
		this.setOnDragOver(dragAndDropHandler);
		this.setOnDragDropped(dragAndDropHandler);
		this.getSelectionModel().selectedItemProperty().addListener(new CenterTable_ChangeListener());
		
	}
	
	/**
	 * Method to set the default width of the delivered column
	 * 
	 * @param col the column for which the width is set
	 */
	private void setWidth(TableColumn<MusicTag, String> col) {
		col.setPrefWidth(Main_Frame.getPrefFrameWidth() / 8);
		col.setMinWidth(Main_Frame.getMinFrameWidth() / 16);
		col.setMaxWidth(Main_Frame.getPrefFrameWidth() / 8);
		col.setResizable(true);
	}

	/**
	 * Initializes and delivers an instance of the cell factory which is 
	 * used to style the cells 
	 * 
	 * @return instance of the cell table factory
	 */
	private Callback<TableColumn<MusicTag, String>, TableCell<MusicTag, String>> getCellFactory() {
		Callback<TableColumn<MusicTag, String>, TableCell<MusicTag, String>> cellFactory =
		        new Callback<TableColumn<MusicTag, String>, TableCell<MusicTag, String>>() {
		            
					public TableCell<MusicTag, String> call(TableColumn<MusicTag, String> p) {
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
		                            checkEntries();
		                        }
		                        
		                    }
		                    
		                    private void checkEntries() {
		                    	for (MusicTag tag : Main_Frame.getBorderPane_Center().getCentertable().getItems()) {
		                    		if (tag.getStatus() == TagState.MISSINGCRITICAL) {
		                    			inCompleteTags = true;
		                    			break;
		                    		} else {
		                    			inCompleteTags = false;
		                    		}
		                    	}
		                    }
		                };

		                return cell;
		            }
		        };
		
		
		return cellFactory;
	}
	
	/**
	 * Returns the boolean indicating whether the list holds entries with incomplete tags
	 * 
	 * @return boolean indicating whether the list holds entries with incomplete tags
	 */
	public boolean isInCompleteTags() {
		return inCompleteTags;
	}
}
