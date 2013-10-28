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
        spacer( 25 );
        list(
          "RAP 2.3 is scheduled together with Eclipe Luna (June 2014)",
          "Plan not yet finalized",
          "Depending on workload and interest: Eclipse 4" );
        image( "images/rap-e4.png", SWT.CENTER );
      }
    };
  }

}
