package svg;

import statistics.*;

public class ControlPoint {
	
	public int x;
	public int y;
	public double xNormD;
	public double yNormD;
	public int xNormInt;
	public int yNormInt;
	public Vector v0;
	public Vector v1;
	public Vector v2;
	public Vector v3;
	public Vector v0Tan1; //tangent of the first control point
	public Vector v3Tan2; //tangent of the second control point
	public int start;
	public int end;
	public int[]  coordinatesX;
	public int[]  coordinatesY;
	
	
	public ControlPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public ControlPoint(int[] coordinatesX, int[]  coordinatesY, int start, int end) {
		this. coordinatesX =  coordinatesX;
		this.coordinatesY =  coordinatesY;
		this.start = start;
		this.end = end;
		this.v0 = new Vector(coordinatesX[start], coordinatesY[start]);
		this.v3 = new Vector(coordinatesX[end], coordinatesY[end]);
		this.getAlpha1();
		this.getAlpha2();
	}

	
	public void normalizeTangent() {
		double len1 = Math.sqrt(x * x  + y * y);
		System.out.println(len1);
		xNormD = ((double) x) /  len1;
		yNormD = ((double) y) /  len1;
		xNormInt = (int) xNormD;
		yNormInt = (int) yNormD;
	}
	
	public double getAlpha1() {
		MatrixC matrix = new MatrixC(coordinatesX, coordinatesY, start, end);
		matrix.calculateX1();
		matrix.calculateX2();
		matrix.setC11();
		matrix.setC12();
		matrix.setC22();
		v0Tan1 = matrix.tangent1;
		double denominator = matrix.calculateDeterminant();
		MatrixC numeratorMatrix = new MatrixC(matrix.x1, matrix.c12, matrix.x2, matrix.c22);
		double numerator = numeratorMatrix.calculateDeterminant();
//		System.out.println("alpha 1 denominator = " + denominator);
//		System.out.println("alpha 1 numerator = " + numerator);
		double alpha1 = numerator/denominator;
		v0Tan1 = Vector.multiplyByScaler(v0Tan1, alpha1);
		v1 = Vector.add(v0Tan1, v0);
//		System.out.println("alpha 1 = " + alpha1);
		System.out.println("control V1 point x = " + v1.xD + " y = " + v1.yD);
		return alpha1;
	}
	
	public double getAlpha2() {
		MatrixC matrix = new MatrixC(coordinatesX, coordinatesY, start, end);
		matrix.calculateX1();
		matrix.calculateX2();
		matrix.setC11();
		matrix.setC12();
		matrix.setC22();
		v3Tan2 = matrix.tangent2;
		double denominator = matrix.calculateDeterminant();
		
		MatrixC numeratorMatrix = new MatrixC(matrix.c11, matrix.x1, matrix.c12, matrix.x2);
		double numerator = numeratorMatrix.calculateDeterminant();
		System.out.println("alpha 2 denominator = " + denominator);
		System.out.println("alpha 2 numerator = " + numerator);
		double alpha2 = numerator/denominator;
		v3Tan2 = Vector.multiplyByScaler(v3Tan2, alpha2);
		v2 = Vector.add(v3Tan2, v3);
		System.out.println("alpha 2 = " + alpha2);
		System.out.println("control V2 pointV x = " + v2.xD + " y = " + v2.yD);
		return alpha2;
	}
	
	
}
