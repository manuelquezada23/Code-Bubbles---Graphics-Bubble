/********************************************************************************/
/*										*/
/*		GraphicsCommand.java 					*/
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
/*	Interface implemented in Command classes 						*/
/*										*/
/********************************************************************************/
public interface GraphicsCommand {
	
	public void undo();
	public void redo();
	
} // end of interface GraphicsCommand

/* end of GraphicsCommand.java */