/*******************************************************************************
 * Copyright (c) 2011, 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.punchy.ece13;

import java.io.File;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class FileUploadExamplePage {

  private static final String INITIAL_TEXT = "no files uploaded.";
  private FileUpload fileUpload;
  private Label fileNameLabel;
  private Button uploadButton;
  private Text logText;

  public Composite createControl( Composite parent ) {
    GridLayout gridLayout = new GridLayout( 1, true );
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 0;
    gridLayout.marginTop = 0;
    gridLayout.verticalSpacing = 60;
    gridLayout.horizontalSpacing = 60;
    GridLayout createMainLayout = gridLayout;
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( createMainLayout );
    createAddMultiButton( composite );
    Control serverColumn = createLogColumn( composite );
    GridData layoutData = new GridData( 300, 200 );
    layoutData.horizontalAlignment = SWT.CENTER;
    serverColumn.setLayoutData( layoutData );
    return composite;
  }

  private static GridData createFillData() {
    GridData fillData = new GridData( SWT.FILL, SWT.FILL, true, true );
    return fillData;
  }

  private static GridData createHorzFillData() {
    GridData fillData = new GridData( SWT.FILL, SWT.TOP, true, false );
    return fillData;
  }

  private Control createLogColumn( Composite parent ) {
    logText = new Text( parent, SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.BORDER );
    logText.setText( INITIAL_TEXT );
    return logText;
  }


  private void addToLog( final String message ) {
    if( !logText.isDisposed() ) {
      logText.getDisplay().asyncExec( new Runnable() {
        public void run() {
          String text = logText.getText();
          if( INITIAL_TEXT.equals( text ) ) {
            text = "";
          }
          logText.setText( text + message + "\n" );
        }
      } );
    }
  }

  private void createAddMultiButton( Composite parent ) {
    Button button = new Button( parent, SWT.PUSH );
    button.setLayoutData( createHorzFillData() );
    button.setText( "Open Files" );
    button.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, true, false ) );
    final Shell parentShell = parent.getShell();
    button.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        openFileDialog( parentShell, true );
      }
    } );
  }

  private void openFileDialog( Shell parent, boolean multi ) {
    final FileDialog fileDialog = new FileDialog( parent, getDialogStyle( multi ) );
    fileDialog.setText( multi ? "Upload Multiple Files" : "Upload Single File" );
    DialogUtil.open( fileDialog, new DialogCallback() {
      public void dialogClosed( int returnCode ) {
        showUploadResults( fileDialog );
      }
    } );
  }

  private void showUploadResults( FileDialog fileDialog ) {
    String[] selectedFiles = fileDialog.getFileNames();
    for( String fileName : selectedFiles ) {
      addToLog( "received: " + new File( fileName ).getAbsolutePath() );
    }
  }

  private static int getDialogStyle( boolean multi ) {
    int result = SWT.SHELL_TRIM | SWT.APPLICATION_MODAL;
    if( multi ) {
      result |= SWT.MULTI;
    }
    return result;
  }

}
