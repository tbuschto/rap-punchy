package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.eclipsesource.rap.punchy.AbstractSlide;
import com.eclipsesource.rap.punchy.Presentation;


public class EceEntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
        parent.setLayout( new FillLayout() );
        Presentation presentation = new Presentation( parent, new Point( 800, 600 ) );
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
            text( "Presenter:" );
            list( "Markus Knauer, RAP Co-Lead", "Tim Buschtöns, RAP Comitter" );
          }
        };
        new AbstractSlide( presentation ) {
          @Override
          public String getTitle() {
            return "Slide 2";
          }
          @Override
          protected void createContent( Composite slideComposite ) {
            list( "This is something" );
            flow( new Text( slideComposite, SWT.BORDER ), 200 );
            list( "I have to say" );
            spacer( 200 );
            text( "Text that is way too long and needs overflow overflow overflow overflow Text that is way too long and needs overflow overflow overflow overflow Text that is way too long and needs overflow overflow overflow overflow Text that is way too long and needs overflow overflow overflow overflow " );
          }
        };
        new AbstractSlide( presentation ) {
          @Override
          public String getTitle() {
            return "Slide 3";
          }
          @Override
          protected void createContent( Composite slideControl ) {
            spacer( 50 );
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
        presentation.start();
    }

}
