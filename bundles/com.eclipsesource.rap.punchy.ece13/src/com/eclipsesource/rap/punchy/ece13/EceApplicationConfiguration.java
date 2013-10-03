package com.eclipsesource.rap.punchy.ece13;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;


public class EceApplicationConfiguration implements ApplicationConfiguration {

    @Override
    public void configure(Application application) {
        Map<String, String> properties = new HashMap<String, String>();
        application.addStyleSheet( RWT.DEFAULT_THEME_ID, "com/eclipsesource/rap/punchy/ece13/ece.css" );
        application.addEntryPoint( "/ece13", EceEntryPoint.class, properties );
    }

}
