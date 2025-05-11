package vector;

import java.util.Arrays;

import statistics.Function;


public class ControlPoint {
	
	Vector v0;
	Vector v1;
	Vector v2;
	Vector v3;
	double[] functionY;//array of all the values
	double[] functionX;
	double[] polynomialY;
	double[] polynomialX;
	double[] parameterT;
	double[] error;
	double maxError;
	int start;
	int end;
	
	
	public ControlPoint(Function f, int start, int end) {
		this.functionX = f.xActualVal;
		this.functionY = f.yActualVal;
		this.polynomialX = new double[end - start];
		this.polynomialY = new double[end - start];
		this.error = new double[end - start];
		this.start = start;
		this.end = end;
		this.v0 = new Vector(functionX[start], functionY[start]);
		this.v3 = new Vector(functionX[end], functionY[end]);
		initialParameterT();
		setV1();
		setV2();
		getValuesOfCurve();
		System.out.println("COSNTRUCTOR polynomialY " + Arrays.toString(polynomialY));
		System.out.println("COSNTRUCTOR polynomialX " + Arrays.toString(polynomialX));
		
	}
	
	
	public void initialParameterT() {
		double n = end - start;
		parameterT = new double[end - start];
		double increment = 1/n;
		double t = 0;
		for(int i = 0; i < n; i++) {
			parameterT[i] = t;
			t += increment;
		}
//		System.out.println("intitial paramter T arr = " + Arrays.toString(parameterT));
	}
	
	public void initialParameterT1() {
		double n = end - start;
		parameterT = new double[end - start];
		double increment = 1/n;
		double t = 0;
		for(int i = 0; i < n; i++) {
			parameterT[i] = t;
			t += increment;
		}
//		System.out.println("intitial paramter T arr = " + Arrays.toString(parameterT));
	}
	
	public int getErrorIndex(Function f) {
		int errorIndex = 0;
		maxError = 0;
		double errorX = 0;
		double errorY = 0;
		error = new double[end - start];
		for(int i = 0; i < error.length; i++) {
			errorY = Math.abs(f.yActualVal[start + i] - polynomialY[i]);
			errorX = Math.abs(f.xActualVal[start + i] - polynomialX[i]);
//			System.out.println("errorY = " + errorY + " errorX = " + errorX + " polynomialY[i] = " + polynomialY[i] + " polynomialX[i]" + polynomialX[i]);
			errorY = Math.pow(errorY, 2);
			errorX = Math.pow(errorX, 2);
			error[i] = Math.sqrt((errorX + errorY));
//			System.out.println("error[i] = " + error[i]);
			if(error[i] > error[errorIndex]) {
				maxError = error[i];
				errorIndex = i;
//				System.out.println(i + " INDEX ERROR");
				
				
			}
		
		}
		
		System.out.println("error = " + Arrays.toString(error));
//		System.out.println("actualVal Y = " + Arrays.toString(f.yActualVal));
//		System.out.println("polynomial Y = " + Arrays.toString(polynomialY));
		return errorIndex;
	}
	
	public Vector getTangent1() {
		Vector tg = new Vector(0, 0);
		double x = 0;
		double y = 0;
		double count = 1;
		for(int i = 0; i < 5; i++) {

				tg.x += functionX[start + i] - functionX[start];
				tg.y += functionY[start + i] - functionY[start];
//				System.out.println("functionX[start + i] = " + functionX[start + i] + "  functionX[start] = " + functionX[start]);
				count++;
			
		}
		tg.x = tg.x/count;
		tg.y = tg.y/count;
	
		return tg.normalize();
	}
	
	public Vector getTangent2() {
		Vector tg = new Vector(0, 0);
		double x = 0;
		double y = 0;
		double count = 0;
		for(int i = 0; i < 5; i++) {
			

			tg.x += functionX[end - i] - functionX[end];
			tg.y += functionY[end - i] - functionY[end];
			count++;
		
		}
		tg.x = tg.x/count;
		tg.y = tg.y/count;
		
		return tg.normalize();
	}
	
	public double getC11() {
		int sizeParamT = parameterT.length;
		double sum = 0;
		for(int i = 0; i < sizeParamT; i++) {
			
			double t = parameterT[i];
			sum += Math.pow(1-t, 4) * Math.pow(t, 2) * 9;
		}
		return sum;
	}
	
	public double getC12C21() {
		int sizeParamT = parameterT.length;
		double sum = 0;
		Vector tan1 = getTangent1();
		Vector tan2 = getTangent2();
		double tan1DotTan2 = tan1.dot(tan2);
		for(int i = 0; i< sizeParamT; i++) {
			
			double t = parameterT[i];
			sum += Math.pow((1-t), 3) * Math.pow(t, 3) * 9 * tan1DotTan2;
		}
		
		return sum;
	}
	
