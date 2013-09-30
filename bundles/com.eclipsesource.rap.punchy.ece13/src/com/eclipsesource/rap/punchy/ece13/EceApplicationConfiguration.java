package com.eclipsesource.rap.punchy.ece13;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;


public class EceApplicationConfiguration implements ApplicationConfiguration {

    @Override
    public void configure(Application application) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put( WebClient.PAGE_TITLE, "EclipseCon 2013: RAP 2.x" );
        application.addStyleSheet( RWT.DEFAULT_THEME_ID, "com/eclipsesource/rap/punchy/ece13/ece.css" );
        application.addEntryPoint( "/ece13", EceEntryPoint.class, properties );
    }

}
