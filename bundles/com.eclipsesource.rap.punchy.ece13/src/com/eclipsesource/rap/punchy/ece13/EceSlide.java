package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import com.eclipsesource.rap.punchy.AbstractSlide;
import com.eclipsesource.rap.punchy.Presentation;


public abstract class EceSlide extends AbstractSlide {

  public EceSlide( Presentation presentation ) {
    super( presentation );
  }

  @Override
  protected void createContent( Composite slideComposite ) {
    header( slideComposite );
    footer( slideComposite );
    setSpacing( 8 );
    setListSpacing( 20 );
    spacer( 20 );
    setPaddingLeft( 30 );
    setPaddingRight( 30 );
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
    addPresentationMenu( logo );
    flow( header, -1, 57 );
  }

  private void footer( Composite slideComposite ) {
    Composite footer = new Composite( slideComposite, SWT.NONE );
    GridLayout layout = new GridLayout( 3, false );
    layout.marginBottom = 4;
    layout.marginTop = 7;
    layout.marginLeft = 15;
    layout.marginRight = 15;
    layout.horizontalSpacing = 15;
    footer.setLayout( layout );
    footer.setBackgroundMode( SWT.INHERIT_FORCE );
    Label progress = new Label( footer, SWT.LEFT );
    progress.setText( ( getSlideNumber() + 1 ) + "/" + getSlidesCount() );
    ProgressBar bar = new ProgressBar( footer, SWT.HORIZONTAL );
    bar.setMaximum( getSlidesCount() - 1 );
    bar.setSelection( getSlideNumber() );
    GridData layoutData = new GridData( SWT.FILL, SWT.CENTER, true, true );
    layoutData.heightHint = 10;
    bar.setLayoutData( layoutData );
    toBottom( footer, -1, 40 );
    Label remain = new Label( footer, SWT.LEFT );
    remain.setText( getPresentation().getMinutesRemaining() + "m" );
    styleAs( "footer", footer, progress, remain );
    float minutesBySlide = ( float )getPresentation().getTotalMinutes() / getSlidesCount();
    float timeForSlides = ( float )getPresentation().getMinutesRemaining() / minutesBySlide;
    int slidesRemaining = getSlidesCount() - getSlideNumber();
    float ahead = timeForSlides - slidesRemaining;
    if( ahead > 1 ) {
      styleAs( "progressAhead", bar );
    } else if( ahead > -1 ) {
      styleAs( "progressOK", bar );
    } else if( ahead > -3 ) {
      styleAs( "progressWarn", bar );
    } else {
      styleAs( "progressBad", bar );
    }
  }

}
