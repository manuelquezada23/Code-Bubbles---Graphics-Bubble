/********************************************************************************/
/*										*/
/*		GraphicsShape.java 					*/
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

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/********************************************************************************/
/*										*/
/*	Interface for shapes (rectangles and ellipses)			*/
/*										*/
/********************************************************************************/
public interface GraphicsShape {
	public double rotateShape(Point2D prev, Point2D curr);
	public void moveShape(Point2D prev, Point2D curr);
	public void resizeShape(Point2D prev, Point2D curr);
	public Node getNode();
	public void setLocation(double x, double y);
	public void setFill(Color color);
	public Point2D getCenter();
	public Point2D rotatePoint(Point2D pointtorotate, Point2D rotatearound, double angle);
	public void setWidth(double x);
	public void setHeight(double y);
	public boolean contains(double x, double y);
	public void setStroke(Color color);
	public void setStrokeWidth(double size);
	public Color getFill();
	public double getWidth();
	public double getHeight();
	public boolean isFocused();
} // end of interface GraphicsShape

/* end of GraphicsShape.java */