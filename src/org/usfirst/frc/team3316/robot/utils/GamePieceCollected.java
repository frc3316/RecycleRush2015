package org.usfirst.frc.team3316.robot.utils;

public enum GamePieceCollected 
{
	/*
	 * Ordered from closest to furthest:
	 
	 * Tote - a tote is in the gripper
	 * Container - a container is in the gripper
	 * Something - something is in the gripper, not sure what
	 * Unsure - unsure if there's something in the gripper
	 * None - the gripper is empty
	 */
	 
	Tote, Container, Something, Unsure, None;
}
