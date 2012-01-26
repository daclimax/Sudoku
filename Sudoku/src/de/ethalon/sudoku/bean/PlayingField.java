package de.ethalon.sudoku.bean;

/**
 * <p>
 * Bean for the playing field
 * </p>
 * 
 * 
 * <br>
 * &nbsp;<br>
 * Licensed Materials - Property of <b>ETHALON GmbH</b> <br>
 * (c) Copyright ETHALON GmbH 2012. All rights reserved. <br>
 * Class PlayingField.java created on 23.01.2012 13:39:27
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

public class PlayingField {

	// count shown numbers at the beginning
	Integer countShownNumbers;

	// bi-dimensional-array
	// first dimension = row [x] []
	// second dimension = column [] [x]
	Integer[][] rowsAndColumns;

	/**
	 * 
	 * <p>
	 * Constructor
	 * </p>
	 * 
	 * @param countShownNumbers
	 */
	public PlayingField(final Integer countShownNumbers) {

		// Playing Field is always 9x9
		this.rowsAndColumns = new Integer[9][9];
		this.countShownNumbers = countShownNumbers;
	}

	/**
	 * Returns the countShownNumbers.
	 * 
	 * @return Returns the countShownNumbers.
	 */
	public Integer getCountShownNumbers() {
		return this.countShownNumbers;
	}

	/**
	 * Returns the rowsAndColumns.
	 * 
	 * @return Returns the rowsAndColumns.
	 */
	public Integer[][] getRowsAndColumns() {
		return this.rowsAndColumns;
	}

	/**
	 * Sets the countShownNumbers field with given countShownNumbers.
	 * 
	 * @param countShownNumbers
	 *            The countShownNumbers to set.
	 */
	public void setCountShownNumbers(final Integer countShownNumbers) {
		this.countShownNumbers = countShownNumbers;
	}

	/**
	 * Sets the rowsAndColumns field with given rowsAndColumns.
	 * 
	 * @param rowsAndColumns
	 *            The rowsAndColumns to set.
	 */
	public void setRowsAndColumns(final Integer[][] rowsAndColumns) {
		this.rowsAndColumns = rowsAndColumns;
	}
}
