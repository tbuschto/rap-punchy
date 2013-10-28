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
        spacer( 50 );
        list(
          "Released 4 months after 2.0<br>(RAP uses a 6 monthly release cycle)",
          "Improve Web Browser Experience",
          new String[] {
            "Built-in support for <em>multiple browser tabs</em><br>",
            "Terminate UISession when leaving browser page",
            "Additional API for keyboard shortcuts<br>(mnemonics, accelerators)"
          },
          "Performance improvements",
          new String[] {
            "Using internal JSON library"
          }
        );
      }
    };
  }

}
