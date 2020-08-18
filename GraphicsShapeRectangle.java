/********************************************************************************/
/*										*/
/*		GraphicsShapeRectangle.java 					*/
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/********************************************************************************/
/*										*/
/*	Creation of rectangles 						*/
/*										*/
/********************************************************************************/
public class GraphicsShapeRectangle implements GraphicsShape {
	private Rectangle shape_rectangle;
	private Double double_rotation;
	private Point2D old_center;
	private Point2D rotated_prev;
	private Point2D rotated_curr;
	private Point2D shape_point;
	private Point2D new_center;
	@SuppressWarnings("unused")
	private Color shape_color;
	private double shape_rotate;
	private Pane graphics_pane;


	public GraphicsShapeRectangle(double x, double y, double width, double height, Color color, Pane graphicspane) {
		shape_rectangle = new Rectangle(50, 30);
		shape_color = color;
		graphics_pane = graphicspane;
	}

	public void setXLoc(double x) {
		shape_rectangle.setX(x);
	}

	public void setYLoc(double y) {
		shape_rectangle.setY(y);
	}

	public double getXLoc() {
		return shape_rectangle.getX();
	}

	public double getYLoc() {
		 return shape_rectangle.getY();
	}

	@Override
	public double rotateShape(Point2D prev, Point2D curr) {
		shape_rotate = Math.atan2(prev.getY() - this.getCenter().getY(), prev.getX() - this.getCenter().getX()) - Math.atan2(curr.getY() - this.getCenter().getY(), curr.getX() - this.getCenter().getX());
		return Math.toDegrees(- shape_rotate);
	}

	@Override
	public void moveShape(Point2D prev, Point2D curr) {
		Double xLoc = prev.getX() - this.getXLoc();
		Double yLoc = prev.getY() - this.getYLoc();
		Double dx = curr.getX() - xLoc;
		Double dy = curr.getY() - yLoc;
		this.setLocation(dx, dy);
	}

	@Override
	public void resizeShape(Point2D prev, Point2D curr) {
		double_rotation = shape_rectangle.getRotate();
		old_center = this.getCenter();
		rotated_prev = this.rotatePoint(prev, old_center, double_rotation);
		rotated_curr = this.rotatePoint(curr, old_center, double_rotation);

		double dx = Math.abs(rotated_curr.getX() - rotated_prev.getX());
		double dy = Math.abs(rotated_curr.getY() - rotated_prev.getY());

		if (Math.abs(this.getCenter().getX() - rotated_prev.getX()) > Math.abs(this.getCenter().getX() - rotated_curr.getX())) {
			dx = -1 * dx;
		}
		if (Math.abs(this.getCenter().getY() - rotated_prev.getY()) > Math.abs(this.getCenter().getY() - rotated_curr.getY())) {
			dy = -1 * dy;
		}

		shape_rectangle.setWidth(shape_rectangle.getWidth() + 2 * dx);
		shape_rectangle.setHeight(shape_rectangle.getHeight() + 2 * dy);

		new_center = this.getCenter();
		if (!(new_center == old_center)) {
			double centerdx = old_center.getX() - new_center.getX();
			double centerdy = old_center.getY() - new_center.getY();
			this.setLocation(this.getXLoc() + centerdx, this.getYLoc() + centerdy);
		}
	}

	@Override
	public Node getNode() {
		return shape_rectangle;
	}

	@Override
	public void setLocation(double x, double y) {
		this.setXLoc(x);
		this.setYLoc(y);
	}

	@Override
	public void setFill(Color color) {
		shape_rectangle.setFill(color);
	}

	@Override
	public Point2D getCenter() {
		Point2D point = new Point2D(shape_rectangle.getX() + shape_rectangle.getWidth()/2, shape_rectangle.getY() + shape_rectangle.getHeight()/2);
		return point;

	}

	@Override
	public Point2D rotatePoint(Point2D pointtorotate, Point2D rotatearound, double angle) {
		double sine = Math.sin(Math.toRadians(angle));
		double cosine = Math.cos(Math.toRadians(angle));
		shape_point = new Point2D(pointtorotate.getX() - rotatearound.getX(), pointtorotate.getY() - rotatearound.getY());
		shape_point = new Point2D(shape_point.getX()*cosine + shape_point.getY()*sine, -shape_point.getX()*sine + shape_point.getY()*cosine);
		shape_point = new Point2D(shape_point.getX() + rotatearound.getX(), shape_point.getY() + rotatearound.getY());
		return shape_point;
	}

	@Override
	public void setWidth(double x) {
		shape_rectangle.setWidth(x);
	}

	@Override
	public void setHeight(double y) {
		shape_rectangle.setHeight(y);
	}

	@Override
	public boolean isFocused() {
		if (shape_rectangle != null) {
			return shape_rectangle.isFocused();
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(double x, double y) {
		if (shape_rectangle.contains(x, y)) {
			shape_rectangle.requestFocus();
			return true;
		} else {
			graphics_pane.requestFocus();
			return false;
		}
	}

	@Override
	public void setStroke(Color color) {
		shape_rectangle.setStroke(color);
	}

	@Override
	public void setStrokeWidth(double size) {
		shape_rectangle.setStrokeWidth(size);
	}

	@Override
	public Color getFill() {
		return (Color) shape_rectangle.getFill();
	}

	@Override
	public double getWidth() {
		return shape_rectangle.getWidth();
	}

	@Override
	public double getHeight() {
		return shape_rectangle.getHeight();
	}
} // end of class GraphicsShapeRectangle

/* end of GraphicsShapeRectangle.java */
