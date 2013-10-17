package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class RAP_2_1_Slides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.1 (Kepler)";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 25 );
        list(
         "Released 4 months after 2.0",
         "Mostly minor enahancements and fixes",
         "Improved Multi-Tab Browsing"
        );
      }
    };
  }

}
