/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6822.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {
	
	//motor controllers
	public static WPI_TalonSRX leftDrive;
	public static WPI_TalonSRX rightDrive;

	public static WPI_VictorSPX leftSlave;
	public static WPI_VictorSPX rightSlave;
	
	//differential drive
	public static DifferentialDrive diffdrive;

	public static DoubleSolenoid Piston1;
	public static DoubleSolenoid Piston2;
	public static DoubleSolenoid Piston3;


	public static void init()
	{

		//new motors, talon
		leftDrive = new WPI_TalonSRX(3);
  		rightDrive = new WPI_TalonSRX(4);
		diffdrive = new DifferentialDrive(leftDrive,rightDrive);
		leftSlave = new WPI_VictorSPX(1);
		rightSlave = new WPI_VictorSPX(2);

		Piston1 = new DoubleSolenoid(7, 8);
		Piston2 = new DoubleSolenoid(9,10);
		Piston3 = new DoubleSolenoid(11,12);

		
		diffdrive.setSafetyEnabled(false);
		
	}
	
	
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}