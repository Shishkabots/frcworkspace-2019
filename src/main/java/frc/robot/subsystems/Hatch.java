package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 */
public class Hatch extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public DoubleSolenoid Piston1 = RobotMap.Piston1;
    public DoubleSolenoid Piston2 = RobotMap.Piston2;
    public DoubleSolenoid Piston3 = RobotMap.Piston3;


    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void setState(String state){
        if(state == "Off"){
            Piston1.set(DoubleSolenoid.Value.kOff);
            Piston2.set(DoubleSolenoid.Value.kOff);
            Piston3.set(DoubleSolenoid.Value.kOff);
        }
        else if(state == "Close"){
            Piston1.set(DoubleSolenoid.Value.kForward);
            Piston2.set(DoubleSolenoid.Value.kForward);
            Piston3.set(DoubleSolenoid.Value.kForward);
        }
        else if(state == "Open"){
            Piston1.set(DoubleSolenoid.Value.kReverse);
            Piston2.set(DoubleSolenoid.Value.kReverse);
            Piston3.set(DoubleSolenoid.Value.kReverse);
        }
    }
}