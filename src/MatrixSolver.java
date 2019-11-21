import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * MatrixSolver program implements various ways to solve
 * a matrix by using the self-authored Matrix class. The 
 * class is capable of matrix REF of a matrix of any size.
 *
 * @author	Hamza Syed
*/
public class MatrixSolver {

	public static void main(String[] args) throws FileNotFoundException {
		
		//Sets System.out to print to specified file. Did this because ouput was too long for proper snapshot
		System.setOut(new PrintStream(new FileOutputStream("outputThree.txt")));
		
		//The following line constructs a matrix of 3x4 size.
		Matrix matrix = new Matrix(3,4);
		
		//The following line allows for the user to fill the constructed matrix.
		matrix.fill();
		
		//Solves the matrix using purely computational power without any fancy techniques.
		matrix.bruteSolve();
		System.out.println("\nBRUTE FORCED SOLUTION:\n"+matrix.solution()+"\n\n"+matrix.backSub()+"\n\n========================\n\n");
		
		//Solves the matrix using partial pivots.
		matrix.pivotSolve();
		System.out.println("\nPivot SOLUTION:\n"+matrix.solution()+"\n\n"+matrix.backSub());
		
	}

}
