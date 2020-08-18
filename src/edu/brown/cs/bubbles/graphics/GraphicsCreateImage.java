/********************************************************************************/
/*										*/
/*		GraphicsCreateImage.java 					*/
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
/*	Command class for creating images 						*/
/*										*/
/********************************************************************************/
public class GraphicsCreateImage implements GraphicsCommand {
	private GraphicsImages graphics_image;
	private Pane graphics_pane;
	private ArrayList<GraphicsImages> images_list;

	public GraphicsCreateImage(Pane graphicspane, ArrayList<GraphicsImages> images, GraphicsImages image)  {
		graphics_pane = graphicspane;
		images_list = images;
		graphics_image = image;
	}

	@Override
	public void undo() {
		images_list.remove(graphics_image);
		graphics_pane.getChildren().remove(graphics_image.getNode());
	}

	@Override
	public void redo() {
		images_list.add(graphics_image);
		graphics_pane.getChildren().add(graphics_image.getNode());
	}
} // end of class GraphicsCreateImage

/* end of GraphicsCreateImage.java */
