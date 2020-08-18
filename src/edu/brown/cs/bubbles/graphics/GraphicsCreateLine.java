/********************************************************************************/
/*										*/
/*		GraphicsCreateLine.java 					*/
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

import javafx.scene.layout.Pane;

/********************************************************************************/
/*										*/
/*	Command class for creating lines 						*/
/*										*/
/********************************************************************************/
public class GraphicsCreateLine implements GraphicsCommand {
	private Pane graphics_pane;
	private GraphicsCurvedLine curved_line;

	public GraphicsCreateLine(Pane graphicspane, GraphicsCurvedLine curvedline) {
		graphics_pane = graphicspane;
		curved_line = curvedline;
	}

	@Override
	public void undo() {
		graphics_pane.getChildren().remove(curved_line.getLine());
	}

	@Override
	public void redo() {
		graphics_pane.getChildren().add(curved_line.getLine());
	}
} // end of class GraphicsCreateLine

/* end of GraphicsCreateLine.java */
