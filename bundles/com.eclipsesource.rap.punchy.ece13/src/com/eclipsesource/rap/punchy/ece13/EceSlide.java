package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.rap.punchy.AbstractSlide;
import com.eclipsesource.rap.punchy.Presentation;


public abstract class EceSlide extends AbstractSlide {

  public EceSlide( Presentation presentation ) {
    super( presentation );
  }

  @Override
  protected void createContent( Composite slideComposite ) {
    header( slideComposite );
    setSpacing( 8 );
    spacer( 20 );
    footer( slideComposite );
    setIndent( 30 );
  }

  private void header( Composite slideComposite ) {
    Composite header = new Composite( slideComposite, SWT.NONE );
    GridLayout layout = new GridLayout( 2, false );
    layout.marginBottom = 0;
    layout.marginTop = 7;
    layout.marginRight = 15;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.marginLeft = 18;
    layout.verticalSpacing = 0;
    header.setLayout( layout );
    Label title = new Label( header, SWT.LEFT );
    title.setText( getTitle() );
    title.setLayoutData( new GridData( SWT.LEFT, SWT.BOTTOM, true, true ) );
    styleAs( "title", title );
    header.setBackgroundMode( SWT.INHERIT_FORCE );
    styleAs( "header", header );
    Label logo = new Label( header, SWT.LEFT );
    logo.setImage( loadImage( "es_small.png" ) );
    logo.setLayoutData( new GridData( SWT.RIGHT, SWT.CENTER, false, false ) );
    flow( header, -1, 57 );
  }

  private void footer( Composite slideComposite ) {
    Composite footer = new Composite( slideComposite, SWT.NONE );
    GridLayout layout = new GridLayout( 1, false );
    layout.marginBottom = 4;
    layout.marginTop = 7;
    layout.marginLeft = 15;
    footer.setLayout( layout );
    footer.setBackgroundMode( SWT.INHERIT_FORCE );
    styleAs( "footer", footer );
    Label label = new Label( footer, SWT.LEFT );
    label.setText( ( getSlideNumber() + 1 ) + "/" + getSlidesCount() );
    styleAs( "footer", label );
    toBottom( footer, -1, 40 );
    addPresentationMenu( footer );
  }

}
