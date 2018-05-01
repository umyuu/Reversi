package core;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import ai.LinerSearchAI;
import ai.Processor;
import ai.RandomSearchAI;

public class Game {

	private Stone turn = Stone.Black;
	private final Board board = new Board();
	private final Processor ai = new RandomSearchAI();
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private boolean isAI = true;
	private final LinkedHashMap<Stone, Integer> score = new LinkedHashMap<>();
	public LinkedHashMap<Stone, Integer> getScore() {
		return score;
	}

	// private final Processor ai = new LinerSearchAI();
	public Game() {
		score.put(Stone.Black, 2);
		score.put(Stone.White, 2);
		System.out.println(this);
	}

	public Stone getTurn() {
		return this.turn;
	}

	public Board getBoard() {
		return this.board;
	}
	public void setEnableAI(boolean b) {
		this.isAI = b;
	}
	public boolean isAI() {
		return isAI;
	}
	public Stone trunFlip() {
		this.turn = this.turn.flip();
		return this.turn;
	}
	public void setPiece(Message m) {
		this.setPiece(m.getPoint().orElseThrow(null), m.getStone());
	}
	public void setPiece(Point p, Stone s) {
		List<Point> l = this.board.setPiece(p, s);
		// l.size() == 4の時、置いた駒:1, 取った駒:3
		score.merge(s, l.size(), Integer::sum);
		score.merge(s.flip(), (l.size() -1) * -1, Integer::sum);
		this.changes.firePropertyChange(EventName.SET_PIECE.name(), null,
				new Object[] { s, l.toArray(new Point[0]) });
	}
	public Message doThinking() {
		return ai.doThinking(board, this.turn);
	}
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}
	
	@Override
	public String toString() {
		return String.join("!", this.turn.toString(), this.ai.toString(), board.toString());
	}
}
