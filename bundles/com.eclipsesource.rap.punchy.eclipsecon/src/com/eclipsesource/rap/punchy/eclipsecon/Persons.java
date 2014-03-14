/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.punchy.eclipsecon;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Persons {

  public static Person[] get( Display display ) {
    List<Person> persons = getPersons( display );
    Person[] personArray = new Person[ persons.size() ];
    persons.toArray( personArray );
    return personArray;
  }

  private static List<Person> getPersons( Display display ) {
    List<Person> persons = new ArrayList<Person>();
    Person ian = new Person( "Ian", "Bull", loadImage( display, "/ian.jpg" ), "+1 555 123456", "ian@mail.domain" );
//    Person mysterion = new Person( "Mysterion, <i>The Great and Powerful</i>, master of the dark underverse of hellishly long names", null, null, "+666", "service@apple.ms" );
    Person moritz = new Person( "Moritz", "Post", loadImage( display, "/moritz.jpg" ), "+49 555 555", "moritz@mail.domain", new Person[] {
      new Person( "Fictional Child 1", "Post", null ),
      new Person( "Fictional Child 2", "Post", null )
    } );
    Person jochen = new Person( "Jochen", "Krause", loadImage( display, "/jochen.jpg" ), "+49 555 444", "jochen@mail.domain" );
    Person holger = new Person( "Holger", "Staudacher", loadImage( display, "/holger.jpg" ), "+49 555 333", "holger@mail.domain" );
    Person jordi = new Person( "Jordi", "Böhme", loadImage( display, "/jordi.jpg" ), "+49 555 333", "jordi@mail.domain" );
    Person tim = new Person( "Tim", "Buschtöns", loadImage( display, "/tim.jpg" ), "+49 555 222", "tim@mail.domain" );
    for( int i = 0; i < 10; i++ ) {
      persons.add( ian );
      persons.add( moritz );
      persons.add( holger );
      persons.add( jordi );
      persons.add( jochen );
      persons.add( tim );
    }
    return persons;
  }

  private static Image loadImage( Display display, String name ) {
    Image image = new Image( display, Persons.class.getResourceAsStream( name ) );
    return ImageUtil.resizeImage( image, 48, 48 );
  }
}
