import java.util.Arrays;
import java.util.Scanner;

public class Matrix {

	private int row,col;
	private double[][] orignal,solution;
	
	public Matrix(int row, int col)
	{
		this.row = row;
		this.col = col;
		this.orignal = new double[row][col];
		this.solution = new double[row][col];
	}

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
	
	private void swapRows(int rOne, int rTwo)
	{
		for(int c=0; c<col; c++)
		{
			double temp = solution[rOne][c];
			solution[rOne][c] = solution[rTwo][c];
			solution[rTwo][c] = temp;
		}
	}

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
				
				System.out.println("Single Row Op done");
			}	
		}
		System.out.println("Row Op Done");
	}

	private void factorRow(int rr, int cc)
	{
		double factor = solution[rr][cc];
		if(factor!=0)
			for(int c=cc; c<col; c++)
			{
				solution[rr][c] = solution[rr][c]/factor;
			}
	}
	
	private static double[][] deepCopyMatrix(double[][] input) {
	    if (input == null)
	        return null;
	    double[][] result = new double[input.length][];
	    for (int r = 0; r < input.length; r++) {
	        result[r] = input[r].clone();
	    }
	    return result;
	}
	
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
