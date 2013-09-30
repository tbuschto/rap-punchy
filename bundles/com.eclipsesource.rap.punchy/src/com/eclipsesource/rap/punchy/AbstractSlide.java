package com.eclipsesource.rap.punchy;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
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
  private Control currentFlowWidget;
  private int spacer = 0;

  public AbstractSlide( Presentation presentation ) {
    this.presentation = presentation;
    presentation.addSlide( this );
  }

  public abstract String getTitle();
  protected abstract void createContent( Composite slideComposite );


  protected int getSlidesCount() {
    return presentation.getSlidesCount();
  }

  protected int getSlideNumber() {
    return presentation.indexOfSlide( this );
  }

  public Presentation getPresentation() {
    return presentation;
  }

  protected Control title() {
    Control control = html( getTitle(), SWT.LEFT );
    control.setData( RWT.CUSTOM_VARIANT, "punchyTitle" );
    return control;
  }

  protected Control text( String text ) {
    return styledText( "text", text );
  }

  protected Control styledText( String style, String text ) {
    Control control = html( text, SWT.WRAP );
    styleAs( style, control );
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
    flow( label );
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

  protected Control list( Object... listItems ) {
    Control control = html( listHtml( listItems ), SWT.LEFT );
    control.setData( RWT.CUSTOM_VARIANT, "punchyList" );
    return control;
  }
  protected Control html( Object content, int style ) {
    Label label = new Label( currentSlideComposite, style );
    label.setText( content.toString() );
    label.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    flow( label );
    return label;
  }

  protected void spacer( int spacer ) {
    this.spacer += spacer;
  }

  protected void floatRight( Control control ) {

  }

  protected void clear( Control control ) {

  }

  protected void styleAs( String style, Control... control ) {
    String variant = "punchy" + style.substring( 0, 1 ).toUpperCase() + style.substring( 1 );
    for( int i = 0; i < control.length; i++ ) {
      control[ i ].setData( RWT.CUSTOM_VARIANT, variant );
    }
  }

  protected void flow( Control control ) {
    flow( control, -1, -1 );
  }

  protected void flow( Control control, int width ) {
    flow( control, width, -1 );
  }

  protected void flow( Control control, int width, int height ) {
    FormData formData = new FormData();
    if( currentFlowWidget != null) {
      formData.top = new FormAttachment( currentFlowWidget, spacer );
    } else {
      formData.top = new FormAttachment( 0, spacer );
    }
    spacer = 0;
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
    currentFlowWidget = control;
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

  Composite create( Composite stage ) {
    Composite slideComposite = new Composite( stage, SWT.NONE );
    slideComposite.setData( RWT.CUSTOM_VARIANT, "punchySlide" );
    slideComposite.setLayout( new FormLayout() );
    currentSlideComposite = slideComposite;
    createContent( slideComposite );
    currentSlideComposite = null;
    currentFlowWidget = null;
    return slideComposite;
  }

  private String listHtml( Object... listItems ) {
    StringBuilder htmlBuilder = new StringBuilder();
    htmlBuilder.append( "<ul style=\"margin:0px\">" );
    boolean merge = false;
    for( int i = 0; i < listItems.length; i++ ) {
      if( !merge ) {
        htmlBuilder.append( "<li>" );
      }
      merge = false;
      if( listItems[ i ] instanceof String[] ) {
        htmlBuilder.append( listHtml( ( Object[] )listItems[ i ] ) );
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
