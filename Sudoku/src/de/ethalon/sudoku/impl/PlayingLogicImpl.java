package de.ethalon.sudoku.impl;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import de.ethalon.sudoku.bean.PlayingField;

/**
 * <p>
 * Logic for the game
 * </p>
 * 
 * 
 * <br>
 * &nbsp;<br>
 * Licensed Materials - Property of <b>ETHALON GmbH</b> <br>
 * (c) Copyright ETHALON GmbH 2012. All rights reserved. <br>
 * Class PlayingLogicImpl.java created on 23.01.2012 14:23:13
 * 
 * <br>
 * &nbsp;<br>
 * <b>History:</b><br>
 * 
 * <br>
 * &nbsp;
 * 
 * @author ETHALON: tmy
 * 
 */

public class PlayingLogicImpl {

	/**
	 * 
	 * <p>
	 * Checks whether the playingField is completely filled.
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param rowsAndColumns
	 * @return true when the playing field is completely filled
	 */
	public Boolean checkForCompleteness(final Integer[][] rowsAndColumns) {

		for (final Integer[] rowsAndColumn : rowsAndColumns) {
			for (int col = 0; col < rowsAndColumns.length; col++) {
				if (rowsAndColumn[col] == null) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * <p>
	 * Checks whether the actual allocation is correct
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param _rowsAndColumns
	 * @param rowsAndColumns
	 * @return true, if it's correct
	 */
	public Boolean checkUserSolution(final Integer[][] _rowsAndColumns, final Integer[][] rowsAndColumns) {

		// just compare the solution, with solution by the user
		for (int row = 0; row < rowsAndColumns.length; row++) {
			for (int col = 0; col < rowsAndColumns.length; col++) {

				if ((rowsAndColumns[row][col] != null) && (rowsAndColumns[row][col] != _rowsAndColumns[row][col])) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * <p>
	 * Shows an additional number.
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param _rowsAndColumns
	 * @param rowsAndColumns
	 * @return new given Point (row/col)
	 */
	public Point giveUserHelpWithOneNumber(final Integer[][] _rowsAndColumns, final Integer[][] rowsAndColumns) {
		int randomRow = (int) (Math.random() * 9);
		int randomColumn = (int) (Math.random() * 9);

		while (rowsAndColumns[randomRow][randomColumn] != null) {
			randomRow = (int) (Math.random() * 9);
			randomColumn = (int) (Math.random() * 9);
		}

		final Point point = new Point(randomRow, randomColumn);

		rowsAndColumns[randomRow][randomColumn] = _rowsAndColumns[randomRow][randomColumn];

		return point;
	}

	/**
	 * 
	 * <p>
	 * inits the playingField with starting values
	 * </p>
	 * 
	 * @param playingField
	 * @return true when initiating the playing field was successful
	 */
	public Boolean initPlayingField(final PlayingField playingField) {

		final Integer[][] rowsAndColumns = playingField.getRowsAndColumns();

		int randomValue;

		for (int initCounter = 0; initCounter < rowsAndColumns.length; initCounter++) {
			randomValue = (int) (Math.random() * 9);

			// check whether the random field is null
			if (rowsAndColumns[initCounter][0] == null) {
				rowsAndColumns[initCounter][0] = randomValue + 1;

				if (!this.checkRow(new Point(initCounter, 0), rowsAndColumns)) {
					return false;
				}

				if (!this.checkColumn(new Point(initCounter, 0), rowsAndColumns)) {
					return false;
				}

				if (!this.checkSquare(new Point(initCounter, 0), rowsAndColumns)) {
					return false;
				}

			} else {
				// when the field is already set, get another random field
				initCounter--;
			}
		}

		// solve this sudoku to get all values
		this.solveSudoku(rowsAndColumns);

		int randomRow;
		int randomColumn;

		// clear some values (only show "playingField.getCountShownNumbers()" numbers)
		for (int clearCounter = 0; clearCounter < ((rowsAndColumns.length * rowsAndColumns.length) - playingField
				.getCountShownNumbers()); clearCounter++) {

			randomRow = (int) (Math.random() * 9);
			randomColumn = (int) (Math.random() * 9);

			if (rowsAndColumns[randomRow][randomColumn] != null) {
				rowsAndColumns[randomRow][randomColumn] = null;
			} else {
				clearCounter--;
			}
		}
		return true;

	}

	/**
	 * 
	 * <p>
	 * Solves the Sudoku (given rowsAndColumns [][])
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param rowsAndColumns
	 */
	public void solveSudoku(final Integer[][] rowsAndColumns) {

		final Point point = this.findNextFreePoint(rowsAndColumns);

		final Integer[][] _rowsAndColumns = new Integer[rowsAndColumns.length][rowsAndColumns.length];

		if (!this.checkForCompleteness(rowsAndColumns)) {

			for (int i = 1; i <= 9; i++) {

				// make a copy of the array
				for (int row = 0; row < rowsAndColumns.length; row++) {
					for (int col = 0; col < rowsAndColumns.length; col++) {
						_rowsAndColumns[row][col] = rowsAndColumns[row][col];
					}
				}

				_rowsAndColumns[point.x][point.y] = i;

				if (this.checkRow(point, _rowsAndColumns) && this.checkColumn(point, _rowsAndColumns)
						&& this.checkSquare(point, _rowsAndColumns)) {
					rowsAndColumns[point.x][point.y] = _rowsAndColumns[point.x][point.y];
					this.solveSudoku(rowsAndColumns);
				}
			}
			if (!this.checkForCompleteness(rowsAndColumns)) {
				rowsAndColumns[point.x][point.y] = null;
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Checks the column of a Point. (Doublettes)
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param point
	 * @param rowsAndColumns
	 * @return
	 */
	private Boolean checkColumn(final Point point, final Integer[][] rowsAndColumns) {

		final Set<Integer> columnNumbers = new HashSet<Integer>();

		for (int numbers = 0; numbers < rowsAndColumns.length; numbers++) {

			if (rowsAndColumns[point.x][numbers] != null) {
				if (!columnNumbers.add(rowsAndColumns[point.x][numbers])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * Checks the row of a Point. (Doublettes)
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param point
	 * @param rowsAndColumns
	 * @return
	 */
	private Boolean checkRow(final Point point, final Integer[][] rowsAndColumns) {

		final Set<Integer> rowNumbers = new HashSet<Integer>();

		for (int numbers = 0; numbers < rowsAndColumns.length; numbers++) {

			if (rowsAndColumns[numbers][point.y] != null) {
				if (!rowNumbers.add(rowsAndColumns[numbers][point.y])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * Checks the square of a Point. (Doublettes)
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param point
	 * @param rowsAndColumns
	 * @return
	 */
	private Boolean checkSquare(final Point point, final Integer[][] rowsAndColumns) {

		final Set<Integer> squareNumbers = new HashSet<Integer>();

		for (int row = (point.x - (point.x % 3)); row < ((point.x - (point.x % 3)) + 3); row++) {
			for (int col = (point.y - (point.y % 3)); col < ((point.y - (point.y % 3)) + 3); col++) {

				if (rowsAndColumns[row][col] != null) {
					if (!squareNumbers.add(rowsAndColumns[row][col])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * Finds the next free point in the playing field
	 * </p>
	 * 
	 * @author ETHALON: tmy
	 * 
	 * @param rowsAndColumns
	 * @return Point freePoint with x = row and y = col
	 */
	private Point findNextFreePoint(final Integer[][] rowsAndColumns) {

		Boolean isPointFree = false;
		final Point freePoint = new Point();

		for (int row = 0; row < rowsAndColumns.length; row++) {
			for (int col = 0; col < rowsAndColumns.length; col++) {
				if (rowsAndColumns[row][col] == null) {
					freePoint.setLocation(row, col);
					isPointFree = true;
					break;
				}
				if (isPointFree) {
					break;
				}
			}
		}

		return freePoint;
	}
}
