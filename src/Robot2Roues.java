import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class Robot2Roues {

	private EV3LargeRegulatedMotor roueGauche;
	private EV3LargeRegulatedMotor roueDroite;
	private static double Lumiere;
	private static double Correction;
	private static double VitesseB;
	private static double VitesseC;
	private static double maxSpeed;

	/**
	 * 
	 * @param portRoueGauche
	 *            MotorPort.(Lettre du port)
	 * @param portRoueDroite
	 *            MotorPort.(Lettre du port)
	 */
	public Robot2Roues(Port portRoueGauche, Port portRoueDroite) {
		roueGauche = new EV3LargeRegulatedMotor(portRoueGauche);
		roueDroite = new EV3LargeRegulatedMotor(portRoueDroite);
	}

	/*
	 * public static EV3LargeRegulatedMotor MotorA = new
	 * EV3LargeRegulatedMotor(MotorPort.A); public static EV3LargeRegulatedMotor
	 * roueDroite = new EV3LargeRegulatedMotor(MotorPort.B); public static
	 * EV3LargeRegulatedMotor roueGauche = new
	 * EV3LargeRegulatedMotor(MotorPort.C); public static EV3LargeRegulatedMotor
	 * MotorD = new EV3LargeRegulatedMotor(MotorPort.D);
	 */

	public int NumberClarck;

	public void rotateWaitAColour(int color, int time, int vitesse) {

		EV3LargeRegulatedMotor[] syncList = new EV3LargeRegulatedMotor[1];
		syncList[0] = roueDroite;

		roueGauche.synchronizeWith(syncList);

		roueGauche.setSpeed(1000);
		roueDroite.setSpeed(1000);
		roueGauche.startSynchronization();
		roueDroite.forward();
		roueGauche.forward();
		roueGauche.endSynchronization();

		Delay.msDelay(1000);

		roueGauche.startSynchronization();
		roueDroite.stop();
		roueGauche.stop();
		roueGauche.endSynchronization();

	}

	public void getRedAndDrawInTheBick() {
		SampleProvider ultra = new EV3ColorSensor(SensorPort.S3).getRedMode();
		float[] sample = new float[ultra.sampleSize()];
		while (true) {

			ultra.fetchSample(sample, 0);
			LCD.drawString(Double.toString(sample[0]), 1, 1);
			Delay.msDelay(100);
			LCD.clear();

		}
	}

	public void followTheLineForEver() {
		SampleProvider color = new EV3ColorSensor(SensorPort.S4).getRedMode();
		float[] sample = new float[color.sampleSize()];
		while (true) {

			color.fetchSample(sample, 0);
			Lumiere = sample[0];

			Correction = (Lumiere - 0.325) * 0.4;
			VitesseC = ((Correction * -1) + 0.4) * roueDroite.getMaxSpeed();
			VitesseB = ((Correction * 1) + 0.4) * roueGauche.getMaxSpeed();

			roueDroite.setSpeed((float) VitesseC);
			LCD.drawString(Double.toString(VitesseB), 1, 1);
			LCD.drawString(Double.toString(VitesseC), 1, 2);
			LCD.drawString(Double.toString(Lumiere), 1, 3);

			roueGauche.setSpeed((float) VitesseB);
			Delay.msDelay(10);
			roueDroite.forward();
			roueGauche.forward();
		}
	}

	public void followTheLineWait(Port capteurCouleur, double moyenCouleur, int timerMs) {
		SampleProvider color = new EV3ColorSensor(capteurCouleur).getRedMode();
		float[] sample = new float[color.sampleSize()];
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		while(cal.compareTo(Calendar.getInstance())*-1>= timerMs){
			
	        LCD.drawString( sdf.format(cal.getTime()), 1, 1);
	        Delay.msDelay(1000);
	        color.fetchSample(sample, 0);
			Lumiere = sample[0];

			Correction = (Lumiere - moyenCouleur) * 0.4;
			VitesseC = ((Correction * -1) + 0.4) * roueDroite.getMaxSpeed();
			VitesseB = ((Correction * 1) + 0.4) * roueGauche.getMaxSpeed();

			roueDroite.setSpeed((float) VitesseC);
			LCD.drawString(Double.toString(VitesseB), 1, 1);
			LCD.drawString(Double.toString(VitesseC), 1, 2);
			LCD.drawString(Double.toString(Lumiere), 1, 3);

			roueGauche.setSpeed((float) VitesseB);
			Delay.msDelay(10);
			roueDroite.forward();
			roueGauche.forward();
	        

		}
		/*
				
		
		while (true) {

			color.fetchSample(sample, 0);
			Lumiere = sample[0];

			Correction = (Lumiere - 0.325) * 0.4;
			VitesseC = ((Correction * -1) + 0.4) * roueDroite.getMaxSpeed();
			VitesseB = ((Correction * 1) + 0.4) * roueGauche.getMaxSpeed();

			roueDroite.setSpeed((float) VitesseC);
			LCD.drawString(Double.toString(VitesseB), 1, 1);
			LCD.drawString(Double.toString(VitesseC), 1, 2);
			LCD.drawString(Double.toString(Lumiere), 1, 3);

			roueGauche.setSpeed((float) VitesseB);
			Delay.msDelay(10);
			roueDroite.forward();
			roueGauche.forward();
		}
		*/
	}

}
