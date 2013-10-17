package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class IncubatorExamplesSlides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Incubator: CKEditor";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        //Composite example = new Composite( slideComposite, SWT.NONE );
        flow( "transparent", new CKEditorExamplePage().createControl( slideComposite ), SWT.DEFAULT, 450 );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Incubator: Nebula Grid";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        //Composite example = new Composite( slideComposite, SWT.NONE );
        flow( new NebulaGridExamplePage().createControl( slideComposite ), SWT.DEFAULT, 450 );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Incubator: DropDown / AutoSuggest";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 50 );
        Composite example = new Composite( slideComposite, SWT.NONE );
        new DropDownDemo().createContents( example );
        flow( "transparent", example, SWT.DEFAULT, 50 );
        Composite example2 = new Composite( slideComposite, SWT.NONE );
        new AutoSuggestSnippet().createContents( example2 );
        flow( "transparent", example2, SWT.DEFAULT, 300 );
      }
    };
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
               "Tabris UI",
               "Some Community widget"
             }
            );
      }
    };
  }

}