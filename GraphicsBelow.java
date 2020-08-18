/********************************************************************************/
/*										*/
/*		GraphicsBelow.java 					*/
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


public class GraphicsBelow implements GraphicsCommand {
	private GraphicsMain main_graphics;

	/********************************************************************************/
	/*										*/
	/*	Command class for setting nodes below 						*/
	/*										*/
	/********************************************************************************/
	public GraphicsBelow(GraphicsMain graphics) {
		main_graphics = graphics;
	}

	@Override
	public void undo() {
		main_graphics.onTop();
	}

	@Override
	public void redo() {
		main_graphics.below();
	}
} // end of class GraphicsBelow

/* end of GraphicsBelow.java */
