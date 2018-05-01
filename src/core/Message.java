package core;

import java.awt.Point;
import java.util.Optional;

public class Message {
	private final Stone s;
	private final Optional<Point> p;

	public Message(Stone s, Optional<Point> p) {
		this.s = s;
		this.p = p;
	}

	public Stone getStone() {
		return this.s;
	}

	public Optional<Point> getPoint() {
		return this.p;
	}

	@Override
	public String toString() {
		return String.join("_", s.toString(), p.toString());
	}
}
