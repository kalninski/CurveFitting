package svg;

import statistics.*;
import java.util.*;

public class ControlPoint {
	
	public int x;
	public int y;
//	public double xNormD;
//	public double yNormD;

	public static Vector v0;
	public static Vector v1;
	public static Vector v2;
	public static Vector v3;
	public Vector v0Tan1; //tangent of the first control point
	public Vector v3Tan2; //tangent of the second control point
	public int start;
	public int end;
	public double[]  coordinatesX;//double
	public double[]  coordinatesY;//double
	public double[] curveX;//Coordinates of the curve Not the original shape, for error estimation //double
	public double[] curveY;//the size is the size of the subarray, each subarray when ControlPoint is instantiated has the array of this size//double
	public double[] error;
	public double maxError;
	public double incr;

	
	
	
	public ControlPoint(double[] coordinatesX, double[]  coordinatesY, int start, int end) {
		this. coordinatesX =  coordinatesX;
		this.coordinatesY =  coordinatesY;
		this.start = start;
		this.end = end;
		this.v0 = new Vector(coordinatesX[start], coordinatesY[start]);
		this.v3 = new Vector(coordinatesX[end], coordinatesY[end]);
		this.incr = 1/((double) (end - start));
		this.getAlpha1();
		this.getAlpha2();
		this.curveX = new double[end - start];
		this.curveY = new double[end - start];
	}

	
	public double getAlpha1() {
		MatrixC matrix = new MatrixC(coordinatesX, coordinatesY, start, end);
		matrix.calculateX1X2();
		matrix.setC11();
		matrix.setC12();
		matrix.setC22();
		v0Tan1 = matrix.tangent1;
		double denominator = matrix.calculateDeterminant();
		double epsilon = 1e-10;
		double alpha1;
		MatrixC numeratorMatrix1 = new MatrixC(matrix.x1, matrix.c12, matrix.x2, matrix.c22);
		double numerator = numeratorMatrix1.calculateDeterminant();
		if(denominator < epsilon) {
			alpha1 = 0;
			v0Tan1 =v0Tan1 = Vector.multiplyByScaler(v0Tan1, 1);
		}else {
			alpha1 = numerator/denominator;
			v0Tan1 = Vector.multiplyByScaler(v0Tan1, alpha1);
		}
		v1 = Vector.add(v0Tan1, v0);
//		System.out.println("determinant = " + denominator + " c11 = " + matrix.c11 + " c12 = " + matrix.c12 + " c21 = " + matrix.c21 + " c22 = " + matrix.c22);
		return alpha1;
	}
	
	public double getAlpha2() {
		MatrixC matrix = new MatrixC(coordinatesX, coordinatesY, start, end);
		matrix.calculateX1X2();
		matrix.setC11();
		matrix.setC12();
		matrix.setC22();
		v3Tan2 = matrix.tangent2;
		double denominator = matrix.calculateDeterminant();
		double epsilon = 1e-10;
		double alpha2;
		MatrixC numeratorMatrix2 = new MatrixC(matrix.c11, matrix.x1, matrix.c12, matrix.x2);
		double numerator = numeratorMatrix2.calculateDeterminant();
//		System.out.println("alpha 2 denominator = " + denominator);
//		System.out.println("alpha 2 numerator = " + numerator);
		if(denominator < epsilon) {
			alpha2 = 0;
			v3Tan2 = Vector.multiplyByScaler(v3Tan2, 1);
		}else {
			alpha2 = numerator/denominator;
			v3Tan2 = Vector.multiplyByScaler(v3Tan2, alpha2);
		}
		v2 = Vector.add(v3Tan2, v3);
//		System.out.println("alpha 2 = " + alpha2);
//		System.out.println("control V2 pointV x = " + v2.xD + " y = " + v2.yD);
		return alpha2;
	}
	
	public void getValuesOfCurve() {
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
			curveX[i] = lerpVec.xD;
			curveY[i] = lerpVec.yD;
			t += incr;
		}
//		System.out.println("curveX values = " + Arrays.toString(curveX) + "\n" + "curveY values =  " + Arrays.toString(curveY));
	}
	
	public int getErrorIndex(Function f) {
		int errorIndex = 0;
		maxError = 0;
		error = new double[end - start];
		for(int i = 0; i < error.length; i++) {
			error[i] = Math.abs(f.yActualVal[start + i] - curveY[i]);
			if(error[i] > error[errorIndex]) {
				maxError = error[i];
				errorIndex = i;
			}
		}
		return errorIndex;
	}
	
	public static Vector getOneValueOfCurve(double t) {
		double coeff0 = Math.pow((1-t), 3);
		double coeff1 = Math.pow((1-t), 2) * t * 3;
		double coeff2 = Math.pow(t, 2) * (1-t) * 3;
		double coeff3 = Math.pow(t, 3);
		Vector v0New = Vector.multiplyByScaler(v0, coeff0);
		Vector v1New = Vector.multiplyByScaler(v1, coeff1);
		Vector v2New = Vector.multiplyByScaler(v2, coeff2);
		Vector v3New = Vector.multiplyByScaler(v3, coeff3);
		return Vector.add4Vectors(v0New, v1New, v2New, v3New);
	}
	
	public static Vector getOneValueOfCurveFirstDerivative(double t) {
		double coeff0 = Math.pow((1-t), 2);
		double coeff1 = (1-t) * t * 2;
		double coeff2 = Math.pow(t, 2);
		Vector deltaV0 = Vector.subtract(v1, v0);
		Vector deltaV1 = Vector.subtract(v2, v1);
		Vector deltaV2 = Vector.subtract(v3, v2);
		Vector v0New = Vector.multiplyByScaler(deltaV0, coeff0);
		Vector v1New = Vector.multiplyByScaler(deltaV1, coeff1);
		Vector v2New = Vector.multiplyByScaler(deltaV2, coeff2);
		Vector sum = Vector.add3Vectors(v0New, v1New, v2New);
		return Vector.multiplyByScaler(sum, 3);
	}
	
	public static Vector getOneValueOfCurveSecondDerivative(double t) {
		double coeff0 = 1-t;
		double coeff1 = t;
		Vector delta1V0 = Vector.subtract(v1, v0);
		Vector delta1V1 = Vector.subtract(v2, v1);
		Vector delta1V2 = Vector.subtract(v3, v2);
		Vector delta2V0 = Vector.subtract(delta1V1, delta1V0);
		Vector delta2V1 = Vector.subtract(delta1V2, delta1V1);
		delta2V0 = Vector.multiplyByScaler(delta2V0, coeff0);
		delta2V1 = Vector.multiplyByScaler(delta2V1, coeff1);
		Vector sum = Vector.add(delta2V1, delta2V0);
		return Vector.multiplyByScaler(sum, (6));
	}
	
	public double updateTParameter(double t) {
		
		
		
		return t;
	}

}