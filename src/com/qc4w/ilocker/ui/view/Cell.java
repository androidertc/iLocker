package com.qc4w.ilocker.ui.view;


public class Cell {
	int row;
	int column;

	// keep # objects limited to 9
	static Cell[][] sCells = new Cell[3][3];
	static {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sCells[i][j] = new Cell(i, j);
			}
		}
	}

	/**
	 * @param row
	 *            The row of the cell.
	 * @param column
	 *            The column of the cell.
	 */
	private Cell(int row, int column) {
		checkRange(row, column);
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	/**
	 * @param row
	 *            The row of the cell.
	 * @param column
	 *            The column of the cell.
	 */
	public static synchronized Cell of(int row, int column) {
		checkRange(row, column);
		return sCells[row][column];
	}

	private static void checkRange(int row, int column) {
		if (row < 0 || row > 2) {
			throw new IllegalArgumentException("row must be in range 0-2");
		}
		if (column < 0 || column > 2) {
			throw new IllegalArgumentException(
					"column must be in range 0-2");
		}
	}
	
	public int getNum() {
		return row * 3 + column;
	}

	public String toString() {
		return "(row=" + row + ",clmn=" + column + ")";
	}
}