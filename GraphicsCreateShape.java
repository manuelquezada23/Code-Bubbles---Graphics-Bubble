/********************************************************************************/
/*										*/
/*		GraphicsCreateShape.java 					*/
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

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/********************************************************************************/
/*										*/
/*	Command class for creating shapes 						*/
/*										*/
/********************************************************************************/
public class GraphicsCreateShape implements GraphicsCommand {
	private Pane graphics_pane;
	private ArrayList<GraphicsShape> shapes_list;
	private GraphicsShape graphics_shape;

	public GraphicsCreateShape(Pane graphicsPane, ArrayList<GraphicsShape> shapes, GraphicsShape shape) {
		graphics_pane = graphicsPane;
		shapes_list = shapes;
		graphics_shape = shape;
	}

	public Node getNode() {
		return graphics_shape.getNode();
	}

	@Override
	public void undo() {
		shapes_list.remove(graphics_shape);
		graphics_pane.getChildren().remove(graphics_shape.getNode());
	}

	@Override
	public void redo() {
		shapes_list.add(graphics_shape);
		graphics_pane.getChildren().add(graphics_shape.getNode());

	}
} // end of class GraphicsCreateShape

/* end of GraphicsCreateShape.java */
