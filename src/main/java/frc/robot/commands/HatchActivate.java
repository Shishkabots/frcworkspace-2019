package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class HatchActivate extends Command {
    public HatchActivate() {
        requires(Robot.m_hatch);
    }

            // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.m_hatch.setState("Off");
        Robot.m_hatch.setState("Open");
    
    }
        
    // Called repeatedly when this Command is scheduled to run
    public int geytime;
    @Override
    protected void execute() {

            
            geytime++;
            

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return geytime >= 10000;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_hatch.setState("Close");
    	Robot.m_hatch.setState("Off");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	Robot.m_hatch.setState("Off");
    }
}