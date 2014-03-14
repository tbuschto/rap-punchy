package com.eclipsesource.rap.punchy.eclipsecon;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.addons.dropdown.DropDown;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;


public class EclipseConApplicationConfiguration implements ApplicationConfiguration {

    @Override
    public void configure(Application application) {
        Map<String, String> properties = new HashMap<String, String>();
        application.addStyleSheet( RWT.DEFAULT_THEME_ID, "com/eclipsesource/rap/punchy/eclipsecon/ece.css" );
        application.addEntryPoint( "/eclipsecon", EclispeConEntryPoint.class, properties );
        application.addThemableWidget( DropDown.class );
    }

}
