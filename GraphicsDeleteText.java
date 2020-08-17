/********************************************************************************/
/*										*/
/*		GraphicsDeleteText.java 					*/
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
/*	Command class for deleting text boxes 						*/
/*										*/
/********************************************************************************/
public class GraphicsDeleteText implements GraphicsCommand {
	private Pane graphics_pane;
	private ArrayList<GraphicsTextBox> texts_list;
	private GraphicsTextBox graphics_text;
	
	public GraphicsDeleteText(Pane graphicspane, ArrayList<GraphicsTextBox> texts, GraphicsTextBox text) {
		graphics_pane = graphicspane;
		texts_list = texts;
		graphics_text = text;
	}
	
	@Override
	public void undo() {
		graphics_pane.getChildren().add(graphics_text.getNode());
		texts_list.add(graphics_text);
	}
	
	@Override
	public void redo() {
		graphics_pane.getChildren().remove(graphics_text.getNode());
		texts_list.remove(graphics_text);
	}
} // end of class GraphicsDeleteText

/* end of GraphicsDeleteText.java */