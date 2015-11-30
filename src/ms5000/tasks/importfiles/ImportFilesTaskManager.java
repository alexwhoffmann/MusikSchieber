package ms5000.tasks.importfiles;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;

public class ImportFilesTaskManager {
	public enum TaskStatus {RUNNING,SCHEDULED,FAILED,SUCCEEDED};
	
	private static ImportFilesTask task;
	private static TaskStatus taskStatus;
	private static Thread taskThread;
	
	public static void startTask() {
		task = new ImportFilesTask();
		
		startTask(task);
	}
	
	private static void startTask(ImportFilesTask task) {
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
 

	public static TaskStatus getTaskStatus() {
		return taskStatus;
	}


	public static ImportFilesTask getTask() {
		return task;
	}
}
