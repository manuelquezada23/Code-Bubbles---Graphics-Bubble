/********************************************************************************/
/*										*/
/*		GraphicsImagesResize.java 					*/
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

/********************************************************************************/
/*										*/
/*	Command class for resizing images 						*/
/*										*/
/********************************************************************************/
public class GraphicsImagesResize implements GraphicsCommand {
	@SuppressWarnings("unused")
	private double current_x;
	@SuppressWarnings("unused")
	private double current_y;
	private double shape_width;
	private double shape_height;
	private GraphicsImages graphics_shape;
	private double old_x;
	private double old_y;
	private double old_height;
	private double old_width;

	public GraphicsImagesResize(double x, double y, double width, double height, GraphicsImages shape) {
		current_x = x;
		current_y = y;
		shape_width = width;
		shape_height = height;
		graphics_shape = shape;
	}

	@Override
	public void undo() {
		old_x = graphics_shape.getCenter().getX();
		old_y = graphics_shape.getCenter().getY();
		old_height = graphics_shape.getHeight();
		old_width = graphics_shape.getWidth();
		graphics_shape.setLocation(old_x, old_y);
		graphics_shape.setHeight(shape_height);
		graphics_shape.setWidth(shape_width);
	}

	@Override
	public void redo() {
		graphics_shape.setWidth(old_width);
		graphics_shape.setHeight(old_height);
		graphics_shape.setLocation(old_x, old_y);
	}
} // end of class GraphicsImagesResize

/* end of GraphicsImagesResize.java */
