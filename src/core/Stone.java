package core;

public enum Stone {
	None(" "), Black("●"), White("○"), Wall("#");

	private final String label;

	Stone(String label) {
		this.label = label;
	}

	public Stone flip() {
		if (this == Stone.Black) {
			return Stone.White;
		}
		if (this == Stone.White) {
			return Stone.Black;
		}
		throw new IllegalStateException(this.toString());
	}

	public static boolean isStone(Stone s) {
		return s == Stone.Black || s == Stone.White;
	}

	@Override
	public String toString() {
		return this.label;
	}
}