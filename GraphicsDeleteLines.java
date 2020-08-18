/********************************************************************************/
/*										*/
/*		GraphicsDeleteLines.java 					*/
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
/*	Command class for deleting lines 						*/
/*										*/
/********************************************************************************/
public class GraphicsDeleteLines implements GraphicsCommand {
	private Pane graphics_pane;
	private ArrayList<GraphicsCurvedLine> lines_list;
	private GraphicsCurvedLine graphics_line;

	public GraphicsDeleteLines(Pane graphicspane, ArrayList<GraphicsCurvedLine> lines, GraphicsCurvedLine line) {
		graphics_pane = graphicspane;
		lines_list = lines;
		graphics_line = line;
	}

	@Override
	public void undo() {
		graphics_pane.getChildren().add(graphics_line.getNode());
		lines_list.add(graphics_line);
	}

	@Override
	public void redo() {
		graphics_pane.getChildren().remove(graphics_line.getNode());
		lines_list.remove(graphics_line);
	}
} // end of class GraphicsDeleteLines

/* end of GraphicsDeleteLines.java */
