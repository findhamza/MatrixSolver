/**
 * Matrix class capability (SUMMARY):
 * 		-Stores Original matrix
 * 		-Stores Solution matrix
 * 		-Fill() method to populate original matrix
 * 		-toString() Overloaded toString method to print Original matrix
 * 		-solution() method returns a string with a solved matrix; must run bruteSolve() or pivotSolve() prior
 * 		-bruteSolve() method solves the Original matrix via brute force; stores solved matrix in Solution matrix
 * 		-pivotSolve() method solves the Original matrix via partial pivot; stores solved matrix in Solution matrix
 * 		-swap() method checks column values to stage them for swapping via swapRows() method
 * 		-swapRows() methods swaps the two rows passed to it
 * 		-rowOp() method tries to resolve rows around the passed row to 0
 * 		-factorRow() method factors a row by the given column in that row
 * 		-deepCopyMatrix() returns a value rather than reference of a given 2D array
 * 		-backSub() returns a string with resolved values for x(n) if possible
 * 
 *
 * @author	Hamza Syed
*/

import java.util.Arrays;
import java.util.Scanner;

public class Matrix {

	/**
	 * Global Variables:
	 * 		-"row" stores the number of rows in the matrix instance
	 * 		-"col" stores the number of columns in the matrix instance
	 * 		-"orignal" stores the originally filled matrix via fill()
	 * 		-"solution" stores the solved matrix. Is refreshed whenever bruteSolve() and pivotSolve() are run
	 */
	private int row,col;
	private double[][] orignal,solution;
	
	/**
	 * Purpose:
	 * 		-Sole constructor of the matrix class which defines the bounds for our 2d matrix
	 * Input:
	 * 		-takes in "row" int to define amount of rows as argument
	 * 		-takes in "col" int to define amount of columns as argument
	 * Output:
	 * 		-No return
	 * 		-Sets the default values for "row", "col", "orignal", and "solution"
	 * @param row
	 * @param col
	 */
	public Matrix(int row, int col)
	{
		this.row = row;
		this.col = col;
		this.orignal = new double[row][col];
		this.solution = new double[row][col];
	}

	/**
	 * Purpose:
	 * 		-Allows for the user to fill the Orignal matrix
	 * Input:
	 * 		-Does not take arguments
	 *  	-Prompts the user for values at given (row,col) to be filled with
	 *  Output:
	 *  	-No return
	 *  	-Sets the Orignal matrix with user specified values
	 */
	public void fill()
	{
		Scanner scan = new Scanner(System.in);
		
		for(int r=0; r<row; r++)
		{
			for(int c=0; c<col; c++)
			{
				System.out.print("Please enter value at ("+(r+1)+","+(c+1)+"): ");
				orignal[r][c] = scan.nextDouble();
				System.out.println(toString());
			}
		}
	}
	
	/**
	 * Purpose:
	 * 		-Overload the toString() method to print the Orignal matrix
	 * Input:
	 * 		-No Arguments
	 * 		-No User Input
	 * Output:
	 * 		-returns a formated matrix of the Orignal matrix
	 */
	public String toString()
	{
		String matrix = "";
		
		for(int r=0; r<row; r++)
		{
			matrix+="|\t";
			for(int c=0; c<col; c++)
			{
				if(c==col-1)
					matrix+=orignal[r][c]+"\t|\n";
				else
					matrix+=orignal[r][c]+",\t";
			}
		}
		return matrix;
	}
	
	/**
	 * Purpose:
	 * 		-Returns the Solution matrix as string. Should only be ran after either bruteSolve()
	 * 		or pivotSolve() have been executed. Otherwise it will return a blank matrix.
	 * Input:
	 * 		-No Arguments
	 * 		-No Inputs
	 * Output:
	 * 		-Returns Solution matrix as formated matrix string
	 * @return
	 */
	public String solution()
	{
		String matrix = "";
		
		for(int r=0; r<row; r++)
		{
			matrix+="|\t";
			for(int c=0; c<col; c++)
			{
				if(Math.abs(solution[r][c]-Math.round(solution[r][c])) < 1E-14)
					solution[r][c] = Math.round(solution[r][c]);
				if(c==col-1)
					matrix+=solution[r][c]+"\t|\n";
				else
					matrix+=solution[r][c]+",\t";
			}
		}
		return matrix;
	}

	/**
	 * Purpose:
	 * 		-Solves the matrix via raw computations. No pivots. Uses the Orignal matrix, and copies
	 * 		it to Solution matrix as to keep the orignal values for comparison.
	 * Input:
	 * 		-No arguments
	 * 		-No User Input
	 * Output:
	 * 		-No return
	 * 		-Updates Solution matrix with rref(Orignal)
	 */
	public void bruteSolve()
	{
		solution = deepCopyMatrix(orignal);
		
		for(int c=0; c<col; c++)
		{
			for(int r=0; r<row; r++)
			{
				if(r==c)
				{
					factorRow(r,c);
					rowOp(r);
				}
			}
		}
	}

	/**
	 * Purpose:
	 * 		-Solves the matrix via partial pivots. Uses the Orignal matrix, and copies
	 * 		it to Solution matrix as to keep the orignal values for comparison.
	 * Input:
	 * 		-No arguments
	 * 		-No User Input
	 * Output:
	 * 		-No return
	 * 		-Updates Solution matrix with rref(Orignal)
	 */
	public void pivotSolve()
	{
		solution = deepCopyMatrix(orignal);
		
		for(int c=0; c<col; c++)
		{
			for(int r=0; r<row; r++)
			{
				if(r==c)
				{
					swap(r,c);
					factorRow(r,c);
					rowOp(r);
				}
			}
		}
	}

