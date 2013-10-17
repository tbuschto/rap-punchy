package com.eclipsesource.rap.punchy.ece13;

import static com.eclipsesource.rap.punchy.ece13.ResourceLoaderUtil.readFile;

import org.eclipse.rap.rwt.lifecycle.WidgetUtil;
import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.eclipsesource.rap.punchy.Presentation;


public class RAP_2_2_Slides {


  public static class DigitsOnlyListener implements Listener {

    @Override
    public void handleEvent( Event event ) {
      Text text = ( Text )event.widget;
      if( text.getText().matches( "^[0-9]*$" ) == false ) {
        text.setBackground( new Color( Display.getCurrent(), 255, 255, 128 ) );
      } else {
        text.setBackground( null );
      }
    }
  }

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.2";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 25 );
        list(
         "December 2013",
         "RWT Scripting",
         "New ToolTips",
         "Row Templates"
        );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RWT Scripting";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 20 );
        list(
         "Formerly \"ClientScripting\" (Incubator)",
         "SWT-Style event handler written in JavaScript",
         "Executed in the Browser (no latency)"
        );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RWT Scripting Example";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 10 );
        list( "Input Validation (digity only) in Java:" );
        setPaddingLeft( 60 );
        Text text1 = new Text( slideComposite, SWT.BORDER );
        text1.addListener( SWT.Modify, new DigitsOnlyListener() );
        flow( text1 );
        setPaddingLeft( 30 );
        spacer( 10 );
        list( "Input Validation with Scripting:" );
        setPaddingLeft( 60 );
        Text text2 = new Text( slideComposite, SWT.BORDER );
        text2.addListener( SWT.Modify, new ClientListener( readFile( "DigitsOnly.js" ) ) );
        flow( text2 );
        setPaddingLeft( 30 );
        spacer( 10 );
        list( "Event Registration:" );
        setPaddingLeft( 60 );
        snippet(
          "java", SWT.DEFAULT, 120,
          "  Text text1 = new Text( slideComposite, SWT.BORDER );\n" +
          "  text1.addListener( SWT.Modify, new DigitsOnlyListener() );\n\n" +
          "  Text text2 = new Text( slideComposite, SWT.BORDER );\n" +
          "  text2.addListener( SWT.Modify, new ClientListener( readFile( \"DigitsOnly.js\" ) ) );\n"
        );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RWT Scripting Example Source";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 10 );
        text( "Listener in Java:" );
        setPaddingLeft( 60 );
        snippet(
          "java", SWT.DEFAULT, 160,
          "  public void handleEvent( Event event ) {\n" +
          "    Text text = ( Text )event.widget;\n" +
          "    if( text.getText().matches( \"^[0-9]*$\" ) == false ) {\n" +
          "      text.setBackground( new Color( Display.getCurrent(), 255, 255, 128 ) );\n" +
          "    } else {\n" +
          "      text.setBackground( null );\n" +
          "    }\n" +
          "  }"
         );
        setPaddingLeft( 30 );
        text( "Listener in JavaScript:" );
        setPaddingLeft( 60 );
        snippet( "javascript", SWT.DEFAULT, 160, readFile( "DigitsOnly.js" ) );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RWT Scripting";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 20 );
        list(
         "Currently 9 widgets and 19 event types supported",
         "Widgets can not be created/disposed by Scripting",
         "Some more Examples:"
        );
        Composite parent = new Composite( slideComposite, SWT.NONE );
        parent.setLayout( new GridLayout( 2, false ) );
        addDateFieldExample( parent );
        addNumpadExample( parent );
        addFocusSwitchExample( parent );
        addCanvasExample( parent );
        flow( "transparent", parent );
      }
    };
  }

  private static void addFocusSwitchExample( Composite parent ) {
    Composite area = new Composite( parent, SWT.NONE );
    area.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    GridLayout layout = new GridLayout( 2, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    area.setLayout( layout );
    Text text = new Text( area, SWT.BORDER );
    text.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    Text next = new Text( area, SWT.BORDER );
    next.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    CustomBehaviors.addFocusNextBehavior( text, next );
    CustomBehaviors.addFocusPreviousBehavior( next, text );
  }

  private static void addDateFieldExample( Composite parent ) {
    final Text text = new Text( parent, SWT.BORDER );
    CustomBehaviors.addDateFieldBehavior( text );
    //addDateValidator( text );
    text.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
  }

  private static void addCanvasExample( Composite parent ) {
    Canvas canvas = new Canvas( parent, SWT.BORDER );
    canvas.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    CustomBehaviors.addPaintingBehavior( canvas );
  }

  private static void addNumpadExample( Composite parent ) {
    Composite pad = new Composite( parent, SWT.BORDER );
    pad.setLayout( new GridLayout( 3, true ) );
    GridData padLayoutData = new GridData( SWT.LEFT, SWT.TOP, false, false );
    padLayoutData.verticalSpan = 3;
    pad.setLayoutData( padLayoutData );
    Text text = new Text( pad, SWT.BORDER );
    GridData texLayoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
    texLayoutData.horizontalSpan = 3;
    text.setLayoutData( texLayoutData );
    text.setEditable( false );
    createNumKeys( pad, text );
  }

  private static void createNumKeys( Composite parent, Text text ) {
    WidgetUtil.registerDataKeys( "textWidget" );
    WidgetUtil.registerDataKeys( "numValue" );
    int[] numbers = new int[]{ 7, 8, 9, 4, 5, 6, 1, 2, 3 };
    ClientListener listener = new ClientListener( ResourceLoaderUtil.readFile( "NumKey.js" ) );
    for( int i = 0; i < numbers.length; i++ ) {
      createNumButton( parent, text, numbers[ i ], listener );
    }
    createNumButton( parent, text, -1, listener ).setText( "C" );
    createNumButton( parent, text, 0, listener );
    createNumButton( parent, text, -2, listener ).setText( "." );
  }


  private static Button createNumButton( Composite parent, Text text, int number, Listener listener ) {
    Button button = new Button( parent, SWT.PUSH );
    button.setText( String.valueOf( number ) );
    button.setLayoutData( new GridData( 40, 35 ) );
    button.setData( "textWidget", WidgetUtil.getId( text ) );
    button.setData( "numValue", Integer.valueOf( number ) );
    button.addListener( SWT.MouseDown, listener );
    return button;
  }

  private static void addDateValidator( final Text text ) {
    text.addFocusListener( new FocusAdapter() {
      Color color = new Color( text.getDisplay(), 255, 128, 128 );
      @Override
      public void focusLost( FocusEvent event ) {
        if( !verifyDate( text.getText() ) ) {
          text.setBackground( color );
        } else {
          text.setBackground( null );
        }
      }
    } );
  }

  private static boolean verifyDate( String date ) {
    String[] values = date.split( "\\.", 3 );
    boolean valid = true;
    try {
      if( Integer.parseInt( values[ 0 ] ) > 31 ) {
        valid = false;
      }
      if( Integer.parseInt( values[ 1 ] ) > 12 ) {
        valid = false;
      }
      Integer.parseInt( values[ 2 ].trim() ); // remove trailing " "
    } catch( NumberFormatException ex ) {
      valid = false;
    }
    return valid;
  }

}
