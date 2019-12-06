package com.nokia.ticktacktoe.enums;

/**
 * Enums for valid game characters
 */
public enum CharacterEnums {

	X("x"), O("o");
	private String value;

	CharacterEnums(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
