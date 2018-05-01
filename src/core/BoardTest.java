package core;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class BoardTest {

	@Test
	void testClone() {
		Board borad = new Board();
		Board clone = borad.clone();
		clone.setPiece(new Point(1,1), Stone.Black);
		assertEquals(Objects.deepEquals(borad.at(1, 1), clone.at(1, 1)), false);
	}

}
