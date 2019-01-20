package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.IntakeSucc;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class Succ extends Command {
    public Succ() {
        requires(Robot.intakesucc);
    }
    IntakeSucc succ = new IntakeSucc();
            // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        succ.Succ("ON");
    
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
        succ.Succ("OFF");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        succ.Succ("OFF");
    }
}