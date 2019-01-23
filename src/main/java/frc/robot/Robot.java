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

package frc.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.vision.VisionRunner;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
	
	public static DriveTrain m_drivetrain;
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;

	
	Command m_onlyCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public static WPI_TalonSRX leftDrive;
	public static WPI_TalonSRX rightDrive;
	public static WPI_VictorSPX leftSlave;
	public static WPI_VictorSPX rightSlave;
	public static IntakeSucc intakesucc;

	private VisionThread visionThread;
	private double centerX = 0.0;
	private final Object imgLock = new Object();
	public static TeleOpCommands tele;

	public static Hatch m_hatch;
	//public static Button m_hatchbutt;
	@Override
	public void robotInit() {
		RobotMap.init();
		leftDrive = RobotMap.leftDrive;
		rightDrive = RobotMap.rightDrive;
		leftSlave = RobotMap.leftSlave;
		rightSlave = RobotMap.rightSlave;
		

		UsbCamera theCamera = CameraServer.getInstance().startAutomaticCapture();
		//theCamera.setVideoMode(theCamera.enumerateVideoModes()[101]);
		theCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    
    	visionThread = new VisionThread(theCamera, new GripPipeline(), pipeline -> {
        if (!pipeline.filterContoursOutput().isEmpty()) {
             Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
             synchronized (imgLock) {
		 		centerX = r.x + (r.width / 2);
		 		System.out.println(centerX);
		 	}
         }	
    	 });
    	 visionThread.start();
		

		m_hatch = new Hatch();
		m_oi = new OI();
		intakesucc = new IntakeSucc();
		//m_hatchbutt = m_oi.hatchbutt;
		
		//idk what this is but it was in the example so...
		
		leftDrive.setInverted(true);
		rightDrive.setInverted(true);

		leftSlave.setInverted(true);
		rightSlave.setInverted(true);

		m_drivetrain = new DriveTrain();
			
		//code to test motors
		leftSlave.set(ControlMode.PercentOutput, 50);
		
		//leftDrive.set(ControlMode.PercentOutput, 50);

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
	
	@Override
	public void autonomousInit() {
		tele = new TeleOpCommands();
		tele.start();

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

		//dunno if this works or if it matters

		// end conditionally?
		//tele.end();
		tele = new TeleOpCommands();
		tele.start();
		

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
            );

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