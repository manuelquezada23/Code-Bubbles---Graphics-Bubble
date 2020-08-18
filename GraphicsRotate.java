/********************************************************************************/
/*										*/
/*		GraphicsRotate.java 					*/
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

/********************************************************************************/
/*										*/
/*	Command class for rotating shapes 						*/
/*										*/
/********************************************************************************/
public class GraphicsRotate implements GraphicsCommand {
	private GraphicsShape graphics_shape;
	private Point2D prev_point;
	private Point2D curr_point;

	public GraphicsRotate(GraphicsShape shape, Point2D prev, Point2D curr) {
		graphics_shape = shape;
		prev_point = prev;
		curr_point = curr;
	}

	@Override
	public void undo() {
		graphics_shape.getNode().setRotate(graphics_shape.rotateShape(prev_point, curr_point));
	}

	@Override
	public void redo() {
		graphics_shape.getNode().setRotate(graphics_shape.rotateShape(curr_point, prev_point));
	}
} // end of class GraphicsRotate

/* end of GraphicsRotate.java */
