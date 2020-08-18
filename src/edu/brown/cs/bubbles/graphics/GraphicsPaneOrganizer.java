/********************************************************************************/
/*										*/
/*		GraphicsPaneOrganizer.java 					*/
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

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/********************************************************************************/
/*										*/
/*	Class organizing panes 						*/
/*										*/
/********************************************************************************/
public class GraphicsPaneOrganizer {
	private BorderPane root_pane;
	private GraphicsMain graphics_main;
	@SuppressWarnings("unused")
	private GraphicsControl graphics_control;
	private StackPane stack_pane;

	public GraphicsPaneOrganizer() {
		root_pane = new BorderPane();
		root_pane.setFocusTraversable(true);
		Pane graphicspane = new Pane();
		graphics_main = new GraphicsMain(graphicspane);
		root_pane.setLeft(graphicspane);
		VBox buttons = new VBox();
		buttons.setMaxSize(1, 1);
		graphics_control = new GraphicsControl(buttons, graphics_main);
		stack_pane = new StackPane(graphicspane, buttons);
		StackPane.setAlignment(buttons, Pos.TOP_LEFT);

	}

	public StackPane getStack() {
		return stack_pane;
	}

} // end of class GraphicsPaneOrganizer

/* end of GraphicsPaneOrganizer.java */