	public double getC22() {
		int sizeParamT = parameterT.length;
		double sum = 0;
		for(int i = 0; i < sizeParamT; i++) {
			
			double t = parameterT[i];
			sum += Math.pow(t, 4) * Math.pow((1-t), 2) * 9;
		}
		
		return sum;
	}
	
	//One bernstein term specifically for getX1(), getX2() method
	public Vector bernstein(double t, Vector v0, Vector v1, Vector v2, Vector v3 ) {
		double coeff0 = Math.pow(1-t, 3);
		double coeff1 = Math.pow((1-t), 2) * t * 3;
		double coeff2 = Math.pow(t, 2) * (1-t) * 3;
		double coeff3 = Math.pow(t, 3);
		Vector newV0 = Vector.multiplyByScaler(coeff0, v0);
		Vector newV1 = Vector.multiplyByScaler(coeff1, v1);
		Vector newV2 = Vector.multiplyByScaler(coeff2, v2);
		Vector newV3 = Vector.multiplyByScaler(coeff3, v3);
		Vector sum = Vector.add4Vectors(newV0, newV1, newV2, newV3);
//		System.out.println("Bernestein T =  " + t + " coeff0 = " + coeff0 + " newV0 = " + newV0.toString() + " v0 = " + v0 );
		return sum;
	}
	
	public double getX1() {
		Vector tan1 = getTangent1();
		int size = parameterT.length;
		double sum = 0;
		
		for(int i = 0; i < size; i ++) {
			
			double t = parameterT[i];
			Vector dI = new Vector(functionX[start + i], functionY[start + i]);
			Vector bernstein = bernstein(t, v0, v0, v3, v3);
			Vector subtraction = Vector.subtract2Vectors(dI, bernstein);
			double coeffA1 = Math.pow((1-t), 2) * t * 3;
			Vector a1 = Vector.multiplyByScaler(coeffA1, tan1);
			sum += subtraction.dot(a1);
		}
		
		return sum;
	}
	
	public double getX2() {
		Vector tan2 = getTangent2();
		int size = parameterT.length;
		double sum = 0;
		
		for(int i = 0; i < size; i ++) {
			
			double t = parameterT[i];
			Vector dI = new Vector(functionX[start + i], functionY[start + i]);
			Vector bernstein = bernstein(t, v0, v0, v3, v3);
			Vector subtraction = Vector.subtract2Vectors(dI, bernstein);
			double coeffA2 = Math.pow(t, 2) * (1-t) * 3;
			Vector a2 = Vector.multiplyByScaler(coeffA2, tan2);
			sum += subtraction.dot(a2);
			
		}
		
		return sum;
	}
	
	public void setV1() {
		Matrix matrix = new Matrix();
		matrix.c11 = getC11();
		matrix.c12 = getC12C21();
		matrix.c21 = matrix.c12;
		matrix.c22 = getC22();
		double x1 = getX1();
		double x2 = getX2();
		double alpha1;
		double epsilon = 1e-10;
		Vector t1 = getTangent1();
		Matrix num = new Matrix(x1, matrix.c12, x2, matrix.c22);
		double numerator = num.determinant();
		double denominator = matrix.determinant();
		if(Math.abs(denominator) < epsilon) {
			
		//	alpha1 = 0;
			t1 = Vector.multiplyByScaler(1, t1);
			
		}else {
			
			alpha1 = numerator/denominator;
			t1 = Vector.multiplyByScaler(alpha1, t1);
		
		}
		v1 = Vector.add2Vectors(v0, t1);
	//	System.out.println("set V1 = " + v1.toString());
	}
	
	public void setV2() {
		Matrix matrix = new Matrix();
		matrix.c11 = getC11();
		matrix.c12 = getC12C21();
		matrix.c21 = matrix.c12;
		matrix.c22 = getC22();
		double x1 = getX1();
		double x2 = getX2();
		double alpha1;
		double epsilon = 1e-10;
		Vector t2 = getTangent2();
		Matrix num = new Matrix(matrix.c11, x1, matrix.c21, x2);
		double numerator = num.determinant();
		double denominator = matrix.determinant();
		if(Math.abs(denominator) < epsilon) {
			
		//	alpha1 = 0;
			t2 = Vector.multiplyByScaler(1, t2);
			
		}else {
			
			alpha1 = numerator/denominator;
			t2 = Vector.multiplyByScaler(alpha1, t2);
		}
		
		v2 = Vector.add2Vectors(v3, t2);
	}
	
	public void getValuesOfCurve() {
		int size = parameterT.length;
		for(int i = 0; i < size; i++) {
			double t = parameterT[i];
			Vector lerpVec = bernstein(t, v0, v1, v2, v3);
			polynomialY[i] = lerpVec.y;
			polynomialX[i] = lerpVec.x;
	//	System.out.println("lerped vec with bernstein = " + lerpVec.toString() );
		}
	}
	
	
}
