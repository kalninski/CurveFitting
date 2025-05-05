package svg;

import java.io.*;
import java.util.Arrays;

import statistics.*;


public class Main {

	public static void main(String[] args) {
		
		String sep = File.separator;
		String folder = "C:" + sep + "Users" + sep + "Toms" + sep + "Desktop" + sep + "ImageEXPERIMENTS";
		

		


		Sine s = new Sine(1,1);
		GaussDistribution gd = new GaussDistribution(0.5, 0);
//		System.out.println(Arrays.toString(gd.xC) + "\n" + Arrays.toString(gd.yC));
		System.out.println(Arrays.toString(s.xActualVal) + "\n" + Arrays.toString(s.yActualVal));
//		s.createValues();
		GenerateXML xml = new GenerateXML(gd);
//		xml.createXML();
		xml.createXML1(gd, 0, gd.yActualVal.length - 1);
		System.out.println(xml.svg);


		ControlPoint cp = new ControlPoint(gd.xActualVal, gd.yActualVal, 0, 314);
		ControlPoint cp1 = new ControlPoint(gd.xActualVal, gd.yActualVal, 314, 628);
		System.out.println("control V1 point x = " + cp.v1.xD + " y = " + cp.v1.yD);
		System.out.println("control V2 point x = " + cp.v2.xD + " y = " + cp.v2.yD);
		System.out.println("control V1 point x = " + cp1.v1.xD + " y = " + cp1.v1.yD);
		System.out.println("control V2 point x = " + cp1.v2.xD + " y = " + cp1.v2.yD);
		
		String svg1 =  """
	            <?xml version="1.0" encoding="UTF-8"?>
	            <svg width="1200" height="1000" xmlns="http://www.w3.org/2000/svg">
	              <path d="%s" stroke="black" fill="none" stroke-width="2"/>
	            </svg>
	            """;
		String svg = String.format(svg1, xml.svg);
		
		try {
			FileWriter w = new FileWriter(folder + sep + "line58.svg");
			w.write(svg);
			w.close();
			System.out.println(w.getEncoding());
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		

	}

}
