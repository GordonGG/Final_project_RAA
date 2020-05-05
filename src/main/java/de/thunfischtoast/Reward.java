package de.thunfischtoast;

/**
 * 
 * @author Gao Tianhong
 *
 */
public class Reward {	
	
	/** SNR in Shannon formula */
	double SNR = 100;
	/** Initial time delay (ms)*/
	double standardTime;
	
	double alpha;
	
	int budget;
	
	public Reward(int SNR,int time,int alpha,int budget) {
		this.SNR = SNR;		
		this.standardTime = time;	
		this.alpha = alpha;
	}
	
    /**
     * @param arm         Selected arm  
     * @param context     the context of vehicles
     */
	
	public double calculateReward(int arm, double[] context) {
		double event = context[0]; 
		double speed = context[1]; 
		int carNum = (int) context[2];		
		double baseTime;
		double transportTime;
		double communicateSpeed;
		double reward;
		baseTime = 1200 - 5*alpha*Math.sqrt(speed * carNum);		
		/**  Shannon equation */
		communicateSpeed = (arm + 1) * 180 * (Math.log10(1 + SNR) / Math.log10(2));
		transportTime = event * 1000000 / communicateSpeed;
		reward = (baseTime - transportTime);
		return format(reward/1000);
	}
	
	
	/** Keep two decimal places */
	public static double format(double value) {
		return Double.parseDouble(String.format("%.2f", value));
	}
	
	
	
}
