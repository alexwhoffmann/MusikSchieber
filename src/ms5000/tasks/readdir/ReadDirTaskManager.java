package ms5000.tasks.readdir;

import java.io.File;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import ms5000.gui.mainframe.Main_Frame;

public class ReadDirTaskManager {
	public enum TaskStatus {RUNNING,SCHEDULED,FAILED,SUCCEEDED};
	
	private static ReadDirTask task;
	private static ImportMode importMode;
	private static TaskStatus taskStatus;
	private static Thread taskThread;
	
	public static void startTask(String pathToDir, ImportMode mode) {
		importMode = mode;
		task = new ReadDirTask(pathToDir,mode);
		
		startTask(task);
	}
	
	public static void startTask(List<File> list, ImportMode mode) {
		importMode = mode;
		task = new ReadDirTask(list,mode);
		
		startTask(task);
	}
	
	public static void startTask(File file, ImportMode mode) {
		importMode = mode;
		task = new ReadDirTask(file,mode);
		
		startTask(task);
	}
	
	private static void startTask(ReadDirTask task) {
		Main_Frame.gethBox_Right().getBtnImportData().changeButtonIcon(TaskStatus.RUNNING);
		task.stateProperty().addListener(getTaskChangeListener());
		
		taskThread = new Thread(task);
		taskThread.start();
	}
	
	private static ChangeListener<Worker.State> getTaskChangeListener() {
		return new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState,
					Worker.State newState) {
				if (newState.toString().equals("SCHEDULED")) {
					taskStatus = TaskStatus.SCHEDULED;
				} else if (newState.toString().equals("SUCCEEDED")) {
					Main_Frame.gethBox_Right().getBtnImportData().changeButtonIcon(TaskStatus.SUCCEEDED);
					taskStatus = null;
				} else if (newState.toString().equals("RUNNING")) {
					taskStatus = TaskStatus.RUNNING;
				} else if (newState.toString().equals("FAILED")) {
					taskStatus = TaskStatus.FAILED;
				}
			}
		};
	}
	
	public static boolean cancelTask() {
		if (taskStatus == TaskStatus.RUNNING || taskStatus == TaskStatus.FAILED || taskStatus == TaskStatus.SCHEDULED) {
			task.cancel();
			taskStatus = null;
			
			if (taskThread.isAlive()) {
				taskThread.interrupt();
				task.cancel();
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public static void destroyTask() {
		task = null;
	}
 

	public static ImportMode getImportMode() {
		return importMode;
	}


	public static TaskStatus getTaskStatus() {
		return taskStatus;
	}


	public static ReadDirTask getTask() {
		return task;
	}
}
