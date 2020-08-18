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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
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
		poly_line.setStrokeWidth(2);
		enableDragging(poly_line);
	}

	public void enableDragging(Node node) {
		final ObjectProperty<Point2D> mouse = new SimpleObjectProperty<>();
		poly_line.setOnMousePressed( event -> mouse.set(new Point2D(event.getSceneX(), event.getSceneY())));
		poly_line.setOnMouseDragged( event -> {
			double x = event.getSceneX() - mouse.get().getX();
			double y = event.getSceneY() - mouse.get().getY();
			poly_line.setLayoutX(node.getLayoutX() + x);
			poly_line.setLayoutY(node.getLayoutY() + y);
			mouse.set(new Point2D(event.getSceneX(), event.getSceneY()));;
		});
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

	public void setSelected() {
		poly_line.getStrokeDashArray().add(5d);
	}

	public void setUnselected() {
		poly_line.getStrokeDashArray().clear();
	}

	public Node getNode() {
		return poly_line;
	}

	public Color getFill() {
		return (Color) poly_line.getStroke();
	}

	public void setFill(Color color) {
		poly_line.setStroke(color);
	}

	public boolean contains(double x, double y) {
		if (poly_line.isPressed() ) {
			return true;
		}
		return false;
	}
} // end of class GraphicsCurvedLine

/* end of GraphicsCurvedLine.java */
