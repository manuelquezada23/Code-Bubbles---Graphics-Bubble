/********************************************************************************/
/*										*/
/*		GraphicsMain.java 					*/
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

import java.util.ArrayList;
import java.util.Stack;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/********************************************************************************/
/*										*/
/*	Class that deals with logic						*/
/*										*/
/********************************************************************************/
public class GraphicsMain {
	private Pane graphics_pane;
	private GraphicsOption current_state;
	private GraphicsCurvedLine graphics_line;
	private Color current_color;
	private GraphicsCommand current_command;
	private Stack<GraphicsCommand> command_undo;
	private Stack<GraphicsCommand> command_redo;
	private GraphicsCreateLine create_line;
	private ArrayList<GraphicsShape> shapes_list;
	private GraphicsShape graphics_shape;
	@SuppressWarnings("unused")
	private Color color_created;
	private Point2D previously_created_point;
	private GraphicsCreateShape create_rectangle;
	private Point2D previous_point;
	@SuppressWarnings("unused")
	private double previous_pointX;
	@SuppressWarnings("unused")
	private double previous_pointY;
	private Point2D current_point;
	private GraphicsDeleteShape delete_shape;
	private GraphicsDeleteText deletegraphics_text;
	private double element_rotation;
	private double previous_point_rotation;
	private GraphicsRotate graphics_rotate;
	private double resize_width;
	private double resize_height;
	private Point2D resize_point;
	private GraphicsCommand resize_element;
	private GraphicsFillShape fill_shape;
	private GraphicsFillLine fill_line;
	private GraphicsBelow graphics_below;
	private GraphicsonTop graphics_OnTop;
	private ArrayList<GraphicsTextBox> texts_list;
	private GraphicsTextBox graphics_text;
	private GraphicsCreateTextBox create_text;
	private ArrayList<GraphicsImages> images_list;
	private GraphicsImages graphics_image;
	private GraphicsCreateImage create_image;
	private GraphicsControl graphics_control;
	private GraphicsDeleteImages delete_image;
	private GraphicsCommand resize_image;
	private GraphicsSetText graphics_setText;
	private String text_style;
	private ArrayList<GraphicsCurvedLine> lines_list;
	private GraphicsDeleteLines delete_line;

	public GraphicsMain(Pane graphicsPane) {
		this.setUpCanvas(graphicsPane);
		graphics_pane = graphicsPane;
		graphics_pane.addEventHandler(MouseEvent.ANY, new MouseHandler());
		graphics_pane.setFocusTraversable(true);
		current_color = Color.BLACK;
		command_undo = new Stack<GraphicsCommand>();
		command_redo = new Stack<GraphicsCommand>();
		shapes_list = new ArrayList<GraphicsShape>();
		texts_list = new ArrayList<GraphicsTextBox>();
		images_list = new ArrayList<GraphicsImages>();
		lines_list = new ArrayList<GraphicsCurvedLine>();
		graphics_control = new GraphicsControl(graphicsPane, null);
		setKeys();
	}

