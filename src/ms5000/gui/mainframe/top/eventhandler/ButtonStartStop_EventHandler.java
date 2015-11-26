package ms5000.gui.mainframe.top.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ms5000.audio.player.AudioPlayer;
import ms5000.audio.player.PlayerStatus;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.top.buttons.Button_Start;

public class ButtonStartStop_EventHandler implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() instanceof Button_Start) {
			Main_Frame.gethBox_Left().getBtnStart().changeButtonIcon(false);

			if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PAUSED
					|| AudioPlayer.getInstance().getStatus() == PlayerStatus.READY) {
				AudioPlayer.getInstance().play();

			} else if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PLAYING) {
				Main_Frame.gethBox_Left().getBtnStart().changeButtonIcon(false);
				AudioPlayer.getInstance().pause();
			}

		} else {
			if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PLAYING
					|| AudioPlayer.getInstance().getStatus() == PlayerStatus.PAUSED) {
				AudioPlayer.getInstance().setStatus(PlayerStatus.READY);
				AudioPlayer.getInstance().stop();
				Main_Frame.gethBox_Left().getBtnStart().changeButtonIcon(true);
			}
		}
	}
}
