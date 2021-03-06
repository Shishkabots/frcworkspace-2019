package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import org.opencv.core.Point;

public class FollowImage extends Command {
    public FollowImage() {
        requires(Robot.m_camgrill);
        requires(Robot.m_drivetrain);
    }

            // Called just before this Command runs the first time
    public static double d;
    public static double eq;
    public static Point p1;
    public static Point p2;
    private final Object imgLock = Robot.m_camgrill.imgLock;
    @Override
    protected void initialize() {
        Robot.m_drivetrain.move(0, 0);
        d = Robot.m_camgrill.m_centerX;
        p1 = new Point(Robot.m_camgrill.r.x,Robot.m_camgrill.r.y);
        p2 = new Point(Robot.m_camgrill.r.x,Robot.m_camgrill.r.y + Robot.m_camgrill.r.height);
        eq = (p2.y-p1.y)/(p2.x-p1.x);
        
    }
        
    // Called repeatedly when this Command is scheduled to run
    
    @Override
    protected void execute() {
        
        while(d != 160) {
            synchronized(imgLock) { 
                d = Robot.m_camgrill.m_centerX;
            }
            double turn = d - 160;
            Robot.m_drivetrain.moveWithCurve(-6, turn*0.005, false);
        }
        while(eq != 0) {
            synchronized(imgLock) {
                p1 = new Point(Robot.m_camgrill.r.x,Robot.m_camgrill.r.y);
                p2 = new Point(Robot.m_camgrill.r.x,Robot.m_camgrill.r.y + Robot.m_camgrill.r.height);
                eq = (p2.y-p1.y)/(p2.x-p1.x);
                Robot.m_drivetrain.moveWithCurve(0.5,eq*0.005,true);
            }
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_drivetrain.move(0,0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	Robot.m_hatch.setState("Off");
    }
}