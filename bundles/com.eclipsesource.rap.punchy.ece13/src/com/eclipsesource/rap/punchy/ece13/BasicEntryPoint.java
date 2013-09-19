package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.AbstractSlide;
import com.eclipsesource.rap.punchy.Presentation;


public class BasicEntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
        parent.setLayout( new FillLayout() );
        Presentation presentation = new Presentation( parent, new Point( 800, 600 ) );
        new AbstractSlide( presentation ) {
          @Override
          public String getTitle() {
            return "Slide 1";
          }
          @Override
          protected void createContent( Composite slideControl ) {
            title();
          }
        };
        new AbstractSlide( presentation ) {
          @Override
          public String getTitle() {
            return "Slide 2";
          }
          @Override
          protected void createContent( Composite slideControl ) {
            title();
          }
        };
        new AbstractSlide( presentation ) {
          @Override
          public String getTitle() {
            return "Slide 3";
          }
          @Override
          protected void createContent( Composite slideControl ) {
            title();
          }
        };
        presentation.start();
    }

}
