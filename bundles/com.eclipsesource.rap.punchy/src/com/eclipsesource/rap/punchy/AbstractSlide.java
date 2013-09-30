package com.eclipsesource.rap.punchy;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
  protected abstract void createContent( Composite slideControl );

  public Presentation getPresentation() {
    return presentation;
  }

  protected Control title() {
    Control control = html( getTitle() );
    control.setData( RWT.CUSTOM_VARIANT, "punchyTitle" );
    return control;
  }

  protected void spacer( int spacer ) {
    this.spacer += spacer;
  }

  protected Control list( String... listItems ) {
    StringBuilder htmlBuilder = new StringBuilder();
    htmlBuilder.append( "<ul>" );
    for( int i = 0; i < listItems.length; i++ ) {
      htmlBuilder.append( "<li>" );
      htmlBuilder.append( listItems[ i ] );
      htmlBuilder.append( "</li>" );
    }
    htmlBuilder.append( "</ul>" );
    Control control = html( htmlBuilder );
    control.setData( RWT.CUSTOM_VARIANT, "punchyList" );
    return control;
  }

  protected Control html( Object content ) {
    Label label = new Label( currentSlideComposite, SWT.WRAP );
    label.setText( content.toString() );
    label.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    flow( label );
    return label;
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

}
