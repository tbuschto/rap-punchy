package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class RAP_2_3_Slides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.3 (Luna)";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        setListSpacing( 5 );
        spacer( -5 );
        list( "RAP 2.3 is scheduled together with Eclipe Luna (June 2014)" );
        image( "images/rap-e4.png", SWT.CENTER );
      }
    };
  }

}
