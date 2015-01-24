package org.usfirst.frc.team3316.robot.stacker;

public class GamePiece 
{
	private GamePieceType type;
	
	public GamePiece (GamePieceType type)
	{
		this.type = type;
	}
	
	public GamePieceType getType ()
	{
		return this.type;
	}
}
