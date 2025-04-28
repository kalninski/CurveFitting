package svg;

import java.io.*;
import java.util.Arrays;

import statistics.*;


public class Main {

	public static void main(String[] args) {
		
		String sep = File.separator;
		String folder = "/Users/maksla/Desktop";
		
		String svg = """
	            <?xml version="1.0" encoding="UTF-8"?>
	            <svg width="2000" height="2000" xmlns="http://www.w3.org/2000/svg">
	              <path d="M 443,599 C 547.66,599 652.3333,401 757,401" stroke="black" fill="none" stroke-width="2"/>
	            </svg>
	            """;
		
		try {
			FileWriter w = new FileWriter(folder + sep + "line12.svg");
			w.write(svg);
			w.close();
			System.out.println(w.getEncoding());
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		ControlPoint c = new ControlPoint(0, -7);

		c.normalizeTangent();
		System.out.println("x = " + c.x + " y = " + c.y + " xNormD = " + c.xNormD + " yNormD = " + c.yNormD + " xNormInt = " + c.xNormInt + " yNormInt =" + c.yNormInt);
		Sine s = new Sine(1,1);
		s.createValues();
		
		Vector t1 = new Vector(549, 401);
		Vector t2 = new Vector(15, 2);
		t1.dot(t2);
		Vector t1N = t1.normalize();
		t1N.dot(t1N);
		MatrixC m = new MatrixC(15.3, 2, 11.2, 3);
		m.calculateDeterminant();
		System.out.println("Tangent :");
		int[] x = new int[] {754, 755, 756, 757};
		int[] y = new int[] {401, 401, 401, 401};
		Vector.getTangent1(x, y, 0);
		System.out.println("above");
		Vector.multiplyByScaler(t1, 2);
		ControlPoint cp = new ControlPoint(s.xC, s.yC, 0, 314);
//		ControlPoint cp1 = new ControlPoint(s.xC, s.yC, 314, 628);
		System.out.println("control V1 point x = " + cp.v1.xD + " y = " + cp.v1.yD);
//		System.out.println("control V2 point x = " + cp.v2.xD + " y = " + cp.v2.yD);
		System.out.println("sine x = " + Arrays.toString(s.xC) + "\n sine y = " + Arrays.toString(s.yC));
		

	}

}
