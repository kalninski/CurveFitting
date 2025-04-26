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
		System.out.println(String.format("vector (%d, %d) normalized = ("+  this.normalized.xD  + ", " +  this.normalized.yD +")", this.x, this.y));
		return normalized;
	}
	
	public static Vector getTangent1(int[] coordinatesX, int[] coordinatesY, int index) {
		Vector tg = new Vector(coordinatesX[index], coordinatesY[index]);
		double x1 = ((double) coordinatesX[index + 1])  - tg.xD;
		double y1 = ((double) coordinatesY[index + 1])  - tg.yD;
		double x2 = ((double) coordinatesX[index + 2])  - tg.xD;
		double y2 = ((double) coordinatesY[index + 2])  - tg.yD;
		double x3 = ((double) coordinatesX[index + 3])  - tg.xD;
		double y3 = ((double) coordinatesY[index + 3])  - tg.yD;
		tg.xD = (x1 + x2 + x3)/3;
		tg.yD = (y1 + y2 + y3)/3;
		tg.x =(int) (x1 + x2 + x3)/3;
		tg.y =(int) (y1 + y2 + y3)/3;
		tg = tg.normalize();
		return tg;
		
	}
	
	public static Vector getTangent2(int[] coordinatesX, int[] coordinatesY, int index) {
		Vector tg = new Vector(coordinatesX[index], coordinatesY[index]);
		double x1 = ((double) coordinatesX[index - 1]) - tg.xD;
		double y1 = ((double) coordinatesY[index - 1]) - tg.yD;
		double x2 = ((double) coordinatesX[index - 2]) - tg.xD;
		double y2 = ((double) coordinatesY[index - 2]) - tg.yD;
		double x3 = ((double) coordinatesX[index - 3]) - tg.xD;
		double y3 = ((double) coordinatesY[index - 3]) - tg.yD;
//		double x4 = tg.xD - ((double) coordinatesY[index + 4]);
//		double y4 = tg.yD - ((double) coordinatesY[index + 4]);
//		double x5 = tg.xD - ((double) coordinatesY[index + 5]);
//		double y5 = tg.yD - ((double) coordinatesY[index + 5]);
//		double x6 = tg.xD - ((double) coordinatesX[index + 6]);
//		double y6 = tg.yD - ((double) coordinatesY[index + 6]) ;
		tg.xD = (x1 + x2 + x3)/3;
		tg.yD = (y1 + y2 + y3)/3;
		tg.x =(int) (x1 + x2 + x3)/3;
		tg.y =(int) (y1 + y2 + y3)/3;
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
	
	public static Vector subtract(Vector v1, Vector v2) {
		double subtractVX = v1.xD - v2.xD;
		double subtractVY = v1.yD - v2.yD;
		Vector vSubtract = new Vector(subtractVX, subtractVY);
		return vSubtract;
	}
}
