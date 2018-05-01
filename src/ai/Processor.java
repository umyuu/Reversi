package ai;

import java.awt.Point;
import java.time.Duration;
import java.util.Optional;

import core.*;

public abstract class Processor {
	public abstract Optional<Point> nextStone(Board board, Stone stone, Duration thinkingTime);

	private static final Duration INFINITE = Duration.ZERO;
	private final Stone turn = Stone.White;
	public String getName() {
		return getClass().getName();
	}

	public Message doThinking(Board board, Stone stone) {
		return new Message(stone, this.nextStone(board, stone, Processor.INFINITE));
	}

}
