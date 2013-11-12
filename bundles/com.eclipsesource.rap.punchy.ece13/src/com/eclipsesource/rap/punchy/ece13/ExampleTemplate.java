/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.rap.rwt.template.ImageCell;
import org.eclipse.rap.rwt.template.ImageCell.ScaleMode;
import org.eclipse.rap.rwt.template.Template;
import org.eclipse.rap.rwt.template.TextCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

@SuppressWarnings("restriction")
public class ExampleTemplate extends Template {
  public ExampleTemplate( Font defaultFont ) {
    super();
    Display display = Display.getCurrent();
    ImageCell imageCell = new ImageCell( this );
    imageCell.setHorizontalAlignment( SWT.BOTTOM );
    imageCell.setBindingIndex( 0 );
    imageCell.setTop( 4 );
    imageCell.setLeft( 4 );
    imageCell.setWidth( 64 );
    imageCell.setHeight( 64 );
    imageCell.setSelectable( true );
    imageCell.setName( "face" );
    imageCell.setScaleMode( ScaleMode.NONE );
    TextCell firstNameCell = new TextCell( this );
    firstNameCell.setHorizontalAlignment( SWT.RIGHT );
    firstNameCell.setVerticalAlignment( SWT.BOTTOM );
    firstNameCell.setBindingIndex( 0 );
    firstNameCell.setForeground( display.getSystemColor( SWT.COLOR_DARK_RED ) );
    firstNameCell.setBackground( display.getSystemColor( SWT.COLOR_GRAY ) );
    firstNameCell.setLeft( 72 );
    firstNameCell.setTop( 4 );
    firstNameCell.setWidth( 180 );
    firstNameCell.setHeight( 40 );
    firstNameCell.setName( "firstname" );
    firstNameCell.setSelectable( true );
    firstNameCell.setWrap( true );
    firstNameCell.setForeground( display.getSystemColor( SWT.COLOR_RED ) );
    FontData fontData = defaultFont.getFontData()[ 0 ];
    fontData.setHeight( 15 );
    fontData.setStyle( SWT.BOLD );
    Font font = new Font( display, fontData );
    firstNameCell.setFont( font );
    TextCell lastNameCell = new TextCell( this );
    lastNameCell.setHorizontalAlignment( SWT.LEFT );
    lastNameCell.setBindingIndex( 1 );
    lastNameCell.setLeft( 90 );
    lastNameCell.setTop( 50 );
    lastNameCell.setRight( 8 );
    lastNameCell.setBottom( 8 );
    lastNameCell.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    lastNameCell.setBackground( display.getSystemColor( SWT.COLOR_DARK_GREEN ) );
    FontData lastNameFont = font.getFontData()[ 0 ];
    lastNameFont.setHeight( 16 );
    lastNameFont.setStyle( SWT.ITALIC );
    lastNameCell.setFont( new Font( display, lastNameFont ) );
    TextCell likeCell = new TextCell( this );
    likeCell.setLeft( 4 );
    likeCell.setWidth( 80 );
    likeCell.setBottom( 2 );
    likeCell.setHeight( 20 );
    likeCell.setText( "Like On FB" );
    likeCell.setName( "like" );
    likeCell.setSelectable( true );
    ImageCell phone = new ImageCell( this );
    phone.setHorizontalAlignment( SWT.RIGHT );
    final Image phoneImage = new Image( display,
                                        ExampleTemplate.class.getResourceAsStream( "/phone.png" ) );
    phone.setImage( phoneImage );
    phone.setTop( 8 );
    phone.setWidth( 48 );
    phone.setRight( 16 );
    phone.setBottom( 8 );
    phone.setName( "phone" );
    phone.setSelectable( true );
  }
}