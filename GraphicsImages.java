/********************************************************************************/
/*										*/
/*		GraphicsImages.java 					*/
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

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;

public class GraphicsImages {
	private Image fx_image;
	private java.awt.Image awt_image;
	private ImageView image_view;
	private double double_rotation;
	private Point2D old_center;
	private Point2D rotated_prev;
	private Point2D rotated_curr;
	private Point2D image_point;
	private Point2D new_center;
	private Clipboard graphics_clipboard;
	private ClipboardContent graphics_content;
	private Pane graphics_pane;
	private Image copied_image;

	/********************************************************************************/
	/*										*/
	/*	Creation of copy-paste images 						*/
	/*										*/
	/********************************************************************************/
	public GraphicsImages(double x, double y, Pane graphicspane) {
		graphics_pane = graphicspane;
		awt_image = this.getImageFromClipboard();
		graphics_clipboard = Clipboard.getSystemClipboard();
		graphics_content = new ClipboardContent();
		if (awt_image != null) {
			try {
				fx_image = awtImageToFX(awt_image);
				graphics_content.putImage(fx_image);
				graphics_clipboard.setContent(graphics_content);
				image_view = new ImageView(fx_image);
				image_view.setFitWidth(fx_image.getWidth() * 0.4);
				image_view.setFitHeight(fx_image.getHeight() * 0.4);
				image_view.setLayoutX(5);
				image_view.setLayoutY(80);
		    	image_view.setPreserveRatio(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			image_view = null;
		}
	}


	public ClipboardContent getContent() {
		return graphics_content;
	}

	public boolean isFocused() {
		if (image_view != null) {
			return image_view.isFocused();
		} else {
			return false;
		}
	}

	public void moveImage(Point2D prev, Point2D curr) {
		Double xLoc = prev.getX() - this.getXLoc();
		Double yLoc = prev.getY() - this.getYLoc();
		Double dx = curr.getX() - xLoc;
		Double dy = curr.getY() - yLoc;
		this.setLocation(dx, dy);
	}

	public void setBorder(String string) {
		if (image_view != null) {
			image_view.setStyle(string);
		}
	}

	public void removeBorder() {
		image_view.setStyle(null);
	}

	public void setLocation(double x, double y) {
		this.setXLoc(x);
		this.setYLoc(y);
	}

	public void setXLoc(double x) {
		image_view.setX(x);
	}

	public void setYLoc(double y) {
		image_view.setY(y);
	}

	public double getXLoc() {
		return image_view.getX();
	}

	public double getYLoc() {
		return image_view.getY();
	}

	public Node getNode() {
		return image_view;
	}

	public boolean contains(double x, double y) {
		if (image_view != null) {
			if (image_view.contains(x, y)) {
				image_view.requestFocus();
				return true;
			}  else {
				graphics_pane.requestFocus();
				return false;
			}
		} else {
			return false;
		}
	}

	public void copyImage() {
		if (image_view != null) {
			copied_image = image_view.getImage();
			System.out.println(copied_image);
			graphics_clipboard = Clipboard.getSystemClipboard();
			graphics_content.putImage(copied_image);
			graphics_clipboard.setContent(graphics_content);
		}
	}

	public void resizeImage(Point2D prev, Point2D curr) {
		double_rotation = image_view.getRotate();
		old_center = this.getCenter();
		rotated_prev = this.rotatePoint(prev, old_center, double_rotation);
		rotated_curr = this.rotatePoint(curr, old_center, double_rotation);

		double dx = Math.abs(rotated_curr.getX() - rotated_prev.getX());
		double dy = Math.abs(rotated_curr.getY() - rotated_prev.getY());

		if (Math.abs(this.getCenter().getX() - rotated_prev.getX()) > Math.abs(this.getCenter().getX() - rotated_curr.getX())) {
			dx = -1 * dx;
		}
		if (Math.abs(this.getCenter().getY() - rotated_prev.getY()) > Math.abs(this.getCenter().getY() - rotated_curr.getY())) {
			dy = -1 * dy;
		}

		image_view.setFitWidth(image_view.getFitWidth() + 2 * dx);
		image_view.setFitHeight(image_view.getFitHeight() + 2 * dy);

		new_center = this.getCenter();
		if (!(new_center == old_center)) {
			double centerdx = old_center.getX() - new_center.getX();
			double centerdy = old_center.getY() - new_center.getY();
			this.setLocation(this.getXLoc() + centerdx, this.getYLoc() + centerdy);
		}
	}

	public void setWidth(double x) {
		image_view.setFitWidth(x);
	}

	public void setHeight(double y) {
		image_view.setFitHeight(y);
	}

	public double getWidth() {
		return image_view.getFitWidth();
	}

	public double getHeight() {
		return image_view.getFitHeight();
	}

	public Point2D rotatePoint(Point2D pointtorotate, Point2D rotatearound, double angle) {
		double sine = Math.sin(Math.toRadians(angle));
		double cosine = Math.cos(Math.toRadians(angle));
		image_point = new Point2D(pointtorotate.getX() - rotatearound.getX(), pointtorotate.getY() - rotatearound.getY());
		image_point = new Point2D(image_point.getX()*cosine + image_point.getY()*sine, -image_point.getX()*sine + image_point.getY()*cosine);
		image_point = new Point2D(image_point.getX() + rotatearound.getX(), image_point.getY() + rotatearound.getY());
		return image_point;
	}

	public Point2D getCenter() {
		Point2D point = new Point2D(image_view.getX() + image_view.maxWidth(image_view.getFitWidth())/2,
				image_view.getY() + image_view.maxHeight(image_view.getFitHeight())/2);
		return point;
	}

	/********************************************************************************/
	/*										*/
	/*	Method to convert awt images to FX images 						*/
	/*										*/
	/********************************************************************************/
	private Image awtImageToFX(java.awt.Image image) throws Exception {
        if (!(image instanceof RenderedImage)) {
            BufferedImage buff = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = buff.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            image = buff;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image, "png", output);
        output.flush();
        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        return new javafx.scene.image.Image(input);
    }
	/********************************************************************************/
	/*										*/
	/*	Method to get image from Clipboard 						*/
	/*										*/
	/********************************************************************************/
	private java.awt.Image getImageFromClipboard() {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            try {
                return (java.awt.Image) transferable.getTransferData(DataFlavor.imageFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
} // end of class GraphicsImages

/* end of GraphicsImages.java */
