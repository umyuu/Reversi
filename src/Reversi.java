import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;

import ai.LinerSearchAI;
import core.EventName;
import core.Game;
import core.Message;
import core.Stone;
import widget.MyPanel;
import widget.Nav;

public class Reversi implements java.beans.PropertyChangeListener{
	private final JFrame frame = new JFrame("Reversi");
	private final Game game = new Game();
	private final ExecutorService es = Executors.newCachedThreadPool();
	private final MyPanel panel;
	private final Nav nav;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Reversi reversi = new Reversi();
			reversi.addEventListener();
			reversi.setVisible(true);
		});
	}

	public Reversi() {
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 400);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		panel = new MyPanel(game);
		nav = new Nav(game);
		frame.add(panel);
		frame.add(nav);
	}
	void addEventListener() {
		panel.addBeanPropertyChangeListener(this);
		nav.addBeanPropertyChangeListener(this);
	}
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(game);
		if(EventName.DO_THINKING.name().equals(evt.getPropertyName())) {
			
			CompletableFuture<Message> cf = CompletableFuture.supplyAsync(game::doThinking, es);
			cf.whenComplete((ret, ex) -> {
				if (ex == null) {
					Message m = ret;
					if(m.getPoint().isPresent()) {
						game.setPiece(ret);
					}
					game.trunFlip();
					// 成功した場合
					System.out.println("result=" + ret);

				} else {
					// 失敗した場合
					System.err.println("err=" + ex);
				}
			});
			return;
		}
		if(EventName.PASS.name().equals(evt.getPropertyName())) {
			game.trunFlip();
			return;
		}
		if(EventName.AI.name().equals(evt.getPropertyName())) {
			game.setEnableAI(!game.isAI());
			return;
		}
	}
}