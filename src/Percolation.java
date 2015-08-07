
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * 
 * @author Bryant
 * @Sources Princeton University's Cedric.
 */

public class Percolation {
	
	int n;
	int topNode;
	int bottomNode;
	boolean[][] grid;
	WeightedQuickUnionUF unionFind;
	WeightedQuickUnionUF unionFind2;
	private int count;
	
	
	public Percolation(int N){
		if(n < 0){
			throw new IllegalArgumentException("The grid cannot be instantiated with a negative value.");
		}
		
		grid = new boolean[N][N];
		
		n = N;
		count = n*n;
		unionFind = new WeightedQuickUnionUF((count + 2));
		unionFind2 = new WeightedQuickUnionUF((count + 1));
		topNode = count;
		bottomNode = count + 1;
		
		
		for(int i = 0; i < n; i++){
			unionFind.union(i, topNode);
			unionFind2.union(i, topNode);
			unionFind.union(count-i-1, bottomNode);
		}	
	}
	
	/**
	 * Opens site (row i, column j) if it is not open already, then 
	 * checks for any adjacent sites that are open and joins them.
	 * 
	 * @throws IndexOutOfBoundsException.
	 * 
	 * @param i horizontal (x) coordinate.
	 * @param j vertical (y) coordinate.
	 */
	
	public void open (int i, int j){
		if(i > n-1 || i < 0 || j > n-1 || j < 0){
			throw new IndexOutOfBoundsException("One or both of the parameters exceed the size of the grid.");
		}
		
		// open site (row i, column j) if it is not open already
		
		grid[i][j] = true;
		
		// Check for any adjacent sites that are open and joins them.
		
		if(i-1 >= 0 && grid[i-1][j]) {
			unionFind.union(toID(i-1,j),toID(i,j));
			unionFind2.union(toID(i-1,j),toID(i,j));
		}
		if(i+1 < n && grid[i+1][j]) {
			unionFind.union(toID(i+1,j),toID(i,j));
			unionFind2.union(toID(i+1,j),toID(i,j));
		}
		if(j-1 >= 0 && grid[i][j-1]) {
			unionFind.union(toID(i,j-1),toID(i,j));
			unionFind2.union(toID(i,j-1),toID(i,j));
		}
		if(j+1 < n && grid[i][j+1]) {
			unionFind.union(toID(i,j+1),toID(i,j));
			unionFind2.union(toID(i,j+1),toID(i,j));
		}
		
		
	}
	
	/**
	 * Checks to see if a given site is open and returns true it is it, false if it is not.
	 * 
	 * @param i horizontal (x) coordinate.
	 * @param j vertical (y) coordinate.
	 * @return boolean.
	 */
	
	public boolean isOpen(int i, int j) {
		if(i > n-1 || i < 0 || j > n-1 || j < 0){
			throw new IndexOutOfBoundsException("One or both of the parameters exceed the size of the grid.");
		}
		
		// is site (row i, column j) open?
		
		return grid[i][j];
	}
	
	/**
	 * Checks to see if a given site is connected to the top (or full) and 
	 * returns true if it is and false if it is not.
	 * 
	 * @param i horizontal (x) coordinate.
	 * @param j vertical (y) coordinate.
	 * @return boolean.
	 */
	
	public boolean isFull(int i, int j) {
		if(i > n-1 || i < 0 || j > n-1 || j < 0){
			throw new IndexOutOfBoundsException("One or both of the parameters exceed the size of the grid.");
		}
		
		if(!isOpen(i,j)) return false;
		
		return unionFind2.connected(topNode, toID(i,j));
	}
	
	/**
	 * Checks to see if the bottom node is connected to the top node. Returns boolean.
	 * @return boolean.
	 */
	public boolean percolates() {
		
		return unionFind.connected(topNode, bottomNode);
	}
	
	/**
	 * Calculates an integer that would represents a 2d coordinate pair as a 1d index in 
	 * a 1 dimensional array if the the 2d coordinates containing array were read line by 
	 * line, from left to right.
	 * 
	 * 
	 * @param i horizontal (x) coordinate.
	 * @param j vertical (y) coordinate.
	 * @return
	 */	
	private int toID(int i, int j){
		
		return (n*i) + j;	
	}
	
	public static void main(String[] args){
		
		Percolation perc = new Percolation(2);
		
		perc.open(0, 0);
		perc.open(0, 1);
		System.out.println(perc.percolates());
		perc.open(1, 1);
		System.out.println(perc.percolates());
	}

}