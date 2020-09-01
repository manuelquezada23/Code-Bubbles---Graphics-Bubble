/********************************************************************************/
/*										*/
/*		GraphicsControl.java 					*/
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

/********************************************************************************/
/*										*/
/*	Control class that handles UI 						*/
/*										*/
/********************************************************************************/
public class GraphicsControl {
	private static GraphicsMain main_graphics;
	private static Pane graphics_pane;
	private static Spinner<Integer> graphics_spinner;
	private static ChoiceBox<String> graphics_fonts;
	private Pane panels_pane;
	private static VBox buttons_toolbar;
	private static VBox buttons;

	public GraphicsControl(Pane graphicspane, GraphicsMain graphics) {
		graphics_pane = graphicspane;

		buttons = new VBox();
		main_graphics = graphics;

        Node radiobuttonspanel = createRadioButtonsPanel();
        radiobuttonspanel.relocate(0, 0);
        panels_pane = new Pane();
        panels_pane.getChildren().addAll(radiobuttonspanel);

        buttons.getChildren().add(panels_pane);
//        graphics_pane.getChildren().add(buttons);
        
        buttons_toolbar = new VBox();
        Button toolbar = new Button();
        buttons_toolbar.getChildren().add(toolbar);
        graphics_pane.getChildren().add(buttons_toolbar);
        
        toolbar.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				graphics_pane.getChildren().add(buttons);
				graphics_pane.getChildren().remove(buttons_toolbar);
			}
    	});

	}

	public Pane getButtonsPane() {
		return panels_pane;
	}

	/********************************************************************************/
	/*										*/
	/*	Button creation methods 						*/
	/*										*/
	/********************************************************************************/
	private static Node createRadioButtonsPanel() {
    	VBox vbox = new VBox();

    	HBox radiobuttons = new HBox();

    	ToggleGroup toggle = new ToggleGroup();

    	RadioButton rectangle = new RadioButton("rectangle");
    	rectangle.setToggleGroup(toggle);
    	rectangle.setSelected(false);
    	rectangle.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				main_graphics.setCurrentRadioButton(GraphicsOption.drawrectangle);
			}
    	});

    	RadioButton pen = new RadioButton("pen");
    	pen.setToggleGroup(toggle);
    	pen.setSelected(false);
    	pen.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				main_graphics.setCurrentRadioButton(GraphicsOption.draw);
			}
    	});

    	RadioButton text = new RadioButton("text");
    	text.setToggleGroup(toggle);
    	text.setSelected(false);
    	text.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				main_graphics.setCurrentRadioButton(GraphicsOption.text);
			}
    	});

    	RadioButton paste = new RadioButton("paste image");
    	paste.setToggleGroup(toggle);
    	paste.setSelected(false);
    	paste.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.setCurrentRadioButton(GraphicsOption.paste);
    		}
    	});

    	RadioButton copy = new RadioButton("copy image");
    	copy.setToggleGroup(toggle);
    	copy.setSelected(false);
    	copy.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.setCurrentRadioButton(GraphicsOption.copy);
    		}
    	});

    	Button help = new Button("?");
    	help.setStyle("-fx-background-radius: 5em;");
    	help.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage stage = new Stage();
    			VBox vbox = new VBox();
    			vbox.setSpacing(2);
    			vbox.setStyle("-fx-background-color: white");
    			Label create = new Label("* Double-click left mouse to create elements.");
    			Label create2 = new Label("* Double-click left mouse and drag to draw");
    			Label rotate = new Label("* Press CTRL and drag to rotate elements.");
    			Label resize = new Label("* Press SHIFT and drag to resize elements.");
    			Label move = new Label("* Click left mouse and drag to move elements");
    			Label copy = new Label("* Select the copy button, click on image twice ");
    			Label copy2 = new Label("to copy it, or press ctrl+C");
    			Label paste = new Label("* Select the paste button, click on window twice ");
    			Label paste2 = new Label("to paste image, or press ctrl+V");
    			Label cut = new Label("* Cut by using the cut button, or pressing ");
    			Label cut2 = new Label("the delete key.");
    			Popup pop = new Popup();
    			pop.getContent().addAll(create, create2, move, rotate, resize, paste, paste2, copy, copy2, cut, cut2);
    			pop.setAutoHide(true);

    			vbox.getChildren().addAll(create, create2, move, rotate, resize, paste, paste2, copy, copy2, cut, cut2);
    			Scene scene = new Scene(vbox, 270, 210);
    			stage.setScene(scene);
    			stage.show();
    		}
    	});
    	
    	Button close = new Button("X");
    	close.setTranslateX(120);
    	close.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			graphics_pane.getChildren().remove(buttons);
				graphics_pane.getChildren().add(buttons_toolbar);
    		}
    		
    	});

    	radiobuttons.setSpacing(5);
    	radiobuttons.getChildren().addAll(rectangle, pen, text, paste, copy, help, close);

    	HBox buttons = new HBox();

    	ColorPicker cp = new ColorPicker();
    	cp.setMaxSize(45, 60);
    	cp.setValue(Color.BLACK);
    	cp.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if(cp.isFocused()) {
    				main_graphics.setColorPicker(cp.getValue());
    			}
    		}
    	});

    	Button fill = new Button("Fill");
    	fill.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.Fill();
    		}
    	});

    	Button ontop = new Button("On Top");
    	ontop.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.onTop();
    		}
    	});


    	Button below = new Button("Below");
    	below.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.below();
    		}
    	});

    	Button delete = new Button("Cut");
    	delete.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.deleteElement();
    		}
    	});

    	Button undo = new Button("Undo");
    	undo.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.Undo();
    		}
    	});

    	Button redo = new Button("Redo");
    	redo.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.Redo();
    		}
    	});

    	Button setStyle = new Button("Set Text");
    	setStyle.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			main_graphics.setTextStyle();
    		}
    	});

    	ObservableList<String> fonts = FXCollections.observableArrayList(Font.getFamilies());
    	graphics_fonts = new ChoiceBox<String>();
    	graphics_fonts.setValue("Arial");
    	graphics_fonts.setMaxWidth(55);
    	graphics_fonts.setItems(fonts);

    	graphics_spinner = new Spinner<Integer>();
    	graphics_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 12));
    	graphics_spinner.setMaxWidth(60);

    	buttons.setSpacing(5);
    	buttons.getChildren().addAll(cp, graphics_fonts, graphics_spinner, setStyle, fill, ontop, below, delete, undo, redo);

    	vbox.setSpacing(5);
    	configureBorder(vbox);
    	vbox.setAlignment(Pos.TOP_LEFT);
    	vbox.getChildren().addAll(radiobuttons, buttons);

    	return vbox;
    }

	public Integer getSpinnerValue() {
		return graphics_spinner.getValue();
	}

	public Spinner<Integer> getSpinner() {
		return graphics_spinner;
	}

	public String getFont() {
		return graphics_fonts.getValue();
	}

	public ChoiceBox<String> getFontOptions() {
		return graphics_fonts;
	}


	/********************************************************************************/
	/*										*/
	/*	Border of UI box 						*/
	/*										*/
	/********************************************************************************/
    private static void configureBorder(final Region region) {
        region.setStyle("-fx-background-color: white;"
                            + "-fx-border-color: black;"
                            + "-fx-border-width: 1;"
                            + "-fx-border-radius: 6;"
                            + "-fx-padding: 6;");
    }

} // end of class GraphicsControl

/* end of GraphicsControl.java */
