package com.eclipsesource.rap.punchy;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.lifecycle.WidgetUtil;
import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;


public class Presentation {


  private static final String IMAGE_KEY = "image";
  private final Composite main;
  private Listener buttonMouseMoveListener;
  private Listener buttonMouseExitListener;
  private Listener buttonLabelMouseHoverListener;
  private static Composite stage;
  private static Composite prev;
  private static Composite next;

  public Presentation( Composite parent ) {
    main = new Composite( parent, SWT.NONE );
    createPrevButton( main );
    createStage( main );
    createNextButton( main );
    main.addListener( SWT.Resize, getMainResizeListener() );
    main.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    WidgetUtil.registerDataKeys( IMAGE_KEY );
  }

  public Control getControl() {
    return main;
  }

  public void next() {
    System.out.println( "next" );
  }

  public void prev() {
    System.out.println( "prev" );
  }

  private Point computeStageSize() {
    Point size = main.getSize();
    int stageHeight = size.y;
    int stageWidth = ( int )Math.ceil( stageHeight * 4 / 3 );
    return new Point( stageWidth, stageHeight );
  }

  private static void createStage( Composite parent ) {
    stage = new Composite( parent, SWT.NONE );
    stage.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLUE ) );
  }

  private void createPrevButton( Composite parent ) {
    prev = createCompositeButton( parent, "left.png", SWT.LEFT );
    prev.getChildren()[ 0 ].addListener( SWT.MouseDown, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        prev();
      }
    } );
  }

  private void createNextButton( Composite parent ) {
    next = createCompositeButton( parent, "right.png", SWT.RIGHT );
    next.getChildren()[ 0 ].addListener( SWT.MouseDown, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        next();
      }
    } );
  }

  private Composite createCompositeButton( Composite parent, String img, int align ) {
    Composite result = new Composite( parent, SWT.NONE );
    result.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    result.setBackgroundMode( SWT.INHERIT_FORCE );
    result.addListener( SWT.MouseMove, getButtonMouseMoveListener() );
    result.addListener( SWT.MouseExit, getButtonMouseExitListener() );
    GridLayout layout = new GridLayout( 1, true );
    layout.marginRight = 20;
    layout.marginLeft = 20;
    result.setLayout( layout );
    Label label = new Label( result, align );
    label.setImage( getImage( img ) );
    label.setLayoutData( new GridData( align, SWT.CENTER, true, true ) );
    label.setCursor( parent.getDisplay().getSystemCursor( SWT.CURSOR_HAND ) );
    label.setVisible( false );
    label.addListener( SWT.MouseExit, getButtonLabelMouseHoverListener() );
    label.addListener( SWT.MouseEnter, getButtonLabelMouseHoverListener() );
    result.setData( IMAGE_KEY, WidgetUtil.getId( label ) );
    return result;
  }

  private static Image getImage( String name ) {
    InputStream stream = Presentation.class.getResourceAsStream( name );
    Image image = new Image( Display.getCurrent(), stream );
    try {
      stream.close();
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
    return image;
  }

  private Listener getMainResizeListener() {
    return new Listener() {
      @Override
      public void handleEvent( Event event ) {
        Point stageSize = computeStageSize();
        stage.setSize( stageSize );
        int buttonWidth = ( int )Math.ceil( ( main.getSize().x - stageSize.x ) / 2 );
        int buttonHeight = main.getSize().y;
        prev.setSize( buttonWidth, buttonHeight );
        next.setSize( buttonWidth, buttonHeight );
        prev.setLocation( 0, 0 );
        stage.setLocation( buttonWidth, 0 );
        next.setLocation( buttonWidth + stageSize.x, 0 );
      }
    };
  }

  private Listener getButtonMouseMoveListener() {
    // Note crashing if failing? Not rendered for Composite?
    if( buttonMouseMoveListener == null ) {
      String script =   "function handleEvent( event ) {"
                      + "  var id = event.widget.getData( '" + IMAGE_KEY + "' );"
                      + "  var image = rap.getObject( id );"
                      + "  image.setVisible( true );"
                      + "  if( image.getData( 'timer' ) ) {"
                      + "    window.clearTimeout( image.getData( 'timer' ) );"
                      + "  }"
                      + "  var timer = window.setTimeout( function() {"
                      + "    if( !image.getData( 'hover' ) ) {"
                      + "      image.setVisible( false );"
                      + "    }"
                      + "  }, 2000 );"
                      + "  image.setData( 'timer', timer );"
                      + "}";
      buttonMouseMoveListener = new ClientListener( script );
    }
    return buttonMouseMoveListener;
  }

  private Listener getButtonMouseExitListener() {
    // Note crashing if failing? Not rendered for Composite?
    if( buttonMouseExitListener == null ) {
      String script =   "function handleEvent( event ) {"
                      + "  var id = event.widget.getData( '" + IMAGE_KEY + "' );"
                      + "  var image = rap.getObject( id );"
                      + "  image.setVisible( false );"
                      + "}";
      buttonMouseExitListener = new ClientListener( script );
    }
    return buttonMouseExitListener;
  }

  private Listener getButtonLabelMouseHoverListener() {
    // Note crashing if failing? Not rendered for Composite?
    if( buttonLabelMouseHoverListener == null ) {
      String script =   "function handleEvent( event ) {"
                      + "  event.widget.setData( 'hover', event.type === SWT.MouseEnter ? true : false );"
                      + "}";
      buttonLabelMouseHoverListener = new ClientListener( script );
    }
    return buttonLabelMouseHoverListener;
  }

}
