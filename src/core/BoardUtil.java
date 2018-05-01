package core;

import java.awt.Point;
import java.util.HashMap;

public class BoardUtil {

	public static Point getPoint(String xy) {
		return getPoint(Integer.parseInt(xy));
	}
	public static Point getPoint(int xy) {
		int x = xy / 10;
		int y = xy % 10;
		return new Point(x, y);
	}
}
