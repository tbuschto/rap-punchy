/*******************************************************************************
 * Copyright (c) 2012, 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.rap.rwt.lifecycle.WidgetUtil;
import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;


public class CustomBehaviors {

  private static final String RESOURCES_PREFIX = "";

  private CustomBehaviors() {
    // prevent instantiation
  }

  public static void addUpperCaseBehavior( Text text ) {
    String scriptCode = ResourceLoaderUtil.readFile( RESOURCES_PREFIX + "UpperCase.js" );
    ClientListener clientListener = new ClientListener( scriptCode );
    text.addListener( SWT.Verify, clientListener );
  }

  public static void addDateFieldBehavior( Text text ) {
    text.setText( "__.__.____" );
    String scriptCode = ResourceLoaderUtil.readFile( RESOURCES_PREFIX + "DateField.js" );
    ClientListener clientListener = new ClientListener( scriptCode );
    text.addListener( SWT.KeyDown, clientListener );
    text.addListener( SWT.Verify, clientListener );
    text.addListener( SWT.MouseUp, clientListener );
    text.addListener( SWT.MouseDown, clientListener );
  }

  public static void addPaintingBehavior( final Canvas canvas ) {
    String scriptCode = ResourceLoaderUtil.readFile( RESOURCES_PREFIX + "Painting.js" );
    ClientListener listener = new ClientListener( scriptCode );
    canvas.addMouseListener( new MouseAdapter() {
      @Override
      public void mouseUp( MouseEvent e ) {
        canvas.setForeground( getRandomColor( canvas.getDisplay() ) );
        canvas.redraw();
      }
    } );
    canvas.addListener( SWT.MouseMove, listener );
    canvas.addListener( SWT.Paint, listener );
    canvas.setForeground( getRandomColor( canvas.getDisplay() ) );
    canvas.redraw();
  }


  public static void addFocusNextBehavior( Text text, Control next ) {
    text.setTextLimit( 4 );
    String scriptCode = ResourceLoaderUtil.readFile( RESOURCES_PREFIX + "FocusSwitch.js" );
    ClientListener listener = new ClientListener( scriptCode );
    text.addListener( SWT.Modify, listener );
    WidgetUtil.registerDataKeys( "next" );
    text.setData( "next", WidgetUtil.getId( next ) );
  }

  public static void addFocusPreviousBehavior( Text text, Control previous ) {
    text.setTextLimit( 4 );
    String scriptCode = ResourceLoaderUtil.readFile( RESOURCES_PREFIX + "FocusSwitch.js" );
    ClientListener listener = new ClientListener( scriptCode );
    text.addListener( SWT.Modify, listener );
    WidgetUtil.registerDataKeys( "previous" );
    text.setData( "previous", WidgetUtil.getId( previous ) );
  }

  public static Color getRandomColor( Device device ) {
    RGB rgb = new RGB(
      ( int )Math.round(  Math.random() * 255 ),
      ( int )Math.round(  Math.random() * 255 ),
      ( int )Math.round(  Math.random() * 255 )
    );
    return new Color( device, rgb );
  }


}
