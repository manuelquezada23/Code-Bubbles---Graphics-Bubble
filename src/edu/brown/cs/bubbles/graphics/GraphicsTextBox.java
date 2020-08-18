/********************************************************************************/
/*										*/
/*		GraphicsTextBox.java 					*/
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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/********************************************************************************/
/*										*/
/*	Creation of text boxes 						*/
/*										*/
/********************************************************************************/
public class GraphicsTextBox {
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
	private TextArea text_area;
	private boolean area_selected;
	private Pane graphics_pane;
	
	public GraphicsTextBox(double x, double y, Color color, Pane graphicsPane) {
		graphics_pane = graphicsPane;
		text_area = new TextArea();
		text_area.setWrapText(true);
		enableDragging(text_area);
		text_area.setMouseTransparent(false);
		area_selected = false;
		
		text_area.setMinWidth(50);
		text_area.setPrefWidth(50);
		text_area.setMaxWidth(150);
		
		text_area.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				text_area.setPrefWidth(text_area.getText().length() * 5); // why 7? Totally trial number.
				
			}
		});
		
		SimpleIntegerProperty count = new SimpleIntegerProperty(50);
		int rowHeight = 1;
		
		text_area.prefHeightProperty().bindBidirectional(count);
		text_area.minHeightProperty().bindBidirectional(count);
		text_area.scrollTopProperty().addListener((ov, oldVal, newVal) -> {
		    if(newVal.intValue() > rowHeight){
		        count.setValue(count.get() + newVal.intValue());
		    }
		});
	}	
	
	public void moveText(Point2D prev, Point2D curr) {
		Double xLoc = prev.getX() - this.getXLoc();
		Double yLoc = prev.getY() - this.getYLoc();
		Double dx = curr.getX() - xLoc;
		Double dy = curr.getY() - yLoc;
		this.setLocation(dx, dy);
	}

	
	public void setStyle(String string) {
		text_area.setStyle(string);
	}
	
	public void textContains(double x, double y) {
		text_area.contains(x, y);
	}
	
	public String getStyle() {
		return text_area.getStyle();
	}
	
	public boolean contains(double x, double y) {
		if (text_area.contains(x, y)) {
			text_area.requestFocus();
			return true;
		}  else {
			graphics_pane.requestFocus();
			return false;
		}
	}
	
	public boolean getAreaSelected() {
		return area_selected;
	}
	
	public boolean isFocused() {
		System.out.println(text_area);
		if (text_area != null) {
			return text_area.isFocused();
		} else {
			return false;
		}
	}
	
	public boolean paneIsFocused() {
		return graphics_pane.isFocused();
	}
	
	public void enableDragging(Node node) {
		final ObjectProperty<Point2D> mouse = new SimpleObjectProperty<>();
		System.out.println(mouse);
		node.setOnMousePressed( event -> mouse.set(new Point2D(event.getSceneX(), event.getSceneY())));
		node.setOnMouseDragged( event -> {
			double x = event.getSceneX() - mouse.get().getX();
			double y = event.getSceneY() - mouse.get().getY();
			node.relocate(node.getLayoutX() + x, node.getLayoutY() + y);
			mouse.set(new Point2D(event.getSceneX(), event.getSceneY()));;
		});
	}
	
	public void setXLoc(double x) { 
		text_area.setLayoutX(x);
	}
	
	public void setYLoc(double y) {
		text_area.setLayoutY(y);
	}
	
	public double getXLoc() { 
		return text_area.getLayoutX();
	}
	
	public double getYLoc() { 
		 return text_area.getLayoutY();
	}
	
	public double rotateShape(Point2D prev, Point2D curr) {
		shape_rotate = Math.atan2(prev.getY() - this.getCenter().getY(), prev.getX() - this.getCenter().getX()) - Math.atan2(curr.getY() - this.getCenter().getY(), curr.getX() - this.getCenter().getX());
		return Math.toDegrees(- shape_rotate);
	}
	
	public void moveShape(Point2D prev, Point2D curr) {
		Double xLoc = prev.getX() - this.getXLoc();
		Double yLoc = prev.getY() - this.getYLoc();
		Double dx = curr.getX() - xLoc;
		Double dy = curr.getY() - yLoc;
		this.setLocation(dx, dy);
	}
	
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
	
	public Node getNode() {
		return text_area;
	}
	
	public IndexRange getSelection() {
		return text_area.getSelection();
	}
	
	public void setLocation(double x, double y) {
		this.setXLoc(x);
		this.setYLoc(y);
	}

	public void setFill(Color color) {
		shape_rectangle.setFill(color);
	}
	
	public Point2D getCenter() {
		Point2D point = new Point2D(shape_rectangle.getX() + shape_rectangle.getWidth()/2, shape_rectangle.getY() + shape_rectangle.getHeight()/2);
		return point;
		
	}

	public Point2D rotatePoint(Point2D pointtorotate, Point2D rotatearound, double angle) {
		double sine = Math.sin(Math.toRadians(angle));
		double cosine = Math.cos(Math.toRadians(angle));
		shape_point = new Point2D(pointtorotate.getX() - rotatearound.getX(), pointtorotate.getY() - rotatearound.getY());
		shape_point = new Point2D(shape_point.getX()*cosine + shape_point.getY()*sine, -shape_point.getX()*sine + shape_point.getY()*cosine);
		shape_point = new Point2D(shape_point.getX() + rotatearound.getX(), shape_point.getY() + rotatearound.getY());
		return shape_point;
	}
	
	public void setWidth(double x) {
		shape_rectangle.setWidth(x);
	}
	
	public void setHeight(double y) {
		shape_rectangle.setHeight(y);
	}
	
	public boolean containsTextBox() {
		if (text_area.isPressed()) {
			return true;
		} 
		return false;
	}
	
	public Color getFill() {
		return (Color) shape_rectangle.getFill();
	}

	public double getWidth() {
		return shape_rectangle.getWidth();
	}

	public double getHeight() {
		return shape_rectangle.getHeight();
	}
} // end of class GraphicsTextBox

/* end of GraphicsTextBox.java */