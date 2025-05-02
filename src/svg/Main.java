package svg;

import java.io.*;
import java.util.Arrays;

import statistics.*;


public class Main {

	public static void main(String[] args) {
		
		String sep = File.separator;
		String folder = "C:" + sep + "Users" + sep + "Toms" + sep + "Desktop" + sep + "ImageEXPERIMENTS";
		
		String svg =  """
	            <?xml version="1.0" encoding="UTF-8"?>
	            <svg width="1200" height="1000" xmlns="http://www.w3.org/2000/svg">
	              <path d="M 0,472 C 171.25,300.75 250.20,513.60 415,596M 415,596 C 421.67,596.00 428.33,599.00 435,599M 435,599 C 436.00,599.00 435.00,599.00 436,599M 436,599 C 437.00,599.00 436.00,599.00 437,599M 437,599 C 438.00,599.00 437.00,599.00 438,599M 438,599 C 439.00,599.00 439.00,599.00 440,599M 440,599 C 441.00,599.00 440.00,599.00 441,599M 441,599 C 442.00,599.00 441.00,599.00 442,599M 442,599 C 443.00,599.00 442.00,599.00 443,599M 443,599 C 444.00,599.00 443.00,599.00 444,599M 444,599 C 445.00,599.00 444.00,599.00 445,599M 445,599 C 446.00,599.00 446.00,599.00 447,599M 447,599 C 448.00,599.00 447.00,599.00 448,599M 448,599 C 449.00,599.00 448.00,599.00 449,599M 449,599 C 450.00,599.00 449.00,599.00 450,599M 450,599 C 451.00,599.00 451.00,599.00 452,599M 452,599 C 453.00,599.00 452.00,599.00 453,599M 453,599 C 454.00,599.00 453.00,599.00 454,599M 454,599 C 455.00,599.00 454.00,599.00 455,599M 455,599 C 458.13,598.48 455.04,599.48 458,598M 458,598 C 459.00,598.00 458.05,598.32 459,598M 459,598 C 460.00,598.00 460.00,598.00 461,598M 461,598 C 461.95,597.68 461.00,598.00 462,598M 462,598 C 462.89,597.55 462.11,597.45 463,597M 463,597 C 464.00,597.00 464.01,597.16 465,597M 465,597 C 468.13,596.48 465.04,597.48 468,596M 468,596 C 469.00,596.00 468.05,596.32 469,596M 469,596 C 469.99,595.84 469.01,596.16 470,596M 470,596 C 471.96,595.35 472.95,595.03 475,594M 475,594 C 478.13,593.48 475.04,594.48 478,593M 478,593 C 479.39,592.77 480.58,592.47 482,592M 482,592 C 482.95,591.68 482.01,592.16 483,592M 483,592 C 487.03,589.32 490.79,588.81 495,586M 495,586 C 501.65,583.78 508.42,578.29 515,575M 515,575 C 518.35,572.21 520.68,571.32 524,568M 524,568 C 530.80,564.60 536.90,556.55 544,553M 544,553 C 572.27,524.73 603.55,496.45 633,467M 633,467 C 857.82,242.18 969.69,757.31 1199,528" stroke="black" fill="none" stroke-width="2"/>
	            </svg>
	            """;
		
		try {
			FileWriter w = new FileWriter(folder + sep + "line27.svg");
			w.write(svg);
			w.close();
			System.out.println(w.getEncoding());
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		


		Sine s = new Sine(1,1);
		GaussDistribution gd = new GaussDistribution(1, 0);
//		System.out.println(Arrays.toString(gd.xC) + "\n" + Arrays.toString(gd.yC));
		System.out.println(Arrays.toString(s.xC) + "\n" + Arrays.toString(s.yC));
//		s.createValues();
		GenerateXML xml = new GenerateXML(s);
//		xml.createXML();
		xml.createXML1(s, 0, s.yC.length - 1);
		System.out.println(xml.svg);


		ControlPoint cp = new ControlPoint(s.xC, s.yC, 0, 314);
		ControlPoint cp1 = new ControlPoint(s.xC, s.yC, 314, 628);
		System.out.println("control V1 point x = " + cp.v1.xD + " y = " + cp.v1.yD);
		System.out.println("control V2 point x = " + cp.v2.xD + " y = " + cp.v2.yD);
		System.out.println("control V1 point x = " + cp1.v1.xD + " y = " + cp1.v1.yD);
		System.out.println("control V2 point x = " + cp1.v2.xD + " y = " + cp1.v2.yD);
		

	}

}
