package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class FollowImage extends Command {
    public FollowImage() {
        requires(Robot.m_camgrill);
        requires(Robot.m_drivetrain);
    }

            // Called just before this Command runs the first time
    public static double d;
    @Override
    protected void initialize() {
        Robot.m_drivetrain.move(0, 0);
        d = Robot.m_camgrill.m_centerX;
    }
        
    // Called repeatedly when this Command is scheduled to run
    
    @Override
    protected void execute() {
        d = Robot.m_camgrill.m_centerX;
        double turn = d - 160;
        Robot.m_drivetrain.moveWithCurve(-6, turn*0.005, false);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return d == 160;
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