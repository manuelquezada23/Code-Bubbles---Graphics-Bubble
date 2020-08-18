/********************************************************************************/
/*										*/
/*		GraphicsFactory.java 					*/
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
/*	Class that implements the factory for a host of graphic bubbles 	*/
/*										*/
/********************************************************************************/
public class GraphicsFactory {
	private static GraphicsFactory the_factory = null;

	public static synchronized GraphicsFactory getFactory() {
		if (the_factory == null) {
			the_factory = new GraphicsFactory();
		}
		return the_factory;
	}

} // end of class GraphicsFactory

/* end of GraphicsFactory.java */
