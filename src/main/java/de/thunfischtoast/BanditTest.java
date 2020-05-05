package de.thunfischtoast;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.Random;
/**
 * 
 * @author Tianhong Gao
 * In this implement, the arm of each algorithm is equal to three, and for each RAA algorithm the context number is three.
 */
public class BanditTest {
	public static void main(String[] args) {
		double maxReward = 0;
		double minReward = 0;
		int budget = 100;
		double randomReward = 0;
		double UCBRward = 0;
		double RAAreward = 0;
		int carEvent = 0;
		double carSpeed;
		RAA raa = new RAA(3, 3, 1);
		UCB UCB = new UCB(3);
		Reward time = new Reward(100,1000,1,100);
        MysqlDemo SQL = new MysqlDemo(); 
		Random random = new Random(7);
		for (int t = 1; t <= 500; t++) {
			int count = 0;
			int Num = (int)carNum(200);
			for (int i = 0; i < Num; i++) {
				if (count <= budget) {
					carEvent = event();
					carSpeed = speed(10,60);
//                    SQL.AddVehicle(t, i, carEvent, carSpeed,Num);
					ArrayRealVector context = new ArrayRealVector(new double[] { carEvent, carSpeed,Num });			
					int armRAA = raa.chooseArm(context);
					int armUCB = UCB.chooseArm();
					double reward = time.calculateReward(random.nextInt(3), context.toArray());
					double reward1 = time.calculateReward(armUCB, context.toArray());		
					double reward2 = time.calculateReward(armRAA, context.toArray());
					randomReward = randomReward + reward;
					UCBRward = UCBRward + reward1;
					RAAreward = RAAreward + reward2;
					maxReward = Math.max(maxReward, reward);
					minReward = Math.min(minReward, reward);
					raa.receiveRewards(new RealVector[] { context }, new int[] { armRAA }, new double[] { reward2 });
					UCB.receiveReward(armUCB, reward1);
					count = count + armRAA;					
				} else {
					count=0;
				}
				System.out.print(randomReward+" "+UCBRward+" "+RAAreward+"\n");
			}
//          SQL.AddReward(t, randomReward, UCBRward,RAAreward);
		}
		System.out.println("Max reward is " + maxReward + " min is " + minReward);
		System.out.println("RAA reward is " + RAAreward + "\n" + "random reward is " + randomReward + "  UCB reward is " + UCBRward);
	}
	/** Randomly generated vehicles obey the poisson distribution */
	private static double carNum(double avgNum) {
		double x = 0, b = 1, c = Math.exp(-avgNum), u;
		do {
			u = Math.random();
			b *= u;
			if (b >= c)
				x++;
		} while (b >= c);
		return  x;
	}
	/** Randomly generated vehicle speed with maximum value of Max and minimum value of Min */
	private static double speed(int max, int min) {		
		double T = min + ((max - min) * new Random().nextDouble());
		return Double.parseDouble(String.format("%.1f", T));
	}

	public static double format(double value) {
		return Double.parseDouble(String.format("%.3f", value));
	}
	
	/** Randomly generate events that will happen to the car */
	public static int event() {
		Random random = new Random();
		return random.nextInt(3)+1;
	}

}
