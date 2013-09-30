package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
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
  }

  private void header( Composite slideComposite ) {
    Composite header = new Composite( slideComposite, SWT.NONE );
    GridLayout layout = new GridLayout( 1, false );
    layout.marginBottom = 6;
    layout.marginTop = 19;
    layout.marginLeft = 19;
    header.setLayout( layout );
    header.setBackgroundMode( SWT.INHERIT_FORCE );
    styleAs( "header", header );
    Label label = new Label( header, SWT.LEFT );
    label.setImage( loadImage( "es_small.png" ) );
    flow( header, -1, 80 );
  }
}
