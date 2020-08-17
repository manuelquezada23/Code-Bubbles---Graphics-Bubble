/********************************************************************************/
/*										*/
/*		GraphicsCurvedLine.java 					*/
/*										*/
/*	Bubbles Environment Auxillary & Missing items feedback report		*/
/*										*/
/*********************************************************************************/
/*	Copyright 2020 Brown University -- Manuel Quezada			*/
/*********************************************************************************
 *  Copyright 2020, Brown University, Providence, RI.				 *
 *										 *
 *			  All Rights Reserved					 *
 *										 *
 * This program and the accompanying materials are made available under the	 *
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, *
 * and is available at								 *
 *	http://www.eclipse.org/legal/epl-v10.html				 *
 *										 *
 ********************************************************************************/

package edu.brown.cs.bubbles.graphics;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/********************************************************************************/
/*										*/
/*	Creation of curved lines used when drawing lines 			*/
/*										*/
/********************************************************************************/
public class GraphicsCurvedLine {
	private Polyline poly_line;
	@SuppressWarnings("unused")
	private Pane graphics_pane;
	@SuppressWarnings("unused")
	private Double[] double_points;
	private Color line_color;
	
	
	public GraphicsCurvedLine(Pane graphicspane) {
		poly_line = new Polyline();
		graphics_pane = graphicspane;
	} 
	
	public void addPoint(Double[] points) {
		double_points = points;
		poly_line.getPoints().addAll(points);
	}
	
	public Node getLine() {
		return poly_line;
	}
	
	public void setColor(Color color) {
		line_color = color;
		poly_line.setStroke(line_color);
	}
} // end of class GraphicsCurvedLine

/* end of GraphicsCurvedLine.java */