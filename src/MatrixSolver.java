
public class MatrixSolver {

	public static void main(String[] args) {
		Matrix matrix = new Matrix(3,4);
		matrix.fill();
		matrix.bruteSolve();
		System.out.println("\nBRUTE FORCED SOLUTION:\n"+matrix.solution()+"\n\n========================\n\n");
		
		matrix.pivotSolve();
		System.out.println("\nPivot SOLUTION:\n"+matrix.solution()+"\n\n"+matrix.backSub());
		
	}

}
