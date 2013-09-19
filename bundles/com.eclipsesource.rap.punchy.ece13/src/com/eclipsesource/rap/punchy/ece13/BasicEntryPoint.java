package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class BasicEntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
        parent.setLayout( new FillLayout() );
        new Presentation( parent );
    }

}
