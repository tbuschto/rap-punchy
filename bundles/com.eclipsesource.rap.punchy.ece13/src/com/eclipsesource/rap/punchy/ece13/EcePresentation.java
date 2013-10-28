package com.eclipsesource.rap.punchy.ece13;

import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

import com.eclipsesource.rap.punchy.AbstractSlide;
import com.eclipsesource.rap.punchy.Presentation;


public class EcePresentation {

  private static GregorianCalendar now;

  static void createPresentation( Composite parent ) {
    GregorianCalendar start = new GregorianCalendar();
    start.set( GregorianCalendar.HOUR_OF_DAY, 18 );
    start.set( GregorianCalendar.MINUTE, 10 );
    start.set( GregorianCalendar.SECOND, 0 );
    start.set( GregorianCalendar.MILLISECOND, 0 );
    GregorianCalendar end = new GregorianCalendar();
    end.set( GregorianCalendar.HOUR_OF_DAY, 19 );
    end.set( GregorianCalendar.MINUTE, 20 );
    end.set( GregorianCalendar.SECOND, 0 );
    end.set( GregorianCalendar.MILLISECOND, 0 );
    Presentation presentation = new Presentation( parent,
                                                  new Point( 1000, 750 ),
                                                  start.getTime(),
                                                  end.getTime() );
    createSlides( presentation );
    presentation.start();
  }

  private static void createSlides( Presentation presentation ) {
    RAP_2_0_Slides.createSlides( presentation );
    RAP_2_1_Slides.createSlides( presentation );
    RAP_2_2_Slides.createSlides( presentation );
    RAP_2_3_Slides.createSlides( presentation );
    IncubatorIntroductionSlides.createSlides( presentation );
    IncubatorExamplesSlides.createSlides( presentation );
    //createExampleSlides( presentation );
  }

  private static void createExampleSlides( Presentation presentation ) {
    new AbstractSlide( presentation ) {
      @Override
      public String getTitle() {
        return "---Example Slides---";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Image Test";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 20 );
        toRight( image( "images/clients.png" ) );
        spacer( 15 );
        text( 340, 380, "Text </br>that is way too is way too long and needs overflow overflow overflow overflow overflow Text that is way too long and needs overflow overflow overflow overflow " );
        text( "Moret Text that is text with text lalalaa All play and no work make me a good employee" );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Control Test";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        text( "A Text (widget)" );
        flow( new Text( slideComposite, SWT.BORDER ) );
        text( "A Scale" );
        flow( "transparent", new Scale( slideComposite, SWT.HORIZONTAL ) );
        text( "A Button" );
        flow( new Button( slideComposite, SWT.ARROW ) );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "List Test";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 50 );
        setListSpacing( 0 );
        list(
          "blub",
          "blab",
          "foo",
          new String[] {
            "Hello",
            "World"
          }
        );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Code Test";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        text( "Java:" );
        snippet( "java", 500, 80, "protected void createContents( Composite parent ) {\n  new Stuff();\n}" );
        text( "JavaScript:" );
        snippet( "javascript", 200, 80, "function foo() {\n  new Stuff();\n}" );
        text( "CSS:" );
        snippet( "css", 300, 80, ".myClass {\n  background-color: #ffaaff;\n}" );
      }
    };
  }

}
