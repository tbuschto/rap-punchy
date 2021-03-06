package com.eclipsesource.rap.punchy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;


public abstract class AbstractSlide {

  private final Presentation presentation;
  private Composite currentSlideComposite;
  private List<Control> flowWidgets;
  private int spacerSum = 0;
  private int defaultSpacing = 0;
  private int leftPadding = 0;
  private int rightPadding = 0;
  private int listSpacing = 0;

  public AbstractSlide( Presentation presentation ) {
    this.presentation = presentation;
    presentation.addSlide( this );
  }

  ///////////////
  // abstract API

  public abstract String getTitle();
  protected abstract void createContent( Composite slideComposite );

  //////////////////
  // Slide Creation

  protected int getSlidesCount() {
    return presentation.getSlidesCount();
  }

  protected int getSlideNumber() {
    return presentation.indexOfSlide( this );
  }

  protected void setSpacing( int spacing ) {
    defaultSpacing = spacing;
    spacer( spacing );
  }

  protected void setListSpacing( int spacing ) {
    listSpacing = spacing;
  }

  protected void setPaddingLeft( int value ) {
    leftPadding = value;
  }

  protected void setPaddingRight( int value ) {
    rightPadding = value;
  }

  public Presentation getPresentation() {
    return presentation;
  }

  protected Control text( String text ) {
    return text( SWT.DEFAULT, SWT.DEFAULT, SWT.WRAP, text );
  }

  protected Control text( int width, int height, String text ) {
    return text( width, height, SWT.WRAP, text );
  }

  protected Control text( int style, String text ) {
    return text( SWT.DEFAULT, SWT.DEFAULT, style, text );
  }

  protected Control text( int width, int height, int style, String text ) {
    return styledText( "text", width, height, style, text );
  }

  protected Control styledText( String style, String text ) {
    return styledText( style, SWT.DEFAULT, SWT.DEFAULT, SWT.WRAP, text );
  }

  protected Control styledText( String style, int styleFlag, String text ) {
    return styledText( style, SWT.DEFAULT, SWT.DEFAULT, styleFlag, text );
  }

  protected Control styledText( String style, int width, int height, String text ) {
    return styledText( style, width, height, SWT.WRAP, text );
  }

  protected Control styledText( String style, int width, int height, int styleFlag, String text ) {
    Control control = html( text, styleFlag );
    flow( control, width, height );
    styleAs( style, control );
    return control;
  }

  protected Control list( Object... listItems ) {
    Control control = html( listHtml( false, listItems ), SWT.LEFT );
    control.setData( RWT.CUSTOM_VARIANT, "punchyList" );
    flow( control );
    return control;
  }

  protected Control image( String name ) {
    return image( name, SWT.LEFT );
  }

  protected Control image( String name, int style ) {
    Label label = new Label( currentSlideComposite, style );
    Image image = loadImage( name );
    label.setImage( image );
    label.setData( RWT.CUSTOM_VARIANT, "punchyImage" );
    Point size = label.computeSize( SWT.DEFAULT, SWT.DEFAULT );
    if( ( style & SWT.BORDER ) != 0 ) {
      // somehow layout gets of by 2px?
      size = new Point( size.x - 2, size.y - 2 );
    }
    flow( label, size.x, size.y );
    return label;
  }

  protected Image loadImage( String name ) {
    InputStream stream = getClass().getResourceAsStream( name );
    Image image = new Image( Display.getCurrent(), stream );
    try {
      stream.close();
    } catch( IOException e ) {
      e.printStackTrace();
    }
    return image;
  }

  protected void spacer( int spacer ) {
    this.spacerSum += spacer;
  }

  protected void toRight( Control control ) {
    if( flowWidgets.indexOf( control ) == -1) {
      flow( control );
    }
    if( flowWidgets.indexOf( control ) != flowWidgets.size() -1 ) {
      throw new IllegalStateException();
    }
    flowWidgets.remove( flowWidgets.size() -1  );
    FormData formData = ( FormData )control.getLayoutData();
    formData.left = null;
    formData.right = new FormAttachment( 100, rightPadding * -1 );
  }

  protected void styleAs( String style, Control... control ) {
    String variant = "punchy" + style.substring( 0, 1 ).toUpperCase() + style.substring( 1 );
    for( int i = 0; i < control.length; i++ ) {
      control[ i ].setData( RWT.CUSTOM_VARIANT, variant );
    }
  }

  protected void addPresentationMenu( Control control ) {
    presentation.addPresentationMenu( control );
  }

