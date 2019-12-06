package com.nokia.ticktacktoe.domain;

import javax.persistence.*;

/**
 * Entity class for TblGamerDetails
 */
@Entity
@Table(name = "TBL_GAME_DETAILS")
public class TblGamerDetails {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "GAMER_NAME")
	private String gamerName;

	@Column(name = "GAMER_CHARACTER")
	private String gamerCharacter;

	@Column(name = "GAME_STATE")
	private String gameState;

	@Column(name = "GAMER_STATUS")
	private String gameStatus;

	public TblGamerDetails() {
		super();
	}

	public TblGamerDetails(Long id, String gamerName, String gamerCharacter, String gameState, String gameStatus) {
		super();
		this.id = id;
		this.gamerName = gamerName;
		this.gamerCharacter = gamerCharacter;
		this.gameState = gameState;
		this.gameStatus = gameStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGamerName() {
		return gamerName;
	}

	public void setGamerName(String gamerName) {
		this.gamerName = gamerName;
	}

	public String getGamerCharacter() {
		return gamerCharacter;
	}

	public void setGamerCharacter(String gamerCharacter) {
		this.gamerCharacter = gamerCharacter;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	@Override
	public String toString() {
		return "TblGamerDetails [id=" + id + ", gamerName=" + gamerName + ", gamerCharacter=" + gamerCharacter
				+ ", gameState=" + gameState + ", gameStatus=" + gameStatus + "]";
	}

}
