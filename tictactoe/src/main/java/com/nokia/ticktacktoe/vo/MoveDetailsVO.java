package com.nokia.ticktacktoe.vo;

import javax.validation.constraints.NotNull;

/**
 * This VO is for receiving/returning MoveDetails from/to application
 */
public class MoveDetailsVO {

	@NotNull(message = "row cannot be null")
	private String row;

	@NotNull(message = "column cannot be null")
	private String column;

	public MoveDetailsVO(@NotNull String row, @NotNull String column) {
		super();
		this.row = row;
		this.column = column;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return "MoveDetailsDTO [row=" + row + ", column=" + column + "]";
	}
}
