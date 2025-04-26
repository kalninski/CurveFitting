package svg;

public class MatrixC {
	
	public double c11;
	public double c12;
	public double c21;
	public double c22;
	public double x1;
	public double x2;
	public int[] coordinatesX;
	public int[] coordinatesY;
	public Vector tangent1;
	public Vector tangent2;
	public double dotTan1Tan2;
	public int start;
	public int end;
	
	public MatrixC(int[] coordinatesX, int[] coordinatesY, int start, int end) {
		this.tangent1 = Vector.getTangent1(coordinatesX, coordinatesY, start);
		this.tangent2 = Vector.getTangent2(coordinatesX, coordinatesY, end);
		this.dotTan1Tan2 = this.tangent1.dot(tangent2);
		System.out.println("tangent1.normalize() = " + this.tangent1.xD + ", " + this.tangent1.yD);
		System.out.println("tangent2.normalize() = " + this.tangent2.xD + ", " + this.tangent2.yD);
		
		this.coordinatesX = coordinatesX;
		this.coordinatesY = coordinatesY;
		this.start = start;
		this.end = end;

	}
	
	public MatrixC(Vector tangent1, Vector tangent2, double start, double end) {
		this.tangent1 = tangent1.normalize();

		this.tangent2 = tangent2.normalize();

		this.dotTan1Tan2 = this.tangent1.dot(tangent2);
		System.out.println("tangent1 = "+ this.tangent1.xD + this.tangent1.yD + "tangent2 = "+ this.tangent2.xD + this.tangent2.yD);
	}
	
	public MatrixC(double c11, double c12, double c21, double c22) {
		this.c11 = c11;
		this.c12 = c12;
		this.c21 = c21;
		this.c22 = c22;
	}
	
	public void setC11() {
		double n = end - start;
		double incr = 1/((double) n);
		double t = 0;
		double sum = 0;
		for(int i = 1; i < n; i++) {

			sum += Math.pow((1-t), 4) * t * t * 9;
			t += incr;
		}
		c11 = sum;
	}
	
	public void setC12() {
		double n = end - start;
		double incr = 1/((double) n);
		double t = 0;
		double sum = 0;
		System.out.println("dotTan1Tan2 = " + dotTan1Tan2);
		for(int i = 0; i < n; i++) {
			
			sum += Math.pow((1-t), 3) * t * t * t * 9 * dotTan1Tan2;
			
			t += incr;
		}
		c12 = sum;
		c21 = sum;
	}
	
	
	public void setC22() {
		double n = end - start;
		double incr = 1/((double) n);
		double t = 0;
		double sum = 0;
		for(int i = 0; i < n; i++) {

			sum += Math.pow((1-t), 2) * t * t * t * t * 9;
			t += incr;
		}
		c22 = sum;
	}
	
	public double calculateDeterminant() {
		double det = (this.c11 * this.c22) - (this.c12 * this.c21);
		System.out.println("det = " + det);
		return det;
	}
	
	public double calculateX1() {
		int start1 = (int) start;
		int end1 = (int) end;
		Vector tan1 = Vector.getTangent1(coordinatesX, coordinatesY, start);
		tangent1 = tan1;
		Vector v0 = new Vector(coordinatesX[start], coordinatesY[start]);
		Vector v3 = new Vector(coordinatesX[end], coordinatesY[end]);
		
		double n = end - start;
		double incr = 1/((double) n);
		double t = 0;
		double sum = 0;
		for(int i = 0; i < n; i++) {
			double coeff1 = Math.pow((1-t), 3);// Bernstein polynomial term
			double coeff2 = Math.pow((1-t), 2) * t * 3;// Bernstein polynomial term
			double coeff3 = Math.pow(t, 2) * (1 - t) * 3;// Bernstein polynomial term
			double coeff4 = Math.pow(t, 3);// Bernstein polynomial term
			double coeff5 = Math.pow((1-t), 2) * t * 3;//outer multiplier tan1 * coeff2
			Vector v01 = Vector.multiplyByScaler(v0, coeff1);
			Vector v02 = Vector.multiplyByScaler(v0, coeff2);
			Vector v33 = Vector.multiplyByScaler(v3, coeff3);
			Vector v34 = Vector.multiplyByScaler(v3, coeff4);
			Vector outerTan = Vector.multiplyByScaler(tan1, coeff5);
			Vector sumBernstein = Vector.add4Vectors(v01, v02, v33, v34);
			Vector dI = new Vector(coordinatesX[start + i], coordinatesY[start + i]);
			Vector dIminusBernstein = Vector.subtract(dI, sumBernstein);
			double oneTerm = dIminusBernstein.dot(outerTan);
			t += incr;
			sum += oneTerm;
		}
		
		x1 = sum;
		return sum;
	}
	
	public double calculateX2() {
		int start1 = (int) start;
		int end1 = (int) end;
		Vector tan2 = Vector.getTangent2(coordinatesX, coordinatesY, end);
		tangent2 = tan2;
		Vector v0 = new Vector(coordinatesX[start], coordinatesY[start]);
		Vector v3 = new Vector(coordinatesX[end], coordinatesY[end]);
		
		double n = end - start;
		double incr = 1/((double) n);
		double t = 0;
		double sum = 0;
		for(int i = 0; i < n; i++) {
			double coeff1 = Math.pow((1-t), 3);// Bernstein polynomial term
			double coeff2 = Math.pow((1-t), 2) * t * 3;// Bernstein polynomial term
			double coeff3 = Math.pow(t, 2) * (1 - t) * 3;// Bernstein polynomial term
			double coeff4 = Math.pow(t, 3);// Bernstein polynomial term
			double coeff5 = Math.pow(t, 2) * (1-t) * 3;//outer multiplier tan1 * coeff2
			Vector v01 = Vector.multiplyByScaler(v0, coeff1);
			Vector v02 = Vector.multiplyByScaler(v0, coeff2);
			Vector v33 = Vector.multiplyByScaler(v3, coeff3);
			Vector v34 = Vector.multiplyByScaler(v3, coeff4);
			Vector outerTan = Vector.multiplyByScaler(tan2, coeff5);
			Vector sumBernstein = Vector.add4Vectors(v01, v02, v33, v34);
			Vector dI = new Vector(coordinatesX[start + i], coordinatesY[start + i]);
			Vector dIminusBernstein = Vector.subtract(dI, sumBernstein);
			double oneTerm = dIminusBernstein.dot(outerTan);
			t += incr;
			sum += oneTerm;
		}
		
		x2 = sum;
		return sum;
	}
	
}
