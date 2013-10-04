package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

import com.eclipsesource.rap.punchy.AbstractSlide;
import com.eclipsesource.rap.punchy.Presentation;


public class EcePresentation {

  static void createPresentation( Composite parent ) {
    Presentation presentation = new Presentation( parent, new Point( 800, 600 ) );
    createSlides( presentation );
    presentation.start();
  }

  private static void createSlides( Presentation presentation ) {
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
          "New Name: </br><b>R</b>emote <b>A</b>pplication <b>P</b>latform"
        );
      }
    };
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
         "A lot minor enahancements and fixes",
         "Improved Multi-Tab Browsing"
        );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.3";
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
         "AutoSuggest",
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
    createExampleSlides( presentation );
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
        toRight( image( "clients.png" ) );
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
