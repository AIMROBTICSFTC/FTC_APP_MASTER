package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;// not use with digital touch

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is Team 9997, "FLOW".
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "ld"
 * Motor channel:  Right drive motor:        "rd"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 * I2C channel 1:  Rev Color Sensor:          "color"
 * Digital channel 1:  REV Touch Sensor:      "digin"
 *
 *
 */
public class Team9997Hardware
{
    /* Public OpMode members. */
    public DcMotor  leftMotor   = null;
    public DcMotor  rightMotor  = null;
    //public Servo    arm1         = null;
    public ColorSensor    color_sensor;
    public DigitalChannel digIn;

    // Initalize start values and range settings
    public final double ARM_HOME = 0.2;
    public final double ARM_MIN_RANGE  = 0.20;
    public final double ARM_MAX_RANGE  = 0.50;
    public final double CLAW_HOME = 0.2;
    public final double CLAW_MIN_RANGE  = 0.20;
    public final double CLAW_MAX_RANGE  = 0.7;
    public final double ARM_SPEED       = 0.1 ;     // sets rate to move servo
    public final double     FORWARD_SPEED = 1.0;
    public final double     TURN_SPEED    = 1.0;
    public double armPosition  =    ARM_HOME;     // Servo home (safe) position
    public boolean inputPin1;                       // Digital input pin set for "true" or "false"
    private final double     COUNTS_PER_MOTOR_REV    = 2000 ;    // eg: TETRIX Motor Encoder
    private final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    private final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    public final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                        (WHEEL_DIAMETER_INCHES * 3.1415);


    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Team9997Hardware() {
    }

    /* Initialize standard Hardware interfaces */


    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor   = hwMap.dcMotor.get("ld");
        rightMotor  = hwMap.dcMotor.get("rd");
        leftMotor.setDirection(DcMotor.Direction.REVERSE); //commented out for encoder

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Reset encoders
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Define and initialize ALL installed servos.
      //  arm1 = hwMap.servo.get("arm1");
       // arm1.setPosition(ARM_HOME);


        //       claw.setPosition(CLAW_HOME);

        // Define and initialize ALL installed sensors.

        color_sensor = hwMap.colorSensor.get("color"); // uncomment to use color sensor,add too config
        digIn  = hwMap.digitalChannel.get("digin");     //  Use generic form of device mapping

    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
