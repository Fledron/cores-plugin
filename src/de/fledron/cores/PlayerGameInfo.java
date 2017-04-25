package de.fledron.cores;

public class PlayerGameInfo {
	
	String team;
	int gameID;
	
	public PlayerGameInfo(String team, int gameID){
		this.team = team;
		this.gameID = gameID;
	}
	
	public String team(){
		return team;
	}
	
	public int id(){
		return gameID;
	}

}
