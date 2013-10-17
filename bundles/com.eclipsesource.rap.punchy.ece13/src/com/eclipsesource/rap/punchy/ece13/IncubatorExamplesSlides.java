package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class IncubatorExamplesSlides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP Incubator";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        setListSpacing( 10 );
        list(
             "What is this?",
             "ClientScripting -> RWT Scripting",
             "CKEditor",
             "DropDown/AutoSuggest",
             "FileUpload/FileDialog",
             "Nebula Grid",
             "Beyond:",
             new String[] {
               "GMaps Widget",
               "Charting Prototype",
               "Some Community widget"
             }
            );
      }
    };
  }

}
