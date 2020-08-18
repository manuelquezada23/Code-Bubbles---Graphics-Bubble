/********************************************************************************/
/*										*/
/*		GraphicsDeleteImages.java 					*/
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
import javafx.scene.layout.Pane;

/********************************************************************************/
/*										*/
/*	Command class for deleting shapes 						*/
/*										*/
/********************************************************************************/
public class GraphicsDeleteImages implements GraphicsCommand {
	private Pane graphics_pane;
	private ArrayList<GraphicsImages> images_list;
	private GraphicsImages graphics_image;

	public GraphicsDeleteImages(Pane graphicspane, ArrayList<GraphicsImages> images, GraphicsImages image) {
		graphics_pane = graphicspane;
		images_list = images;
		graphics_image = image;
	}

	@Override
	public void undo() {
		graphics_pane.getChildren().add(graphics_image.getNode());
		images_list.add(graphics_image);
	}

	@Override
	public void redo() {
		graphics_pane.getChildren().remove(graphics_image.getNode());
		images_list.remove(graphics_image);
	}
} // end of class GraphicsDeleteShape

/* end of GraphicsDeleteShape.java */
