package com.eclipsesource.rap.punchy.ece13;

import static com.eclipsesource.rap.punchy.ece13.ResourceLoaderUtil.readFile;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.rap.rwt.lifecycle.WidgetUtil;
import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

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
          "Scheduled for December 2013",
          "Themes",
          new String[] {
            "RWT Scripting",
            "New ToolTips",
            "Row Templates"
          }
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
         "Formerly \"ClientScripting\" (RAP Incubator project)",
         "SWT-Style event handler written in JavaScript",
         "Executed in the Browser (no network latency)"
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
        spacer( 20 );
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
         "Currently 9 widgets and 19 event types",
         "Scripting can not create/dispose widgets",
         "Some more Examples:"
        );
        spacer( 20 );
        Composite parent = new Composite( slideComposite, SWT.NONE );
        parent.setLayout( new GridLayout( 2, false ) );
        addDateFieldExample( parent );
        addNumpadExample( parent );
        addFocusSwitchExample( parent );
        addCanvasExample( parent );
        flow( "transparent", parent );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "New ToolTips";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 20 );
        list(
          "New (default) look with pointer",
          "Revised, widget-specific behavior",
          "HTML/markup support planned"
        );
        spacer( 20 );
        Composite parent = new Composite( slideComposite, SWT.NONE );
        GridLayout layout = new GridLayout( 3, false );
        layout.verticalSpacing = 10;
        layout.horizontalSpacing = 10;
        parent.setLayout( layout );
        ToolBar toolBarLeft = new ToolBar( parent, SWT.VERTICAL | SWT.BORDER );
        GridData layoutData = new GridData( SWT.DEFAULT, SWT.FILL, false, true );
        layoutData.verticalSpan = 6;
        toolBarLeft.setLayoutData( layoutData );
        fillToolBar( toolBarLeft );
        ToolBar toolBarTop = new ToolBar( parent, SWT.HORIZONTAL | SWT.BORDER );
        GridData layoutData1 = new GridData( SWT.LEFT, SWT.DEFAULT, false, false );
        toolBarTop.setLayoutData( layoutData1 );
        fillToolBar( toolBarTop );
        createTabFolder( parent );
        createButton( parent, "Button" );
        styleAs( "transparent", createCheck( parent ) );
        createText( parent );
        ProgressBar bar = new ProgressBar( parent, SWT.HORIZONTAL );
        bar.setSelection( 40 );
        bar.setToolTipText( "hor" );
        addBarListener( bar );
        Scale scale = new Scale( parent, SWT.HORIZONTAL );
        scale.setToolTipText( "Scale" );
        styleAs( "transparent", scale );
        addScaleListener( scale );
        flow( "transparent", parent );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RowTemplates";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 20 );
        list(
          "Customize how tree/table items are rendered",
          "Make text/images clickable",
          "Pure Java API, no HTML/CSS/JS",
          "Possible future support for other widgets",
          "Tabris support"
        );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RowTemplates Example";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        Composite example = new Composite( slideComposite, SWT.NONE );
        new RowTemplateDemo().createContents( example );
        flow( "transparent", example, SWT.DEFAULT, 550 );
      }
    };
  }

  //////////////
  // Helper

  private static void fillToolBar( ToolBar toolbarTop ) {
    createToolItem( toolbarTop, "ICON_ERROR" );
    createToolItem( toolbarTop, "ICON_INFORMATION" );
    createToolItem( toolbarTop, "ICON_QUESTION" );
    createToolItem( toolbarTop, "ICON_WARNING" );
    createToolItem( toolbarTop, "ICON_WORKING" );
  }

  private static void createToolItem( ToolBar toolbar, String icon ) {
    try {
      int id = SWT.class.getField( icon ).getInt( null );
      ToolItem item = new ToolItem( toolbar, SWT.CHECK );
      item.setImage( Display.getCurrent().getSystemImage( id ) );
      item.setToolTipText( icon );
    } catch( IllegalArgumentException e ) {
      e.printStackTrace();
    } catch( SecurityException e ) {
      e.printStackTrace();
    } catch( IllegalAccessException e ) {
      e.printStackTrace();
    } catch( NoSuchFieldException e ) {
      e.printStackTrace();
    }
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


  private static void createTabFolder( Composite composite ) {
    TabFolder folder = new TabFolder( composite, SWT.NONE );
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    layoutData.verticalSpan = 6;
    folder.setLayoutData( layoutData );
    TabItem tabItem1 = new TabItem( folder, SWT.NONE );
    tabItem1.setText( "Create" );
    tabItem1.setToolTipText( "ToolTips" );
    TabItem tabItem2 = new TabItem( folder, SWT.NONE );
    tabItem2.setText( "Edit" );
    tabItem2.setToolTipText( "appear" );
    TabItem tabItem3 = new TabItem( folder, SWT.NONE );
    tabItem3.setText( "Publish" );
    tabItem3.setToolTipText( "quickliy..." );
    tabItem1.setControl( createTableViewer( folder ) );
    folder.setSelection( 0 );
  }

  private static Control createTableViewer( Composite composite ) {
    TableViewer table = new TableViewer( composite );
    table.getControl().setToolTipText( "Table" );
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    //layoutData.horizontalSpan = 3;
    table.getControl().setLayoutData( layoutData );
    table.setContentProvider( new ArrayContentProvider() );
    ColumnViewerToolTipSupport.enableFor( table );
    ( ( Table )table.getControl() ).setHeaderVisible( true );
    TableViewerColumn columnViewer = new TableViewerColumn( table, SWT.NONE );
    columnViewer.getColumn().setWidth( 100 );
    columnViewer.getColumn().setToolTipText( "a column" );
    columnViewer.getColumn().setText( "Col 1" );
    columnViewer.setLabelProvider( new ColumnLabelProvider(){
      @Override
      public String getToolTipText(Object element) {
        return "More about: " + element.toString();
      };
      @Override
      public String getText(Object element) {
        return element.toString();
      };
    } );
    TableViewerColumn columnViewerTwo = new TableViewerColumn( table, SWT.NONE );
    columnViewerTwo.getColumn().setWidth( 170 );
    columnViewerTwo.getColumn().setText( "Col 2" );
    columnViewerTwo.setLabelProvider( new ColumnLabelProvider(){
      @Override
      public String getToolTipText(Object element) {
        return "Even more about " + element.toString();
      };
      @Override
      public String getText(Object element) {
        return "Another " + element.toString();
      };
    } );
    TableViewerColumn columnViewer3= new TableViewerColumn( table, SWT.NONE );
    columnViewer3.getColumn().setWidth( 100 );
    columnViewer3.getColumn().setText( "Col 3" );
    columnViewer3.setLabelProvider( new ColumnLabelProvider() );
    table.setInput( new String[]{ "Data One ", "Data Two", "Data Three" } );
    return table.getControl();
  }

  private static void addScaleListener( Scale scale ) {
    String script =   "function handleEvent( event ) { "
                    + "  event.widget.setToolTipText( event.widget.getSelection() + \"%\" ); "
                    + "}";
    scale.addListener( SWT.Selection, new ClientListener( script ) );
  }

  private static void addBarListener( final ProgressBar bar ) {
    bar.addListener( SWT.MouseDown, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        int sel = bar.getSelection();
        sel += 10;
        if( sel > bar.getMaximum() ) {
          sel = 0;
        }
        bar.setSelection( sel );
        bar.setToolTipText( sel + "%" );
      }
    } );
  }

  private static Button createButton( Composite composite, String text ) {
    Button ok = new Button( composite, SWT.PUSH );
    ok.setText( text );
    ok.setToolTipText( "This is OK!" );
    return ok;
  }

  private static Button createCheck( Composite composite ) {
    Button check = new Button( composite, SWT.CHECK );
    check.setText( "check this out" );
    check.setToolTipText( "yohoo!" );
    return check;
  }

  private static Text createText( Composite composite ) {
    Text text = new Text( composite, SWT.BORDER );
    text.setText( "Text with text in the text" );
    text.setToolTipText( "I have nothing to say" );
    ControlDecoration decoration = new ControlDecoration( text, SWT.RIGHT | SWT.TOP );
    decoration.setDescriptionText( "Deco" );
    decoration.setMarginWidth( 2 );
    Image warningImage = getDecorationImage( FieldDecorationRegistry.DEC_WARNING );
    decoration.setImage( warningImage );
    return text;
  }

  private static Image getDecorationImage( String id ) {
    FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
    FieldDecoration decoration = registry.getFieldDecoration( id );
    return decoration.getImage();
  }

}
