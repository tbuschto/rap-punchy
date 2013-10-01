package com.eclipsesource.rap.punchy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.lifecycle.WidgetUtil;
import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


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
  private Label warning;
  private List<Control> menuControls = new ArrayList<Control>( 10 );
  private Table slideList;

  public Presentation( Composite parent, Point size ) {
    main = new Composite( parent, SWT.NONE );
    minsize = size;
    slides = new ArrayList<AbstractSlide>();
    createPrevButton( main );
    createStage( main );
    createWarning( parent, size );
    createMenu( main );
    createNextButton( main );
    createSlideList( stage );
    main.addListener( SWT.Resize, getMainResizeListener() );
    main.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    WidgetUtil.registerDataKeys( IMAGE_KEY );
  }

  private void createSlideList( Composite parent ) {
    slideList = new Table( parent, SWT.V_SCROLL | SWT.BORDER );
    FormData layoutData = new FormData();
    layoutData.left = new FormAttachment( 0 );
    layoutData.top = new FormAttachment( 0 );
    layoutData.bottom = new FormAttachment( 100 );
    layoutData.width = 300;
    slideList.setLayoutData( layoutData  );
    slideList.setVisible( false );
    slideList.addFocusListener( new FocusAdapter() {
      @Override
      public void focusLost( FocusEvent event ) {
        slideList.setVisible( false );
      }
    } );
    slideList.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        showSlide( slideList.getSelectionIndex() );
        slideList.setVisible( false );
      }
    } );
  }

  private void createWarning( Composite parent, Point size ) {
    warning = new Label( main, SWT.CENTER );
    warning.setText( "Minimum size " + size );
    warning.setVisible( false );
    warning.setForeground( new Color( parent.getDisplay(), 255, 40, 40 ) );
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
    Display.getCurrent().setData( RWT.ACTIVE_KEYS, new String[]{
      "CTRL+ARROW_LEFT",
      "CTRL+ARROW_RIGHT"
    } );
    Display.getCurrent().addFilter( SWT.KeyDown, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        if( event.stateMask == SWT.CTRL ) {
          if( event.keyCode == SWT.ARROW_LEFT ) {
            checkedPrev();
          } else if( event.keyCode == SWT.ARROW_RIGHT ) {
            checkedNext();
          }
        }
      }
    });
    prevItem = new MenuItem( menu, SWT.PUSH );
    prevItem.setText( "Previous\tCtrl + Left" );
    prevItem.setSelection( true );
    prevItem.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        prev();
      }
    } );
    nextItem = new MenuItem( menu, SWT.PUSH );
    nextItem.setText( "Next\tCtrl + Right" );
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
    final MenuItem slidesItem = new MenuItem( menu, SWT.PUSH );
    slidesItem.setText( "Slides\tCtrl + S" );
    slidesItem.setAccelerator( SWT.CONTROL | 'S' );
    slidesItem.setSelection( true );
    slidesItem.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        slideList.setVisible( true );
        slideList.forceFocus();
      }
    } );
  }

  private void initialize() {
    for( Iterator<AbstractSlide> iterator = slides.iterator(); iterator.hasNext(); ) {
      TableItem tableItem = new TableItem( slideList, SWT.NONE );
      tableItem.setText( iterator.next().getTitle() );
    }
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
    slideList.setSelection( index );
    if( currentSlideControl != null ) {
      detachMenu();
      currentSlideControl.dispose();
    }
    slideIndex = index;
    currentSlideControl = slides.get( slideIndex ).create( stage );
    FormData layoutData = createFillFormData();
    currentSlideControl.setLayoutData( layoutData );
    slideList.moveAbove( currentSlideControl );
    addPresentationMenu( currentSlideControl );
    stage.layout();
    updateNavigation();
  }

  private static FormData createFillFormData() {
    FormData layoutData = new FormData();
    layoutData.left = new FormAttachment( 0 );
    layoutData.top = new FormAttachment( 0 );
    layoutData.right = new FormAttachment( 100 );
    layoutData.bottom = new FormAttachment( 100 );
    return layoutData;
  }

  void addPresentationMenu( Control control ) {
    control.setMenu( menu );
    menuControls.add( control );
  }

  private void detachMenu() {
    // Otherwise the mnenu gets disposed... bug?
    for( Iterator<Control> iterator = menuControls.iterator(); iterator.hasNext(); ) {
      iterator.next().setMenu( null );
    }
    menuControls = new ArrayList<Control>( 10 );
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
    stage.setLayout( new FormLayout() );
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

  private void checkedNext() {
    if( slideIndex < slides.size() - 1 ) {
      next();
    }
  }

  private void checkedPrev() {
    if( slideIndex > 0 ) {
      prev();
    }
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
