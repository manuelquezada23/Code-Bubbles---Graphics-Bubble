/********************************************************************************/
/*										*/
/*		GraphicsApp.java 					*/
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

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicsApp extends Application {
	private Scene the_scene;
	private Stage the_stage;
	
	/********************************************************************************/
	/*										*/
	/*	Stage and scene creation 						*/
	/*										*/
	/********************************************************************************/

    @Override
    public void start(Stage stage) {
    	the_stage = stage;
    	GraphicsPaneOrganizer organizer = new GraphicsPaneOrganizer(); 
    	the_scene = new Scene(organizer.getStack(), 570, 400); 
    	the_stage.setScene(the_scene); 
    	the_stage.setTitle("Graphics Bubble"); 
    	the_stage.show(); 
    }
    
    public static void main(String[] argv) {
        launch(argv);
    }
    
    public ReadOnlyDoubleProperty getSceneWidthProperty() {
    	return the_scene.widthProperty();
    }
    
    public double getSceneHeight() {
    	return the_scene.getHeight();
    }
    
    public double getSceneWidth() {
    	return the_scene.getWidth();
    }
} // end of class GraphicsApp

/* end of GraphicsApp.java */
