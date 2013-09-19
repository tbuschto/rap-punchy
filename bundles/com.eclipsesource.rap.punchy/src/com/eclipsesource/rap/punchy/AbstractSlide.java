package com.eclipsesource.rap.punchy;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public abstract class AbstractSlide {

  private final Presentation presentation;
  private Composite currentSlideComposite;

  public AbstractSlide( Presentation presentation ) {
    this.presentation = presentation;
    presentation.addSlide( this );
  }

  public abstract String getTitle();
  protected abstract void createContent( Composite slideControl );

  public Presentation getPresentation() {
    return presentation;
  }

  protected void title() {
    Label title = new Label( currentSlideComposite, SWT.LEFT );
    FormData formData = new FormData();
    formData.left = new FormAttachment( 0 );
    formData.top = new FormAttachment( 0 );
    title.setLayoutData( formData );
    title.setText( getTitle() );
    title.setData( RWT.CUSTOM_VARIANT, "punchyTitle" );
  }

  Composite create( Composite stage ) {
    Composite slideComposite = new Composite( stage, SWT.NONE );
    slideComposite.setData( RWT.CUSTOM_VARIANT, "punchySlide" );
    slideComposite.setLayout( new FormLayout() );
    currentSlideComposite = slideComposite;
    createContent( slideComposite );
    return slideComposite;
  }

}
