package ai;

import java.awt.Point;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import core.Board;
import core.BoardUtil;
import core.Stone;

public class RandomSearchAI extends Processor {
	private final Random rnd = new SecureRandom();

	@Override
	public Optional<Point> nextStone(Board board, Stone stone, Duration thinkingTime) {
		board = board.clone();
		// 駒を置ける位置からランダムを選択
		LinkedHashMap<Integer, Integer> positions = board.HitSearch(stone);
		if (positions.isEmpty()) {
			return Optional.ofNullable(null);
		}
		List<Integer> source = new ArrayList<>(positions.keySet());
		System.out.println(positions.entrySet());
		Collections.shuffle(source, rnd);
		System.out.println(source);
		return Optional.ofNullable(BoardUtil.getPoint(source.get(0)));
	}
}