	/**
	 * Purpose:
	 * 		-Goes through all the rows to see what needs to be swapped based on the given
	 * 		parameters. Utilizes swapRow() to do actual row swapping.
	 * Input:
	 * 		-int rr 
	 * 			-sets a ceiling on what can and can't be swapped
	 * 		-int cc
	 * 			-defines the column which will be used for the magnitude comparison
	 * Output:
	 * 		-No return
	 * 		-Prints to console the row swap operation
	 */
	private void swap(int rr, int cc)
	{
		for(int i=rr; i<row-1; i++)
		{
			for(int j=rr; j<row-i-1; j++)
			{
				if(Math.abs(solution[j][cc])<Math.abs(solution[j+1][cc]))
				{
					swapRows(j,j+1);
					System.out.println("SWAPED: Row["+(j+1)+"] & Row["+(j+2)+"]\n"+this.toString());
				}
			}
		}
		
	}

	/**
	 * Purpose:
	 * 		-Helper method for swap(), swaps the rows defined in the arguments.
	 * Input:
	 * 		-int rOne
	 * 		-int rTwo
	 * 		-Naming schema is irrelevant and does not correlate to the actual matrix
	 * Output:
	 * 		-No return
	 * 		-Updates Solution matrix with swapped rows.
	 */
	private void swapRows(int rOne, int rTwo)
	{
		for(int c=0; c<col; c++)
		{
			double temp = solution[rOne][c];
			solution[rOne][c] = solution[rTwo][c];
			solution[rTwo][c] = temp;
		}
	}

	/**
	 * Purpose:
	 * 		-Does row operations on all the rows around the row passed to rowOp().
	 * 		-Goal is to reduce any values above and below a 1 in our passed row to 0
	 * Input:
	 * 		-int rr
	 * 			-Defines the row all the operations will be done around column by column
	 * Output:
	 * 		-No return
	 * 		-Updates Solution matrix with new values based on row operation
	 */
	private void rowOp(int rr)
	{
		for(int rows=0; rows<row; rows++)
		{
			if(rows==rr)
				continue;
			else
			{
				double factor = solution[rows][rr];
				for(int cols=0; cols<col; cols++)
				{
					solution[rows][cols] = solution[rows][cols] - (solution[rr][cols]*factor);
					if(Math.abs(solution[rows][cols]) <  1.0E-14)
						solution[rows][cols]=0;
					System.out.println(this.solution());
				}
			}	
		}
	}

	/**
	 * Purpose:
	 * 		-Factors the given value defined by the input parameters to 1 while factoring
	 * 		the whole row by the value that helped achieve the 1
	 * Input:
	 * 		-int rr
	 * 			-Defines which row to target
	 * 		-int cc
	 * 			-Defines which column to target
	 * 		-The targeted row is divided by the value given by the crossed value given by rr and cc
	 * Output:
	 * 		-No return
	 * 		-Updates Solution matrix with factored row
	 */
	private void factorRow(int rr, int cc)
	{
		double factor = solution[rr][cc];
		if(factor!=0)
			for(int c=cc; c<col; c++)
			{
				solution[rr][c] = solution[rr][c]/factor;
			}
	}

	/**
	 * Purpose:
	 * 		-Copy 2d array by value and not by reference. This is useful to move values from Orignal to Solution
	 * 		before computations as to preserve the orginal user given values.
	 * Input:
	 * 		-double[][] input
	 * 			-Input array to be copied
	 * Output:
	 * 		-return 2d array with values of input array
	 */
	private static double[][] deepCopyMatrix(double[][] input) {
	    if (input == null)
	        return null;
	    double[][] result = new double[input.length][];
	    for (int r = 0; r < input.length; r++) {
	        result[r] = input[r].clone();
	    }
	    return result;
	}

	/**
	 * Purpose:
	 * 		-Solves for the x values based on the Solution matrix.
	 * 		-This should not be ran before bruteSolve() or pivotSolve()
	 * Input:
	 * 		-No arguments
	 * 		-No User Input
	 * Output:
	 * 		-Returns a string with all x values computed for. Returns equations in case of free variables.
	 */
	public String backSub()
	{
		String bSub = "Back Substituted Values: \n";
		Double[] xVal = new Double[row];
		xVal[row-1] = solution[row-1][col-1];
		
		if(solution[row-1][row-1] != 0)
		{
			for(int i=row-2; i>=0; i--)
			{
				xVal[i] = solution[i][col-1];
			}
			for(int z=0; z<xVal.length; z++)
			{
				bSub += "x"+(z+1)+" = "+xVal[z]+"\n";
			}
			
			return bSub;
		}
		else
		{
			for(int i=0; i<row-1; i++)
			{
				bSub += "x"+(i+1)+" = ";
				for(int j=i+1; j<col-1; j++)
					if(solution[i][j]!=0)
						bSub += "("+solution[i][j]+")x"+(j+1)+" +";

				bSub += " "+solution[i][col-1]+"\n";
			}
			
			bSub += "x"+(row)+" = Free Variable";
			
			return bSub;
		}
	}

}
