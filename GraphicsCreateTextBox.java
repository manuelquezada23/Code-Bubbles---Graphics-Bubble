/********************************************************************************/
/*										*/
/*		GraphicsCreateTextBox.java 					*/
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
/*	Command class for creating textboxes 						*/
/*										*/
/********************************************************************************/
public class GraphicsCreateTextBox implements GraphicsCommand {
	private GraphicsTextBox text_box;
	private Pane graphics_pane;
	private ArrayList<GraphicsTextBox> text_boxes_list;
		
	public GraphicsCreateTextBox(Pane graphicspane, ArrayList<GraphicsTextBox> textarray, GraphicsTextBox textbox)  {
		graphics_pane = graphicspane;
		text_boxes_list = textarray;
		text_box = textbox;

	}

	@Override
	public void undo() {
		text_boxes_list.remove(text_box);
		graphics_pane.getChildren().remove(text_box.getNode());
	}

	@Override
	public void redo() {
		text_boxes_list.add(text_box);
		graphics_pane.getChildren().add(text_box.getNode());
	}
} // end of class GraphicsCreateTextBox

/* end of GraphicsCreateTextBox.java */