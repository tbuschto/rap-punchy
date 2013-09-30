package com.eclipsesource.rap.punchy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.lifecycle.WidgetUtil;
import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;


public class Presentation {


  private static final String IMAGE_KEY = "image";
  private final Composite main;
  private Listener buttonMouseMoveListener;
  private Listener buttonMouseExitListener;
  private Composite stage;
  private Composite prev;
  private Composite next;
  private final List<AbstractSlide> slides;
  private final Point minsize;
  private int slideIndex = -1;
  private Composite currentSlideControl;
  private Menu menu;
  private boolean maximized = true;
  private MenuItem prevItem;
  private MenuItem nextItem;
  private final Label warning;

  public Presentation( Composite parent, Point size ) {
    main = new Composite( parent, SWT.NONE );
    minsize = size;
    slides = new ArrayList<AbstractSlide>();
    createPrevButton( main );
    createStage( main );
    warning = new Label( main, SWT.CENTER );
    warning.setText( "Minimum size " + size );
    warning.setVisible( false );
    warning.setForeground( new Color( parent.getDisplay(), 255, 40, 40 ) );
    createMenu( main );
    createNextButton( main );
    main.addListener( SWT.Resize, getMainResizeListener() );
    main.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    WidgetUtil.registerDataKeys( IMAGE_KEY );
  }

  public Control getControl() {
    return main;
  }

  public void start() {
    checkNotStarted();
    initialize();
    showSlide( 0 );
  }

  public void next() {
    checkStarted();
    showSlide( slideIndex + 1 );
  }

  public void prev() {
    checkStarted();
    showSlide( slideIndex - 1 );
  }

  void addSlide( AbstractSlide slide ) {
    checkNotStarted();
    slides.add( slide );
  }

  private void createMenu( Composite parent ) {
    menu = new Menu( parent );
    // RAP Bug: Wanted to use accelerator does not convert correctly for Key left/right/space?
    prevItem = new MenuItem( menu, SWT.PUSH );
    prevItem.setText( "Previous\tCtrl + Shift + Space" );
    //prevItem.setAccelerator( SWT.CONTROL | SWT.SHIFT | ' ' );
    prevItem.setSelection( true );
    prevItem.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        prev();
      }
    } );
    nextItem = new MenuItem( menu, SWT.PUSH );
    nextItem.setText( "Next\tCtrl + Space" );
    //nextItem.setAccelerator( SWT.CONTROL | ' ' );
    nextItem.setSelection( true );
    nextItem.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        next();
      }
    } );
    final MenuItem maxItem = new MenuItem( menu, SWT.CHECK );
    maxItem.setText( "Maximized\tCtrl + M" );
    maxItem.setAccelerator( SWT.CONTROL | 'M' );
    maxItem.setSelection( true );
    maxItem.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        maximized = maxItem.getSelection();
        internalLayout();
      }
    } );
  }

  private void initialize() {
    // TODO Auto-generated method stub
  }

  private void checkStarted() {
    if( slideIndex < 0 ) {
      throw new IllegalStateException( "Operation not possible before presentation is started" );
    }
  }
  private void checkNotStarted() {
    if( slideIndex >= 0 ) {
      throw new IllegalStateException( "Operation not possible after presentation is started" );
    }
  }

  private void showSlide( int index ) {
    if( currentSlideControl != null ) {
      currentSlideControl.setMenu( null ); // do not dispose the menu!
      currentSlideControl.dispose();
    }
    slideIndex = index;
    currentSlideControl = slides.get( slideIndex ).create( stage );
    currentSlideControl.setMenu( menu );
    stage.layout();
    updateNavigation();
  }

  private void updateNavigation() {
    if( slideIndex > 0 ) {
      enableButton( prev );
      prevItem.setEnabled( true );
    } else {
      disableButton( prev );
      prevItem.setEnabled( false );
    }
    if( slideIndex < slides.size() - 1 ) {
      enableButton( next );
      nextItem.setEnabled( true );
    } else {
      disableButton( next );
      nextItem.setEnabled( false );
    }
  }

  private void createStage( Composite parent ) {
    stage = new Composite( parent, SWT.NONE );
    stage.setLayout( new FillLayout() );
    stage.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
  }

  private void createPrevButton( Composite parent ) {
    prev = createCompositeButton( parent, "left.png", SWT.LEFT );
    Listener listener = new Listener() {
      @Override
      public void handleEvent( Event event ) {
        prev();
      }
    };
    prev.addListener( SWT.MouseDown, listener );
    prev.getChildren()[ 0 ].addListener( SWT.MouseDown, listener );
  }

  private void createNextButton( Composite parent ) {
    next = createCompositeButton( parent, "right.png", SWT.RIGHT );
    Listener listener = new Listener() {
      @Override
      public void handleEvent( Event event ) {
        next();
      }
    };
    next.addListener( SWT.MouseDown, listener );
    next.getChildren()[ 0 ].addListener( SWT.MouseDown, listener );
  }

  private Composite createCompositeButton( Composite parent, String img, int align ) {
    Composite result = new Composite( parent, SWT.NONE );
    result.setEnabled( false );
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
    label.setVisible( false );
    result.setData( IMAGE_KEY, WidgetUtil.getId( label ) );
    return result;
  }

  void enableButton( Composite button ) {
    Control label = button.getChildren()[ 0 ];
    button.setCursor( button.getDisplay().getSystemCursor( SWT.CURSOR_HAND ) );
    label.setCursor( button.getDisplay().getSystemCursor( SWT.CURSOR_HAND ) );
    button.setEnabled( true );
  }

  void disableButton( Composite button ) {
    Control label = button.getChildren()[ 0 ];
    button.setCursor( null );
    label.setCursor( null );
    button.setEnabled( false );
    label.setVisible( false );
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
        internalLayout();
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
                      + "    image.setVisible( false );"
                      + "  }, 2000 );"
                      + "  image.setData( 'timer', timer );"
                      + "}";
      buttonMouseMoveListener = new ClientListener( script );
    }
    return buttonMouseMoveListener;
  }

  private Listener getButtonMouseExitListener() {
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

  private void internalLayout() {
    Point stageSize = computeStageSize();
    boolean stageVisible = stageSize.x >= minsize.x && stageSize.y >= minsize.y;
    stage.setVisible( stageVisible );
    warning.setVisible( !stageVisible );
    stage.setSize( stageSize );
    warning.setSize( stageSize );
    int buttonWidth = ( int )Math.ceil( ( main.getSize().x - stageSize.x ) / 2 );
    int buttonHeight = main.getSize().y;
    prev.setSize( buttonWidth, buttonHeight );
    next.setSize( buttonWidth, buttonHeight );
    prev.setLocation( 0, 0 );
    stage.setLocation( buttonWidth, 0 );
    warning.setLocation( buttonWidth, 0 );
    next.setLocation( buttonWidth + stageSize.x, 0 );
  }

  private Point computeStageSize() {
    Point size = main.getSize();
    if( maximized ){
      int stageHeight = size.y;
      int stageWidth = ( int )Math.ceil( stageHeight * minsize.x / minsize.y );
      return new Point( stageWidth, stageHeight );
    } else {
      int stageHeight = Math.min( size.y, minsize.y );
      int stageWidth = Math.min( size.x, minsize.x );
      return new Point( stageWidth, stageHeight );
    }
  }

  int getSlidesCount() {
    return slides.size();
  }

  int indexOfSlide( AbstractSlide slide ) {
    return slides.indexOf( slide );
  }

}
