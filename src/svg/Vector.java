package svg;

public class Vector {
	
	public int x;
	public int y;
	public double xD;
	public double yD;
	public Vector normalized;
	
	public Vector() {
		
	}
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
		this.xD = (double) x;
		this.yD = (double) y;
	}
	
	public Vector(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		this.xD = x;
		this.yD = y;
	}
	
	public double dot(Vector vec2) {
		double product = 0;
		product = ( this.xD * vec2.xD ) + ( this.yD  * vec2.yD );
//		System.out.println("( " + this.xD + ", "+  this.yD + " )" + " dot " + "( " + vec2.xD + ", "+  vec2.yD + " )" + " = " + product);
		return product;
	}
	
	public Vector normalize() {
		double len = Math.sqrt(this.xD * this.xD + this.yD * this.yD);
		normalized = new Vector();
		normalized.xD = this.xD / len;
		normalized.yD = this.yD / len;
		normalized.x =(int) normalized.xD;
		normalized.y = (int) normalized.yD;
//		System.out.println(String.format("vector (%d, %d) normalized = ("+  this.normalized.xD  + ", " +  this.normalized.yD +")", this.x, this.y));
		return normalized;
	}
	
	public static Vector getTangent1(double[] coordinatesX, double[] coordinatesY, int index) {//double //double
		Vector tg = new Vector(coordinatesX[index], coordinatesY[index]);

		double x = 0;
		double y = 0;
		for(int i = 0; i <= 5; i++) {
			x += ( coordinatesX[index + i] - tg.xD);//double
			y += (coordinatesY[index + i] - tg.yD);//double
		}
		tg.xD = x/5;
		tg.yD = y/5;
		tg.x =(int) (x)/5;
		tg.y =(int) (y)/5;
		tg = tg.normalize();
		return tg;
		
	}
	
	public static Vector getTangent2(double[] coordinatesX, double[] coordinatesY, int index) {//double//double
		Vector tg = new Vector(coordinatesX[index], coordinatesY[index]);

		double x = 0;
		double y = 0;
		for(int i = 0; i <= 5; i++) {
			x += (coordinatesX[index - i] - tg.xD);//double
			y += (coordinatesY[index - i] - tg.yD);//double
		}
		tg.xD = x/5;
		tg.yD = y/5;
		tg.x =(int) (x)/5;
		tg.y =(int) (y)/5;
		tg = tg.normalize();
		return tg;
		
	}
	
	public static Vector multiplyByScaler(Vector v, double scaler) {
		Vector vScaled = new Vector();
		vScaled.xD = v.xD * scaler;
		vScaled.yD = v.yD * scaler;
		vScaled.x = (int) vScaled.xD;
		vScaled.y = (int) vScaled.yD;
//		System.out.println("multiply by scaler ( " + v.xD + ", " + v.yD + " )" + " * " + scaler + " = " + "( " + vScaled.xD + ", " + vScaled.yD + " )");
		return vScaled;
	}
	
	public static Vector add(Vector v1, Vector v2) {
		double addVX = v1.xD + v2.xD;
		double addVY = v1.yD + v2.yD;
		Vector vAdd = new Vector(addVX, addVY);
		return vAdd;
	}
	
	public static Vector add4Vectors(Vector v1, Vector v2, Vector v3, Vector v4) {
		double addVX = v1.xD + v2.xD + v3.xD + v4.xD;
		double addVY = v1.yD + v2.yD + v3.yD + v4.yD;
		Vector vAdd = new Vector(addVX, addVY);
		return vAdd;
	}
	
	public static Vector add3Vectors(Vector v1, Vector v2, Vector v3) {
		double addVX = v1.xD + v2.xD + v3.xD;
		double addVY = v1.yD + v2.yD + v3.yD;
		Vector vAdd = new Vector(addVX, addVY);
		return vAdd;
	}
	

	
	public static Vector subtract(Vector v1, Vector v2) {
		double subtractVX = v1.xD - v2.xD;
		double subtractVY = v1.yD - v2.yD;
		Vector vSubtract = new Vector(subtractVX, subtractVY);
		return vSubtract;
	}
}