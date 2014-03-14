package com.eclipsesource.rap.punchy.eclipsecon;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class EclispeConEntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
        parent.setLayout( new FillLayout() );
        EclipseConPresentation.createPresentation( parent );
    }

}
