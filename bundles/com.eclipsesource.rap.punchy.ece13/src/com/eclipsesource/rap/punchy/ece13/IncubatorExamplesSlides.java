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
        flow( "transparent", new CKEditorExamplePage().createControl( slideComposite ), SWT.DEFAULT, 550 );
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
        flow( new NebulaGridExamplePage().createControl( slideComposite ), SWT.DEFAULT, 550 );
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
        return "Incubator: FileDialog";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 30 );
        flow( "transparent", new FileUploadExamplePage().createControl( slideComposite ), SWT.DEFAULT, 350 );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Beyond Incubator";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        setPaddingLeft( 60 );
        setPaddingRight( 60 );
        toRight( text( 400, 50, SWT.CENTER, "Tabris UI" ) );
        spacer( 20 );
        text( 400, 40, SWT.CENTER, "GMaps Widget, d3 Widget" );
        toRight( image( "tabrisui1.png", SWT.BORDER ) );
        image( "gmaps.png", SWT.BORDER );
        toRight( image( "tabrisui2.png", SWT.BORDER ) );
        image( "d3.png", SWT.BORDER );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Links";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        setPaddingLeft( 80 );
        image( "addons.png" );
        setPaddingLeft( 350 );
        spacer( -450 );
        list( "<a href='http://eclipse.org/rap/incubator/'>http://eclipse.org/rap/incubator/</a>" );
        spacer( 240 );
        list( "<a href='https://wiki.eclipse.org/RAP/Add-Ons'>http://wiki.eclipse.org/RAP/Add-Ons</a>" );
      }
    };
  }

}
