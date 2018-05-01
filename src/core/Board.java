package core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Board implements Cloneable {
	public static final int ROWS = 10;// 番兵分マスを増やす
	public static final int COLUMNS = 10;
	public static final List<Point> DIRECTIONS = Collections
			.unmodifiableList(Arrays.asList(new Point(-1, -1), new Point(-1, 0), new Point(-1, 1), new Point(0, -1),
					new Point(0, 1), new Point(1, -1), new Point(1, 0), new Point(1, 1)));
	public Stone[][] board = new Stone[ROWS][COLUMNS];

	Board() {
		for (Stone[] row : board) {
			Arrays.fill(row, Stone.None);
		}
		// 壁マス
		IntStream.of(0, ROWS - 1).forEach(row -> {
			Arrays.fill(board[row], Stone.Wall);
		});
		IntStream.range(1, ROWS).forEach(row -> {
			IntStream.of(0, COLUMNS - 1).forEach(col -> {
				board[row][col] = Stone.Wall;
			});
		});

		board[4][4] = Stone.White;
		board[5][5] = Stone.White;
		board[4][5] = Stone.Black;
		board[5][4] = Stone.Black;
	}

	@Override
	public Board clone() {
		Board clone = new Board();
		// deep copy
		Stone[][] copyOf = new Stone[board.length][];
		for (int i = 0; i < board.length; i++) {
			copyOf[i] = Arrays.copyOf(board[i], board[i].length);
		}
		clone.board = copyOf;
		return clone;
	}

	public Stone at(int row, int col) {
		return board[row][col];
	}

	public int testPiece(int x, int y, Stone stone) {
		// @return boolean true:駒が置ける, false:駒が置けない
		// 駒が設置済み
		if (at(x, y) != Stone.None) {
			return 0;
		}
		// 周りの8マスに対して駒を置けるかどうかのチェックを行う。
		return DIRECTIONS.stream().mapToInt(vec -> canPutDown(stone, x, y, vec)).sum();
	}

	public List<Point> setPiece(Point p, Stone s) {
		board[p.x][p.y] = s;
		return flip(p, s);
	}

	private int canPutDown(final Stone stone, int x, int y, Point vec) {
		// ------------>>
		// ●# -------> 0
		// ●○● -------> 1
		// ●● -------> 0
		int flip_count = 0;
		while (true) {
			x += vec.x;
			y += vec.y;
			Stone check = at(x, y);
			if (!Stone.isStone(check)) {
				// 駒を挟んでいないので0を返す。
				return 0;
			}
			if (check != stone) {
				flip_count++;
			}
			if (check == stone) {
				return flip_count;
			}
		}
	}

	private List<Point> flip(final Point point, final Stone stone) {
		List<Point> l = new ArrayList<>();
		l.add(point);
		DIRECTIONS.stream().filter(vec -> canPutDown(stone, point.x, point.y, vec) > 0).forEach(vec -> {
			int x = point.x + vec.x;
			int y = point.y + vec.y;
			do {
				// 駒を裏返す
				board[x][y] = board[x][y].flip();
				l.add(new Point(x, y));
				x += vec.x;
				y += vec.y;
			} while (at(x, y) != stone);
		});
		return l;
	}

	public LinkedHashMap<Integer, Integer> HitSearch(Stone stone) {
		LinkedHashMap<Integer, Integer> positions = new LinkedHashMap<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				int value = this.testPiece(i, j, stone);
				if (value > 0) {
					positions.put(i * 10 + j, value);
				}
			}
		}
		return positions;
	}

	@Override
	public String toString() {
		return Arrays.deepToString(board);
	}
}
