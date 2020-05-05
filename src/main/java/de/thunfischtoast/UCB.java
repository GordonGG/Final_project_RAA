package de.thunfischtoast;
import org.apache.commons.math3.linear.*;
import java.lang.Math;
import java.util.Random;

public class UCB {
	protected int n, T, arm;
	protected double reward = 0;
	protected double estimate_reward = 0;
	protected double[][] A;
	Random random = new Random(7);
	 /**
     * @param n     number of arms
     * @param alpha how many times the standard deviation of the expected payoff
     *              is added to the predicted payoff in the ridge regression
     */
	public UCB(int n) {
		this.n = n;
		A = new double[n][4];
		int T = 0;
		for (int i = 0; i < n; i++) {
			A[i][0] = i;
			A[i][1] = 1;
			A[i][2] = 0;
			A[i][3] = 0;
		}
	}

	public void receiveReward(int arm, double reward) {
		this.arm = arm;
		this.reward = reward;
		T = T + 1;		
		for (int i = 0; i < n; i++) {
			if (i == arm) {
				A[arm][1] = A[arm][1] + 1;
				A[arm][2] = A[arm][2] + reward;
				A[arm][3] = A[arm][2] / (A[arm][1]-1) + calculate_delta(T, A[arm][1]);
			} else {
				A[i][3] = A[i][2] / A[i][1] + calculate_delta(T, A[i][1]);
			}
		}

	}

	public double calculate_delta(int T, double chosen_count) {
		return Math.sqrt(2 * Math.log(T / chosen_count));
	}

	public int chooseArm() {
		int curArm = 0;
		for (int i = 0; i < n; i++) {
			if (A[curArm][3] >= A[i][3]) {
				curArm = curArm;
			} else {
				curArm = i;
			}

		}
		return curArm;
	}

}
