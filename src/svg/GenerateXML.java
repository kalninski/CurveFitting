package svg;

import java.util.*;
import java.lang.*;
import statistics.*;


public class GenerateXML {
	
	public Function f;
	public int[] functionArrX;
	public int[] functionArrY;
	public double[] errorArr;
	public int start;
	public int end;
	public StringBuilder svg = new StringBuilder("""
												<?xml version="1.0" encoding="UTF-8"?>
            									<svg width="1200" height="1000" xmlns="http://www.w3.org/2000/svg">
              									<path d=\"""");
	public String restXML =  "\" stroke=\"black\" fill=\"none\" stroke-width=\"2\"/>"
							+ "</svg>";
			        

	
	public GenerateXML(Function f) {
		this.f = f;
		this.functionArrY = f.yC;
		this.functionArrX = f.xC;
		
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
		ControlPoint cp = new ControlPoint(f.xC, f.yC, start, end);
		cp.getValuesOfCurve();
		String points;
		int e = cp.getErrorIndex(f);

			points = String.format(Locale.US,"M %d,%d C %.2f,%.2f %.2f,%.2f %d,%d",  functionArrX[start], functionArrY[start], cp.v1.xD, cp.v1.yD, cp.v2.xD, cp.v2.yD, functionArrX[end], functionArrY[end]);

		if(cp.maxError > 30 && (end - start) > 20 && e > 3) {
			createXML1(f, start, start + e);
			createXML1(f, start + e, end);
		}
		if(cp.maxError <= 30 || (end - start) <= 20 || (e <= 3 && e > 1)){
			svg.append(points);
			System.out.println(points);
		}
	}
}
