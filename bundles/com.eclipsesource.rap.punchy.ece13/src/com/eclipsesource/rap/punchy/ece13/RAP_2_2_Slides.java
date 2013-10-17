package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class RAP_2_2_Slides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.2";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 25 );
        list(
         "December 2013",
         "RWT Scripting (\"ClientScripting\")",
         "New ToolTips",
         "Row Templates",
         "TBA..."
        );
      }
    };
  }

}
