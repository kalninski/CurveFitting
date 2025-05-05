package svg;

import java.util.*;
import java.lang.*;
import statistics.*;


public class GenerateXML {
	
	public Function f;
	public double[] functionArrX;
	public double[] functionArrY;
	public double[] errorArr;
	public int start;
	public int end;
//	public StringBuilder svg = new StringBuilder("""
//												<?xml version="1.0" encoding="UTF-8"?>
//            									<svg width="1200" height="1000" xmlns="http://www.w3.org/2000/svg">
//              									<path d=\"""");
	StringBuilder svg = new StringBuilder();
	public String restXML =  "\" stroke=\"black\" fill=\"none\" stroke-width=\"2\"/></svg>";
							
			        

	
	public GenerateXML(Function f) {
		this.f = f;
		this.functionArrY = f.yActualVal;
		this.functionArrX = f.xActualVal;
		
	}
	
	public String createXML() {
		
		int start = 0;
		int end = 300;
		String fourPoints = "M %d,%d C %.2f,%.2f %.2f,%.2f %d,%d";
		int size = functionArrY.length;
		while(end <= size) {
			if((end + 300) >= (functionArrY.length - 1)) {
				end = functionArrY.length - 1;
			}
			ControlPoint cp = new ControlPoint(functionArrX, functionArrY, start, end);
			String points = String.format(Locale.US,fourPoints, functionArrX[start], functionArrY[start], cp.v1.xD, cp.v1.yD, cp.v2.xD, cp.v2.yD, functionArrX[end], functionArrY[end]);
			svg.append(points);
			start = end;
			end += 300;
			
		}
		svg.append(restXML);
//		System.out.println(svg);
		
		return svg.toString();
	}
	
	public void createXML1(Function f, int start, int end) {
		ControlPoint cp = new ControlPoint(f.xActualVal, f.yActualVal, start, end);
		cp.getValuesOfCurve();
		String points;
		int e = cp.getErrorIndex(f);

		points = String.format(Locale.US,"M %.2f,%.2f C %.2f,%.2f %.2f,%.2f %.2f,%.2f",  functionArrX[start], functionArrY[start], cp.v1.xD, cp.v1.yD, cp.v2.xD, cp.v2.yD, functionArrX[end], functionArrY[end]);
		System.out.println("RECURSIVE CALL!");
		if(cp.maxError > 5 && (end - start - e) > 15 && e > 5) {
			createXML1(f, start, start + e);
			System.out.println("left size = " + e);
			createXML1(f, start + e, end);
			System.out.println("right size = " + (end - (start + e)));
		}else {
			if(cp.v1.xD == Double.NaN || cp.v1.yD == Double.NaN || cp.v2.xD == Double.NaN || cp.v2.yD == Double.NaN){
				System.out.println("one or more of the points was NaN");
				points = String.format(Locale.US,"M %.2f,%.2f L %.2f,%.2f",  functionArrX[start], functionArrY[start], functionArrX[end], functionArrY[end]);
			}
			svg.append(points);
			System.out.println(points);
		}
	}
}