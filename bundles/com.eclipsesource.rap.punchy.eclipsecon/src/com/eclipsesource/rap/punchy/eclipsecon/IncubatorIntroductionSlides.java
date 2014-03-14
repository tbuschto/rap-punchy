package com.eclipsesource.rap.punchy.eclipsecon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class IncubatorIntroductionSlides {

  public static void createSlides( Presentation presentation ) {
    new EclipseConSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP Incubator";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 100 );
        styledText( "big", SWT.CENTER, "The <em>RAP Incubator</em> Project" );
        spacer( 100 );
        text( SWT.CENTER, "What is this?" );
      }
    };
  }

}
