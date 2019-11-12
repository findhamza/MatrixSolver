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

	public void bruteSolve()
	{
		for(int c=0; c<col; c++)
		{
			for(int r=0; r<row; r++)
			{
				if(r==c)
				{
					int 
					factor(solution,r,solution[r][c]);
				}
			}
		}
	}

}
