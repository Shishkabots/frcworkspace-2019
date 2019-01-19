/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
This is the Robot.java after William deleted almost everything and basically only left the drivetrain functioning
My java thing isn't working still so im not sure if there are errors in the code due to deleted things
victor and talon on each side encoder is on talon
victor follows talon
canbus instead of pwm
set id for talon
ctre removed library
id on talon and victor
*/

package org.usfirst.frc.team6822.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.usfirst.frc.team6822.robot.commands.*;
import org.usfirst.frc.team6822.robot.subsystems.*;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */

public class Robot extends TimedRobot 
{
    public static OI m_oi;
	//public static Intake m_intake;
	//public static LinearSlide m_linearslide;
	public static DriveTrain m_drivetrain;

	//public static Claws m_claws;
    //public static Platform m_platform;
    //public static Gripper m_gripper;
	Command m_onlyCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	WPI_TalonSRX leftDrive = RobotMap.leftDrive;
	WPI_TalonSRX rightDrive = RobotMap.rightDrive;
	WPI_VictorSPX leftSlave = RobotMap.leftSlave;
	WPI_VictorSPX rightSlave = RobotMap.rightSlave;

	@Override
	public void robotInit() {
        RobotMap.init();
        
		UsbCamera theCamera = CameraServer.getInstance().startAutomaticCapture();
		theCamera.setVideoMode(theCamera.enumerateVideoModes()[101]);

		m_oi = new OI();
		//m_intake = new Intake();
		//m_linearslide = new LinearSlide();
		
        //m_claws = new Claws();
        //m_platform = new Platform();
		//m_gripper = new Gripper();

		//idk what this is but it was in the example so...
		leftDrive.setInverted(true);
		rightDrive.setInverted(true);

		leftSlave.setInverted(true);
		rightSlave.setInverted(true);

		m_drivetrain = new DriveTrain();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * Actually just don't call autonomous 
	 * Ignore my rart changes
	 */
	
	//double[][] loadedAction = new double[1000][8];
	boolean boi = false;
	@Override
	public void autonomousInit() {
		//im trying something in case autonomous is still called
		//m_onlyCommand = new TeleOpCommands().start();
		boi = true;

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	
	//int positionAtRecording = 0;
	
	@Override
	public void autonomousPeriodic() 
	{
		//I dunno what to put here
	}

	@Override
	public void teleopInit() {
		//ignore the boi thing for now
		//probably not important
		
		if(boi) {
			//m_onlyCommand().stop();
		}
		new TeleOpCommands().start();
	}

	/**
	 * This function is called periodically during operator control.
	 * I don't have any idea how it works
	 * Just keep as is probably
	 */
	
	long timeAtZero = 0;
	boolean isRecording = false;
	boolean hasRecorded = false;
	PrintWriter pW ;
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		if(Robot.m_oi.joystick.getRawButton(3) && Robot.m_oi.joystick.getRawButton(4))
		{
			if(!isRecording && !hasRecorded)
			{
			timeAtZero = System.currentTimeMillis();
			isRecording = true;
			
				try {
					pW = new PrintWriter(new FileWriter("/home/lvuser/recordedy.0"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(isRecording && (System.currentTimeMillis() - timeAtZero > 1000))
			{
				isRecording = false;
				hasRecorded = true;
				pW.flush();
				pW.close();
			}
		}
		if(isRecording)
		{
			pW.println(
                (System.currentTimeMillis() - timeAtZero) + ","
		        + (Robot.m_oi.xbox.getRawAxis(Robot.m_oi.rTriggerAxis) - Robot.m_oi.xbox.getRawAxis(Robot.m_oi.lTriggerAxis)) + ","
		        + Robot.m_oi.xbox.getRawAxis(Robot.m_oi.turnAxis) + ","
                + (
                    (Robot.m_oi.joystick.getRawAxis(Robot.m_oi.slideAxis)
                    + Robot.m_oi.tensionSlide
                    - 0.2 * Robot.m_oi.joystick.getRawAxis(Robot.m_oi.otherSlideAxis)
                ) * Robot.m_oi.throttleSlide) + ","
            + leftDrive.getMotorOutputPercent() + ", " + rightDrive.getMotorOutputPercent());

			System.out.println((System.currentTimeMillis() - timeAtZero) + " " + 1000 * 15);
		}
	}

	@Override
	public void testInit(){
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}