package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.GripPipeline;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.vision.VisionRunner;
import edu.wpi.first.vision.VisionThread;

/**
 *
 */
public class Camera extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private VisionThread visionThread;
    private final Object imgLock = new Object();
    public  double m_centerX = 0.0;
    public Rect r;
	public Camera()
	{
        UsbCamera theCamera = CameraServer.getInstance().startAutomaticCapture();
		//theCamera.setVideoMode(theCamera.enumerateVideoModes()[101]);
		theCamera.setResolution(320, 240);
    
    	visionThread = new VisionThread(theCamera, new GripPipeline(), pipeline -> {
        if (!pipeline.filterContoursOutput().isEmpty()) {
             r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
             synchronized (imgLock) {
				 m_centerX = r.x + (r.width / 2);
				 
		 		System.out.println("CAMERA VALUE" + m_centerX);
		 	}
         }
		 });
		 
    	 visionThread.start();
    }

	
    public void initDefaultCommand() {
       
    }
    public double getX() {
        return m_centerX;
    }
    
	
}
