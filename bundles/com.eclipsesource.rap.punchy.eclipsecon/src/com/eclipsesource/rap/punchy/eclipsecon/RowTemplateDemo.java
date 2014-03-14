/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.punchy.eclipsecon;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.rap.rwt.template.Template;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;

public class RowTemplateDemo extends AbstractEntryPoint {

  @SuppressWarnings("serial")
  private final class SelectionListener extends SelectionAdapter {

    private final Composite parent;

    private SelectionListener( Composite parent ) {
      this.parent = parent;
    }

    @Override
    public void widgetSelected( SelectionEvent e ) {
      if( "phone".equals( e.text ) ) {
        TableItem item = ( TableItem )e.item;
        alert( "Dialing...", "Calling " + item.getText( 2 ) + "!" );
      } else if( "mail".equals( e.text ) ) {
        String mail = ( ( TableItem )e.item ).getText( 3 );
        String firstName = ( ( TableItem )e.item ).getText( 0 );
        UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
        if( launcher != null ) {
          launcher.openURL( "mailto:" + mail + "?subject=RAP%20Rocks!&body=Hello%20" + firstName );
        } else {
          alert( "Now mailing to...", mail );
        }
      } else if( "arrow".equals( e.text ) ) {
        TableItem item = ( TableItem )e.item;
        String firstName = item.getText( 0 );
        alert( "Nothing here", "Lets edit " + firstName + "!" );
      } else if( "like".equals( e.text ) ) {
        TableItem item = ( TableItem )e.item;
        String firstName = item.getText( 0 );
        alert( "I Like You", "Liking " + firstName + " on FB!" );
      } else if( "firstname".equals( e.text ) ) {
        System.out.println( "Clicking firstname" );
      }
    }

    private void alert( String title, String message ) {
      MessageBox messageBox = new MessageBox( parent.getShell(), SWT.ICON_INFORMATION );
      messageBox.setText( title );
      messageBox.setMessage( message );
      DialogUtil.open( messageBox, null );
    }
  }

  private Control exampleControl;
  private Listener createGrid;
  private Combo templateCombo;
  private Integer[] templateHeights;
  private Template[] templates;

  @Override
  protected void createContents( final Composite parent ) {
    createGrid = new Listener() {
      @Override
      public void handleEvent( Event event ) {
        createGrid( parent );
        parent.layout();
      }
    };
    GridLayout layout = new GridLayout( 1, true );
    layout.marginLeft = 0;
    layout.marginTop = 0;
    layout.marginRight = 0;
    layout.marginBottom = 0;
    layout.horizontalSpacing = 10;
    layout.verticalSpacing = 10;
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    parent.setLayout( layout );
    createConfigArea( parent );
    createGrid( parent );
  }

  private void createConfigArea( Composite parent ) {
    templateCombo = new Combo( parent, SWT.READ_ONLY );
    templateCombo.setItems( new String[] { "no template", "PrettyTemplate", "ExampleTemplate" } );
    Font defaultFont = parent.getFont();
    templates = new Template[] {
      null,
      new PrettyTemplate( defaultFont ),
      new ExampleTemplate( defaultFont )
    };
    templateHeights = new Integer[]{ null, Integer.valueOf( 56 ), Integer.valueOf( 92 ) };
    templateCombo.select( 0 );
    templateCombo.addListener( SWT.Selection, createGrid );
  }

  private void createGrid( Composite parent ) {
    if( exampleControl != null ) {
      exampleControl.dispose();
    }
    createTable( parent );
    exampleControl.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    int tempalteIndex = templateCombo.getSelectionIndex();
    exampleControl.setData( RWT.ROW_TEMPLATE, templates[ tempalteIndex ] );
    exampleControl.setData( RWT.CUSTOM_ITEM_HEIGHT, templateHeights[ tempalteIndex ] );
    exampleControl.moveAbove( null );
  }

  private void createTable( Composite parent ) {
    TableViewer tableViewer = new TableViewer( parent, SWT.BORDER );
    exampleControl = tableViewer.getTable();
    tableViewer.setContentProvider( new ArrayContentProvider() );
    configColumnViewer( tableViewer );
    tableViewer.getTable().setHeaderVisible( templateCombo.getSelectionIndex() == 0 );
    tableViewer.getTable().addSelectionListener( new SelectionListener( parent ) );
  }

  private void configColumnViewer( ColumnViewer viewer ) {
    addFirstNameColumn( viewer );
    addLastNameColumn( viewer );
    addPhoneColumn( viewer );
    addMailColumn( viewer );
    viewer.setInput( Persons.get( viewer.getControl().getDisplay() ) );
  }

  private void addFirstNameColumn( final ColumnViewer viewer ) {
    ViewerColumn firstNameColumn = createViewerColumn( viewer, "First Name", 200 );
    firstNameColumn.setLabelProvider( new ColumnLabelProvider() {
      @Override
      public String getText( Object element ) {
        Person p = ( Person )element;
        return p.getFirstName();
      }
      @Override
      public Image getImage( Object element ) {
        Person p = ( Person )element;
        return p.getImage();
      }
    } );
  }

  private void addLastNameColumn( ColumnViewer viewer ) {
    ViewerColumn lastNameColumn = createViewerColumn( viewer, "Last Name", 200 );
    lastNameColumn.setLabelProvider( new ColumnLabelProvider() {
      @Override
      public String getText( Object element ) {
        Person p = ( Person )element;
        return p.getLastName();
      }
    } );
  }

  private void addPhoneColumn( ColumnViewer viewer ) {
    ViewerColumn pohoneColumn = createViewerColumn( viewer, "Phone", 130 );
    pohoneColumn.setLabelProvider( new ColumnLabelProvider() {
      @Override
      public String getText( Object element ) {
        Person p = ( Person )element;
        return p.getPhone();
      }
    } );
  }

  private void addMailColumn( ColumnViewer viewer ) {
    ViewerColumn pohoneColumn = createViewerColumn( viewer, "E-Mail", 180 );
    pohoneColumn.setLabelProvider( new ColumnLabelProvider() {
      @Override
      public String getText( Object element ) {
        Person p = ( Person )element;
        return p.getMail();
      }
    } );
  }

  private ViewerColumn createViewerColumn( final ColumnViewer viewer, String name, int width ) {
    ViewerColumn viewerColumn = null;
    if( viewer instanceof TableViewer ) {
      TableViewer tableViewer = ( TableViewer )viewer;
      TableViewerColumn tableColumn = new TableViewerColumn( tableViewer, SWT.NONE );
      tableColumn.getColumn().setWidth( width );
      tableColumn.getColumn().setText( name );
      viewerColumn = tableColumn;
    } else if( viewer instanceof TreeViewer ) {
      TreeViewer treeViewer = ( TreeViewer )viewer;
      TreeViewerColumn treeColumn = new TreeViewerColumn( treeViewer, SWT.NONE );
      treeColumn.getColumn().setWidth( width );
      treeColumn.getColumn().setText( name );
      viewerColumn = treeColumn;
    }
    return viewerColumn;
  }

}
