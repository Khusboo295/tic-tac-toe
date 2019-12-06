package com.nokia.ticktacktoe.vo;

import javax.validation.constraints.NotBlank;

/**
 * This VO is for receiving/returning MoveDetails from/to application
 */
public class MoveDetailsVO {

	@NotBlank(message = "row cannot be blank")
	private String row;

	@NotBlank(message = "column cannot be blank")
	private String column;

	public MoveDetailsVO(String row, String column) {
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
