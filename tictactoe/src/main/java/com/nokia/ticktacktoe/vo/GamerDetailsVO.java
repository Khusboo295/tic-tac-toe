package com.nokia.ticktacktoe.vo;

import javax.validation.constraints.NotBlank;
import com.nokia.ticktacktoe.validators.IsValidCharacters;

/**
 * This VO is for receiving/returning GamerDetails from/to application
 */
public class GamerDetailsVO {

	@NotBlank(message = "name cannot be blank!")
	private String name;

	@NotBlank(message = "character cannot be blank!")
	@IsValidCharacters
	private String character;

	public GamerDetailsVO(String name, @IsValidCharacters String character) {
		super();
		this.name = name;
		this.character = character;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	@Override
	public String toString() {
		return "GamerDetailsVO [name=" + name + ", character=" + character + "]";
	}

}