  protected void snippet( final String lang, int width, int height, final String source ) {
    String doc = new HtmlDocument() {
      @Override
      public void html() {
        head.innerHTML(
          link().type( "text/css" ).href( locationOf( "prettify.css" ) ).rel( "stylesheet" ),
          script().type( "text/javascript" ).src( locationOf( "prettify.js" ) )
        );
        body.attr( "onload", "prettyPrint()" );
        body.style( "padding-left:6px" );
        body.innerHTML(
          pre().cssClass( "prettyprint lang-" + lang ).innerHTML(
            escape( source )
          )
        );
      }
    }.toString();
    Browser browser = new Browser( currentSlideComposite, SWT.BORDER );
    browser.setText( doc.toString() );
    styleAs( "snippet", browser );
    flow( browser, width, height );
  }

  protected void flow( Control control ) {
    flow( control, -1, -1 );
  }

  protected void flow( String style, Control control ) {
    flow( control, -1, -1 );
    styleAs( style, control );
  }

  protected void flow( Control control, int width ) {
    flow( control, width, -1 );
  }

  protected void flow( String style, Control control, int width ) {
    flow( control, width, -1 );
    styleAs( style, control );
  }

  protected void flow( String style, Control control, int width, int height ) {
    flow( control, width, height );
    styleAs( style, control );
  }

  protected void flow( Control control, int width, int height ) {
    if( flowWidgets.indexOf( control ) != -1 ) {
      throw new IllegalStateException();
    }
    FormData formData = new FormData();
    if( !flowWidgets.isEmpty() ) {
      formData.top = new FormAttachment( flowWidgets.get( flowWidgets.size() - 1 ), spacerSum );
    } else {
      formData.top = new FormAttachment( 0, spacerSum );
    }
    spacerSum = defaultSpacing;
    formData.left = new FormAttachment( 0, leftPadding );
    if( width >= 0 ) {
      formData.width = width;
    } else {
      formData.right = new FormAttachment( 100, rightPadding * -1 );
    }
    if( height >= 0 ) {
      formData.height = height;
    }
    control.setLayoutData( formData );
    flowWidgets.add( control );
  }

  protected void toBottom( Control control, int width, int height ) {
    FormData formData = new FormData();
    formData.bottom = new FormAttachment( 100 );
    formData.left = new FormAttachment( 0 );
    if( width >= 0 ) {
      formData.width = width;
    } else {
      formData.right = new FormAttachment( 100 );
    }
    if( height >= 0 ) {
      formData.height = height;
    }
    control.setLayoutData( formData );
  }

  //////////////////
  // Package Private

  Composite create( Composite stage ) {
    reset();
    Composite slideComposite = new Composite( stage, SWT.NONE );
    slideComposite.setLayout( new FormLayout() );
    currentSlideComposite = slideComposite;
    createContent( slideComposite );
    return slideComposite;
  }

  ////////////
  // Internals


  private Control html( Object content, int style ) {
    Label label = new Label( currentSlideComposite, style );
    label.setText( content.toString() );
    label.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    return label;
  }

  private void reset() {
    currentSlideComposite = null;
    flowWidgets = new ArrayList<Control>();
    spacerSum = 0;
    defaultSpacing = 0;
    leftPadding = 0;
    rightPadding = 0;
  }

  private String listHtml( boolean sublist, Object... listItems ) {
    StringBuilder htmlBuilder = new StringBuilder();
    String style = "style=\"margin:0px";
    if( sublist ) {
      style += ";margin-top:" + listSpacing + "px";
    }
    style += "\"";
    htmlBuilder.append( "<ul " + style + ">" );
    boolean merge = false;
    for( int i = 0; i < listItems.length; i++ ) {
      int actualSpacing = i == listItems.length - 1 ? 0 : listSpacing;
      if( !merge ) {
        htmlBuilder.append( "<li style=\"margin-bottom:" + actualSpacing  + "px\" >" );
      }
      merge = false;
      if( listItems[ i ] instanceof String[] ) {
        htmlBuilder.append( listHtml( true, ( Object[] )listItems[ i ] ) );
      } else {
        htmlBuilder.append( listItems[ i ] );
      }
      if( i < listItems.length - 1 && listItems[ i + 1 ] instanceof String[] ) {
        merge = true;
      } else {
        htmlBuilder.append( "</li>" );
      }
    }
    htmlBuilder.append( "</ul>" );
    String html = htmlBuilder.toString();
    return html;
  }

}