	public void setKeys() {
		graphics_pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				final KeyCombination paste = new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN);
				final KeyCombination copy = new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN);
				final KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
				final KeyCombination redo = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN);

				if (paste.match(keyEvent)) {
					graphics_image = new GraphicsImages(5, 80, graphics_pane);
					if (graphics_image.getContent().hasImage()) {
						GraphicsMain.this.createImages(graphics_image, 5, 80);
						create_image = new GraphicsCreateImage(graphics_pane, images_list, graphics_image);
						previously_created_point = new Point2D(5, 80);
						current_command = create_image;
						command_undo.push(current_command);
					} else {
						System.out.println("no image");
					}
				}

				if (copy.match(keyEvent)) {
					if (graphics_image != null) {
						graphics_image.copyImage();
					}
				}

				if (undo.match(keyEvent)) {
					if (command_undo.size() > 0 && !(current_command == null)) {
						current_command = command_undo.pop();
						current_command.undo();
						command_redo.push(current_command);
					}
				}

				if (redo.match(keyEvent)) {
					if (command_redo.size() > 0 && !(current_command == null)) {
						current_command = command_redo.pop();
						current_command.redo();
						command_undo.push(current_command);
					}
				}

				if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
					if (graphics_shape != null && graphics_text == null && graphics_image == null && graphics_line == null) {
						graphics_pane.getChildren().remove(graphics_shape.getNode());
						shapes_list.remove(graphics_shape);
						delete_shape = new GraphicsDeleteShape(graphics_pane, shapes_list, graphics_shape);
						current_command = delete_shape;
						command_undo.push(delete_shape);
					}

					else if (graphics_shape == null && graphics_text != null && graphics_image == null && graphics_line == null) {
						graphics_pane.getChildren().remove(graphics_text.getNode());
						texts_list.remove(graphics_text);
						deletegraphics_text = new GraphicsDeleteText(graphics_pane, texts_list, graphics_text);
						current_command = deletegraphics_text;
						command_undo.push(deletegraphics_text);
					}

					else if (graphics_shape == null && graphics_text == null && graphics_image != null && graphics_line == null) {
						graphics_pane.getChildren().remove(graphics_image.getNode());
						images_list.remove(graphics_image);
						delete_image = new GraphicsDeleteImages(graphics_pane, images_list, graphics_image);
						current_command = delete_image;
						command_undo.push(delete_image);
					}

					else if (graphics_shape == null && graphics_text == null && graphics_image == null && graphics_line != null) {
						graphics_pane.getChildren().remove(graphics_line.getNode());
						lines_list.remove(graphics_line);
						delete_line = new GraphicsDeleteLines(graphics_pane, lines_list, graphics_line);
						current_command = delete_line;
						command_undo.push(delete_line);
					}
				}

			keyEvent.consume();
		}

		});
	}

	private void setUpCanvas(Pane graphicsPane) {
		graphicsPane.setPrefSize(950, 700);
		graphicsPane.setStyle("-fx-background-color:WHITE");
	}

	public void setCurrentRadioButton(GraphicsOption button) {
		 current_state = button;
	}

	public String toRGBCode( Color color ) {
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }

	/********************************************************************************/
	/*										*/
	/*	MouseHandler for creation of elements						*/
	/*										*/
	/********************************************************************************/
	private class MouseHandler implements EventHandler<MouseEvent> {

		public String toRGBCode( Color color ) {
	        return String.format( "#%02X%02X%02X",
	            (int)( color.getRed() * 255 ),
	            (int)( color.getGreen() * 255 ),
	            (int)( color.getBlue() * 255 ) );
	    }

		public void handle(MouseEvent event) {
			if (current_state == GraphicsOption.draw && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					graphics_line = new GraphicsCurvedLine(graphics_pane);
					Double[] points = {event.getX(), event.getY()};
					graphics_line.addPoint(points);
					graphics_line.setColor(current_color);
					graphics_pane.getChildren().add(graphics_line.getLine());
					lines_list.add(graphics_line);
					create_line = new GraphicsCreateLine(graphics_pane, graphics_line);
					current_command = create_line;
					command_undo.push(create_line);
				}

				if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					Double[] points = {event.getX(), event.getY()};
					graphics_line.addPoint(points);
				}
			}

			if (current_state == GraphicsOption.drawrectangle && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					graphics_shape = new GraphicsShapeRectangle(5, 75, 0, 0, current_color, graphics_pane);
					graphics_shape.setFill(current_color);
					color_created = current_color;
					previously_created_point = new Point2D(event.getX(), event.getY());
					GraphicsMain.this.createShape(graphics_shape, 5, 75);
					create_rectangle = new GraphicsCreateShape(graphics_pane, shapes_list, graphics_shape);
					current_command = create_rectangle;
					command_undo.push(current_command);
				}
			}

			if (current_state == GraphicsOption.text && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					graphics_text = new GraphicsTextBox(5, 80, current_color, graphics_pane);
					text_style = ((("-fx-text-fill: " + this.toRGBCode(current_color)).toString()
							+ ";" + ("-fx-font-size: " + graphics_control.getSpinnerValue()).toString() + ";"
							+ ("-fx-font-family: " + graphics_control.getFont().toString())).toString() + ";" + "-fx-focus-color: red"
							);
					graphics_text.setStyle(text_style);
					color_created = current_color;
					previously_created_point = new Point2D(event.getX(), event.getY());
					GraphicsMain.this.createTextBox(graphics_text, 5, 80);
					create_text = new GraphicsCreateTextBox(graphics_pane, texts_list, graphics_text);
					current_command = create_text;
					command_undo.push(current_command);
				}
			}

			if (current_state == GraphicsOption.paste) {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					graphics_image = new GraphicsImages(event.getX(), event.getY(), graphics_pane);
					if (graphics_image.getContent().hasImage()) {
						GraphicsMain.this.createImages(graphics_image, (int) event.getX(), (int) event.getY());
						create_image = new GraphicsCreateImage(graphics_pane, images_list, graphics_image);
						previously_created_point = new Point2D(event.getX(), event.getY());
						current_command = create_image;
						command_undo.push(current_command);
					} else {
						System.out.println("no image");
					}
				}
			}

			if (current_state == GraphicsOption.copy) {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					if (graphics_image != null) {
						graphics_image.copyImage();
					}
				}
			}

			//selecting for nodes
			if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {

				//selecting for lines
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					for (int i = 0; i < lines_list.size(); i++) {
						lines_list.get(i).setUnselected();
						graphics_line = null;
					}
				}

				for (int i = 0; i < lines_list.size(); i++) {
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						if (lines_list.get(i).contains(event.getX(), event.getY())) {
							graphics_line = lines_list.get(i);
							graphics_line.setSelected();
							graphics_text = null;
							graphics_image = null;
							graphics_shape = null;
						}
					}
				}

				//selecting for shapes
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					for (int i = 0; i < shapes_list.size(); i++) {
						System.out.println(shapes_list.get(i));
							shapes_list.get(i).setStroke(null);
							graphics_shape = null;
					}
				}

				for (int i = 0; i < shapes_list.size(); i++) {
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						if (shapes_list.get(i).contains(event.getX(), event.getY())) {
							if (shapes_list.get(i).getNode().isFocused()) {
								graphics_shape = shapes_list.get(i);
								graphics_shape.setStroke(Color.RED);
								graphics_shape.setStrokeWidth(2);
								graphics_image = null;
								graphics_text = null;
								graphics_line = null;
							}
						}
					}
				}


				//selecting for images
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					for (int i = 0; i < images_list.size(); i++) {
						if (graphics_image != null) {
							graphics_image.setBorder("");
							graphics_image = null;
						}
					}
				}

				for (int i = 0; i < images_list.size(); i++) {
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						if (images_list.get(i).contains(event.getX(), event.getY())) {
							if (graphics_image == null) {
								if (images_list.get(i).getNode().isFocused()) {
									images_list.get(i).setBorder("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
									graphics_image = images_list.get(i);
									graphics_shape = null;
									graphics_text = null;
									graphics_line = null;
								}
							}
						}
					}
				}

				//selecting for texts
				for (int i = 0; i < texts_list.size(); i++) {
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						final int innerLoop = new Integer(i);
						for (int j = 0; j < texts_list.size(); j++) {
							graphics_text = null;
						}
						texts_list.get(i).getNode().setOnMouseClicked(textEvent -> {
							texts_list.get(innerLoop).contains(textEvent.getX(), textEvent.getY());
							if (texts_list.get(innerLoop).contains(textEvent.getX(), textEvent.getY())) {
								graphics_text = texts_list.get(innerLoop);
								graphics_image = null;
								graphics_shape = null;
								graphics_line = null;
							}
						});

					}
				}

				if (graphics_image != null && graphics_image.getNode() != null && !(event.isControlDown()) && !(event.isShiftDown())) {
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						previous_point = new Point2D(event.getX(), event.getY());
						previous_pointX = event.getX();
						previous_pointY = event.getY();
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
						current_point = new Point2D(event.getX(), event.getY());
						graphics_image.moveImage(previous_point, current_point);
						previous_point = current_point;
					}
				}

				if (!(graphics_image == null) && graphics_shape == null && event.isShiftDown() == true && !(event.isControlDown())) {
					previous_point = new Point2D(event.getX(), event.getY());
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						resize_width = graphics_image.getWidth();
						resize_height = graphics_image.getHeight();
						resize_point = new Point2D(graphics_image.getCenter().getX(), graphics_image.getCenter().getY());
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
						Point2D currPoint = new Point2D(event.getX(), event.getY());
						graphics_image.resizeImage(previously_created_point, currPoint);
						previously_created_point = currPoint;
						resize_image = new GraphicsImagesResize(resize_point.getX(), resize_point.getY(), resize_width, resize_height, graphics_image);
					}
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
						current_command = resize_image;
						command_undo.push(resize_image);
					}
				}


				if (!(graphics_shape == null) && !(event.isControlDown() && !(event.isShiftDown()))) {
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						previous_point = new Point2D(event.getX(), event.getY());
						previous_pointX = event.getX();
						previous_pointY = event.getY();
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
						current_point = new Point2D(event.getX(), event.getY());
						graphics_shape.moveShape(previous_point, current_point);
						previous_point = current_point;
					}
				}

				if (graphics_shape != null && graphics_image == null && event.isControlDown() == true && !(event.isShiftDown())) {
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						previous_point = new Point2D(event.getX(), event.getY());
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
						current_point = new Point2D(event.getX(), event.getY());
						element_rotation = graphics_shape.rotateShape(previous_point, current_point);
						graphics_shape.getNode().setRotate(element_rotation + previous_point_rotation);
					}
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
						graphics_rotate = new GraphicsRotate(graphics_shape, previous_point, current_point);
						previous_point_rotation = graphics_shape.getNode().getRotate();
						current_command = graphics_rotate;
						command_undo.push(current_command);
					}
				}

				if (!(graphics_shape == null) && graphics_image == null && event.isShiftDown() == true && !(event.isControlDown())) {
					previous_point = new Point2D(event.getX(), event.getY());
					if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
						resize_width = graphics_shape.getWidth();
						resize_height = graphics_shape.getHeight();
						resize_point = new Point2D(graphics_shape.getCenter().getX(), graphics_shape.getCenter().getY());
					}
					if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
						Point2D currPoint = new Point2D(event.getX(), event.getY());
						graphics_shape.resizeShape(previously_created_point, currPoint);
						previously_created_point = currPoint;
						resize_element = new GraphicsShapeResize(resize_point.getX(), resize_point.getY(), resize_width, resize_height, graphics_shape);
					}
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
						current_command = resize_element;
						command_undo.push(resize_element);
					}
				}
			}
			event.consume();
		}
	}

	/********************************************************************************/
	/*										*/
	/*	Methods for different commands						*/
	/*										*/
	/********************************************************************************/
	public void onTop() {
		if (graphics_shape != null) {
			graphics_shape.getNode().toFront();
			graphics_OnTop = new GraphicsonTop(GraphicsMain.this);
			current_command = graphics_OnTop;
			command_undo.push(graphics_OnTop);
		}
		if (graphics_text != null) {
			graphics_text.getNode().toFront();
			graphics_OnTop = new GraphicsonTop(GraphicsMain.this);
			current_command = graphics_OnTop;
			command_undo.push(graphics_OnTop);
		}
		if (graphics_image != null) {
			graphics_image.getNode().toFront();
			graphics_OnTop = new GraphicsonTop(GraphicsMain.this);
			current_command = graphics_OnTop;
			command_undo.push(graphics_OnTop);
		}
		if (graphics_line != null) {
			graphics_line.getNode().toFront();
			graphics_OnTop = new GraphicsonTop(GraphicsMain.this);
			current_command = graphics_OnTop;
			command_undo.push(graphics_OnTop);
		}
	}

	public void below() {
		if (graphics_shape != null) {
			graphics_shape.getNode().toBack();
			graphics_below = new GraphicsBelow(GraphicsMain.this);
			current_command = graphics_below;
			command_undo.push(graphics_below);
		}

		if (graphics_text != null) {
			graphics_text.getNode().toBack();
			graphics_below = new GraphicsBelow(GraphicsMain.this);
			current_command = graphics_below;
			command_undo.push(graphics_below);
		}

		if (graphics_image != null) {
			graphics_image.getNode().toBack();
			graphics_below = new GraphicsBelow(GraphicsMain.this);
			current_command = graphics_below;
			command_undo.push(graphics_below);
		}

		if (graphics_line != null) {
			graphics_line.getNode().toBack();
			graphics_below = new GraphicsBelow(GraphicsMain.this);
			current_command = graphics_below;
			command_undo.push(graphics_below);
		}
	}

	public void deleteElement() {
		if (graphics_shape != null && graphics_text == null && graphics_image == null && graphics_line == null) {
			graphics_pane.getChildren().remove(graphics_shape.getNode());
			shapes_list.remove(graphics_shape);
			delete_shape = new GraphicsDeleteShape(graphics_pane, shapes_list, graphics_shape);
			current_command = delete_shape;
			command_undo.push(delete_shape);
		}

		else if (graphics_shape == null && graphics_text != null && graphics_image == null && graphics_line == null) {
			graphics_pane.getChildren().remove(graphics_text.getNode());
			texts_list.remove(graphics_text);
			deletegraphics_text = new GraphicsDeleteText(graphics_pane, texts_list, graphics_text);
			current_command = deletegraphics_text;
			command_undo.push(deletegraphics_text);
		}

		else if (graphics_shape == null && graphics_text == null && graphics_image != null && graphics_line == null) {
			graphics_pane.getChildren().remove(graphics_image.getNode());
			images_list.remove(graphics_image);
			delete_image = new GraphicsDeleteImages(graphics_pane, images_list, graphics_image);
			current_command = delete_image;
			command_undo.push(delete_image);
		}

		else if (graphics_shape == null && graphics_text == null && graphics_image == null && graphics_line != null) {
			graphics_pane.getChildren().remove(graphics_line.getNode());
			lines_list.remove(graphics_line);
			delete_line = new GraphicsDeleteLines(graphics_pane, lines_list, graphics_line);
			current_command = delete_line;
			command_undo.push(delete_line);
		}
	}

	public void Fill() {
		if (graphics_shape != null) {
			fill_shape = new GraphicsFillShape(graphics_shape, current_color);
			current_command = fill_shape;
			graphics_shape.setFill(current_color);
			command_undo.push(fill_shape);
		}
		if (graphics_line != null) {
			fill_line = new GraphicsFillLine(graphics_line, current_color);
			current_command = fill_line;
			graphics_line.setFill(current_color);
			command_undo.push(fill_line);
		}
	}

	public void setTextStyle() {
		if (graphics_text != null) {
			text_style = ((("-fx-text-fill: " + this.toRGBCode(current_color)).toString()
					+ ";" + ("-fx-font-size: " + graphics_control.getSpinnerValue()).toString() + ";"
					+ ("-fx-font-family: " + graphics_control.getFont().toString())).toString() + ";" + "-fx-focus-color: red"
					);
			graphics_setText = new GraphicsSetText(graphics_text, text_style);
			current_command = graphics_setText;
			graphics_text.setStyle(text_style);
			command_undo.push(graphics_setText);
		}
	}

	private void createTextBox(GraphicsTextBox textbox, int x, int y) {
		textbox.setLocation(x, y);
		graphics_pane.getChildren().add(textbox.getNode());
		texts_list.add(textbox);
	}

	private void createShape(GraphicsShape Shape, double x, double y) {
		Shape.setLocation(x, y);
		Shape.setFill(current_color);
		graphics_pane.getChildren().add(Shape.getNode());
		shapes_list.add(Shape);
	}

	private void createImages(GraphicsImages image, int x, int y) {
		if (image.getNode() != null) {
			images_list.add(image);
			graphics_pane.getChildren().add(image.getNode());
		}
	}

	public void enableDragging(Node node) {
		final ObjectProperty<Point2D> mouse = new SimpleObjectProperty<>();
		node.setOnMousePressed( event -> mouse.set(new Point2D(event.getSceneX(), event.getSceneY())));
		node.setOnMouseDragged( event -> {
			double x = event.getSceneX() - mouse.get().getX();
			double y = event.getSceneY() - mouse.get().getY();
			node.relocate(node.getLayoutX() + x, node.getLayoutY() + y);
			mouse.set(new Point2D(event.getSceneX(), event.getSceneY()));;
		});
	}


	public void Undo() {
		if (command_undo.size() > 0 && !(current_command == null)) {
			current_command = command_undo.pop();
			current_command.undo();
			command_redo.push(current_command);
		}
	}

	public void Redo() {
		if (command_redo.size() > 0 && !(current_command == null)) {
			current_command = command_redo.pop();
			current_command.redo();
			command_undo.push(current_command);
		}
	}

	public void setColorPicker(Color color) {
		current_color = color;
	}

	public Pane getPane() {
		return graphics_pane;
	}
} // end of class GraphicsMain

/* end of GraphicsMain.java */
