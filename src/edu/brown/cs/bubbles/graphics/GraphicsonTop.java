/********************************************************************************/
/*										*/
/*		GraphicsonTop.java 					*/
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

/********************************************************************************/
/*										*/
/*	Command class for setting nodes on top of each other 			*/
/*										*/
/********************************************************************************/
public class GraphicsonTop implements GraphicsCommand {
	private GraphicsMain graphics_main;

	public GraphicsonTop(GraphicsMain graphics) {
		graphics_main = graphics;
	}

	@Override
	public void undo() {
		graphics_main.below();
		System.out.println("undo");
	}

	@Override
	public void redo() {
		System.out.println("redo");
		graphics_main.onTop();

	}
} // end of class GraphicsonTop

/* end of GraphicsonTop.java */
