import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * PercolationStats
 * 
 * @author Bryant
 * @param N size of grid (N by N)
 * @param T number of experiments to run.
 */

public class PercolationStats {

	int experiments;
	double[] openedAverages;
	
	public PercolationStats(int N, int T){
		if(N < 0 || T < 0){
			throw new IllegalArgumentException("Cannot use a negative number.");
		}
		experiments = T;
		openedAverages = runExperiement(N,T);
	}
	
	/**
	 * Performs T independent experiments on an N b yN grid
	 * @param N
	 * @param T
	 */
	private double[] runExperiement(int N, int T) {
		
		double[] openedCount = new double[T];
		
		for (int i = 0; i < T ; i++){
			
			Percolation p = new Percolation(N);
			int count = 0;
			
			while(!p.percolates()){
				
				int row = StdRandom.uniform(N);
				int column = StdRandom.uniform(N);
				if(!p.isOpen(row, column)){
					p.open(row,column);
					count++;
				}
			}
			openedCount[i] = ((double) count)/(N*N);
		}
		return openedCount;
	}

	/**
	 * Returns the mean value of this PercolationStats experiment. Sample mean of percolation threshold.
	 * @return double
	 */
	public double mean(){ 
		return StdStats.mean(openedAverages);
	}
	
	/**
	 * Returns the deviation. Samples standard deviation of percolation threshold. 
	 * @return
	 */
	public double stddev(){ 
		return StdStats.stddev(openedAverages);
	}
	
	/**
	 * Returns low end-point of 95% confidence interval.
	 * @return
	 */
	public double confidenceLow(){ 
		return mean() - StdStats.stddev(openedAverages);
	}

	/**
	 * Returns high endpoint of 95% confidence interval.
	 * @return
	 */
	public double confidenceHigh(){ 
		return mean() + StdStats.stddev(openedAverages);
	}
	
	/**
	 * Main method. Tests this class by instantiating it and calling the sample methods. Prints returned values.
	 * @param args
	 */
	public static void main(String[] args){

		PercolationStats test = new PercolationStats(200,100);
		
		StdOut.println("Experiment Complete: ");
		StdOut.println();
		StdOut.printf("%-20s %.20f\n", "mean():", test.mean());
		StdOut.printf("%-20s %.20f\n", "stddev():", test.stddev());
		StdOut.printf("%-20s %.20f\n", "confidenceLow():", test.confidenceLow());
		StdOut.printf("%-20s %.20f\n", "confidenceHigh():", test.confidenceHigh());
	}
	
	
}
