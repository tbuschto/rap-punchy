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

import java.util.Arrays;

import org.eclipse.rap.addons.autosuggest.ArrayDataProvider;
import org.eclipse.rap.addons.autosuggest.AutoSuggest;
import org.eclipse.rap.addons.autosuggest.ColumnDataProvider;
import org.eclipse.rap.addons.autosuggest.ColumnTemplate;
import org.eclipse.rap.addons.autosuggest.DataSource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;


public class AutoSuggestSnippet {

  private DataSource simpleDataSource;
  private DataSource columnDataSource;
  private Text text1;

  protected void createContents( Composite parent ) {
    parent.setLayout( new FormLayout() );
    createDataSources();
    text1 = new Text( parent, SWT.BORDER );
    text1.setLayoutData( createTextData( null ) );
    text1.setMessage( "Movies (AutoSuggest)" );
    AutoSuggest autoSuggest1 = new AutoSuggest( text1 );
    autoSuggest1.setAutoComplete( true );
    autoSuggest1.setDataSource( simpleDataSource );
//    autoSuggest1.addSelectionListener( new SuggestionSelectedListener() {
//      public void suggestionSelected() {
//        System.out.println( "selected: " + text1.getText() );
//      }
//    } );

    Text text2 = new Text( parent, SWT.BORDER );
    text2.setLayoutData( createTextData( text1 ) );
    text2.setMessage( "Plate Codes" );
    AutoSuggest autoSuggest2 = new AutoSuggest( text2 );
    autoSuggest2.setDataSource( columnDataSource );
  }

  private void createDataSources() {
    simpleDataSource = new DataSource();
    simpleDataSource.setDataProvider( new ArrayDataProvider( Movies.VALUES ) );
    columnDataSource = new DataSource();
    columnDataSource.setTemplate( new ColumnTemplate( 50, 150, 250 ) );
    columnDataSource.setDataProvider( new ColumnDataProvider() {
      public Iterable<?> getSuggestions() {
        return Arrays.asList( KFZ.DE );
      }
      public String getValue( Object element ) {
        String[] value = ( String[] )element;
        return value[ 0 ];
      }
      public String[] getTexts( Object element ) {
        String[] value = ( String[] )element;
        return new String[] { value[ 0 ], value[ 2 ], value[ 3 ] };
      }
    } );
  }

  private static FormData createTextData( Control control ) {
    FormData formData = new FormData();
    formData.top = control == null
                 ? new FormAttachment( 0, 30 )
                 : new FormAttachment( control, 55 );
    formData.left = new FormAttachment( 50, -150 );
    formData.right = new FormAttachment( 50, 150 );
    return formData;
  }

}
