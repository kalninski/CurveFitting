package svg;

import java.util.Arrays;

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
	public int[] curveX;//Coordinates of the curve Not the oribinal shape, for error estimation 
	public int[] curveY;//Coordinates of the curve Not the oribinal shape, for error estimation 
	
	
	public ControlPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public ControlPoint(int[] coordinatesX, int[]  coordinatesY, int start, int end) {
		this. coordinatesX =  coordinatesX;
		this.coordinatesY =  coordinatesY;
		this.start = start;
		this.end = end;
		this.curveX = new int[end - start];
		this.curveY = new int[end - start];
		this.v0 = new Vector(coordinatesX[start], coordinatesY[start]);
		this.v3 = new Vector(coordinatesX[end], coordinatesY[end]);
		this.getAlpha1();
		this.getAlpha2();
		this.setValuesOfCurve();
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
	
	
	public void setValuesOfCurve() {
		Vector outPutOfBernstein = new Vector();
		double n = ((double) end - start);
		double incr = 1/n;
		double t = 0;
		for(int i = 0; i < n; i++ ) {
			double coeff0 = Math.pow((1-t), 3);
			double coeff1 = Math.pow((1-t), 2) * t * 3;
			double coeff2 = Math.pow(t, 2) * (1-t) * 3;
			double coeff3 = Math.pow(t, 3);
			Vector v0New = Vector.multiplyByScaler(v0, coeff0);
			Vector v1New = Vector.multiplyByScaler(v1, coeff1);
			Vector v2New = Vector.multiplyByScaler(v2, coeff2);
			Vector v3New = Vector.multiplyByScaler(v3, coeff3);
			Vector lerpVec = Vector.add4Vectors(v0New, v1New, v2New, v3New);
			curveX[i] =(int) lerpVec.xD;
			curveY[i] =(int) lerpVec.yD;
			t += incr;
		}
		System.out.println("cureveX valus = " + Arrays.toString(curveX) + "\n" + "curveY values =  " + Arrays.toString(curveY));
	}	
}
