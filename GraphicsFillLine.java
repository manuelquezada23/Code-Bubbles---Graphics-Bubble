/********************************************************************************/
/*										*/
/*		GraphicsFillLine.java 					*/
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

import javafx.scene.paint.Color;

/********************************************************************************/
/*										*/
/*	Command class for filling shapes 						*/
/*										*/
/********************************************************************************/
public class GraphicsFillLine implements GraphicsCommand {
	private GraphicsCurvedLine graphics_line;
	private Color new_color;
	private Color old_color;
	
	public GraphicsFillLine (GraphicsCurvedLine line, Color color) {
		graphics_line = line; 
		old_color = graphics_line.getFill();
		new_color = color;
	}
	
	@Override
	public void undo() {
		new_color = graphics_line.getFill();
		graphics_line.setFill(old_color);
	}
 
	@Override
	public void redo() {
		graphics_line.setFill(new_color);
	}
} // end of class GraphicsFill

/* end of GraphicsFill.java */