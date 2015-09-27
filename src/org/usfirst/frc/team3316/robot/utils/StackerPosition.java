package org.usfirst.frc.team3316.robot.utils;

public enum StackerPosition
{
	Tote, Step, Floor, Unknown;
	
	public String toString ()
	{
		if (this == Tote) return "Tote";
		if (this == Step) return "Step";
		if (this == Floor) return "Step";
		
		else return "Unknown";
	}
}
