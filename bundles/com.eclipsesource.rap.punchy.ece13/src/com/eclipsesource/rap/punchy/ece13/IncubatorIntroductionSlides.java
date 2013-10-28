package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class IncubatorIntroductionSlides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
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
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP Incubator Examples";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        setListSpacing( 10 );
        list(
         "ClientScripting moved to core RWT Scripting",
         "CKEditor as RichText editor",
         "DropDown and AutoSuggest",
         "FileUpload and FileDialog",
         "Nebula Grid",
         "Beyond:",
         new String[] {
           "GMaps widget",
           "Charting prototype",
           "Some community widgets"
         }
        );
      }
    };
  }

}
