package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class RAP_2_0_Slides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Welcome";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 100 );
        styledText( "big", SWT.CENTER, "RAP 2.x and Beyond" );
        spacer( 100 );
        setListSpacing( 8 );
        text( "Presenter:" );
        list( "Markus Knauer, RAP Co-Lead", "Tim Buschtöns, RAP Comitter" );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.0";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        toRight( image( "clients.png" ) );
        spacer( 25 );
        list(
          "JSON Protocol </br>fully implemented",
          "Tabris 1.0 Released",
          "API Clean-Up",
          "Client Services API",
          "Custom Widget API",
            "New Name: </br>"
          + "<big style='line-height:80px'><b>R</b>emote <b>A</b>pplication <b>P</b>latform</big>"
        );
      }
    };
  }

}
