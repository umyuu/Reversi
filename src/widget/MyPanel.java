package widget;

import java.awt.GridLayout;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JPanel;

import core.Board;
import core.BoardUtil;
import core.EventName;
import core.Game;
import core.Stone;

@SuppressWarnings("serial")
public class MyPanel extends JPanel implements PropertyChangeListener {
	private final JButton[][] b = new JButton[10][10];
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
	public MyPanel(Game game) {
		Board board = game.getBoard();
		setLayout(new GridLayout(8, 8));
		IntStream.rangeClosed(1, 8).forEach(i -> {
			IntStream.rangeClosed(1, 8).forEach(j -> {
				JButton button = new JButton(board.at(i, j).toString());
				button.setSize(80, 80);
				button.setActionCommand(Integer.toHexString(i) + "" + Integer.toHexString(j));
				button.addActionListener((e) -> {
					String str = e.getActionCommand();
					System.out.println(game);
					Point p = BoardUtil.getPoint(str);
					if (board.testPiece(p.x, p.y, game.getTurn()) == 0) {
						return;
					}
					game.setPiece(p, game.getTurn());
					game.trunFlip();
					if(!game.isAI()) {
						return;
					}
					PropertyChangeEvent evt = new PropertyChangeEvent(e.getSource(), EventName.DO_THINKING.name(), null, "2");
					changes.firePropertyChange(evt);
					//
				});
				b[i][j] = button;
				this.add(button);
			});

		});

		game.addPropertyChangeListener(this);
	}
	public void addBeanPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	public void removeBeanPropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt);
		if (!EventName.SET_PIECE.name().equals(evt.getPropertyName())) {
			return;
		}
		Object[] newValue = (Object[]) evt.getNewValue();
		Stone stone = (Stone) newValue[0];
		Point[] points = (Point[]) newValue[1];
		for (Point point : points) {
			b[point.x][point.y].setText(stone.toString());
		}
	}
}
