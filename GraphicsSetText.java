/********************************************************************************/
/*										*/
/*		GraphicsSetText.java 					*/
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
/*	Command class for setting style of text boxes 						*/
/*										*/
/********************************************************************************/
public class GraphicsSetText implements GraphicsCommand {
	private GraphicsTextBox graphics_text;
	private String old_style;
	private String new_style;
	
	public GraphicsSetText(GraphicsTextBox text, String style) {
		graphics_text = text;
		old_style = graphics_text.getStyle();
		new_style = style;
		System.out.println("old is " + old_style);
		System.out.println("new is " + new_style);
	}

	@Override
	public void undo() {
		new_style = graphics_text.getStyle();
		graphics_text.setStyle(old_style);
	}

	@Override
	public void redo() {
		graphics_text.setStyle(new_style);
	}
} // end of class GraphicsSetText

/* end of GraphicsSetText.java */
