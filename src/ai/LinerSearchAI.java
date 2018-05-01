package ai;
import java.awt.Point;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import core.*;

public class LinerSearchAI extends Processor {

	@Override
	public Optional<Point> nextStone(Board board, Stone stone, Duration thinkingTime) {
		board = board.clone();
		// 左上→右下に操作して、空きマスを探す。
		LinkedHashMap<Integer,Integer> positions = board.clone().HitSearch(stone);
		if (positions.isEmpty()) {
			return Optional.ofNullable(null);
		}
		for(Entry<Integer, Integer> entry: positions.entrySet()) {
			return Optional.ofNullable(BoardUtil.getPoint(entry.getKey()));
		}
		return Optional.ofNullable(null);
	}
}
