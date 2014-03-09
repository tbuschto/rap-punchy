package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.rap.punchy.Presentation;


public class RAP_2_0_Slides {

  public static void createSlides( Presentation presentation ) {
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "Welcome";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        spacer( 100 );
        styledText( "big", SWT.CENTER, "Hand-picked Highlights" );
        styledText( "big", SWT.CENTER, "from Eclipse RAP" );
        spacer( 200 );
        setPaddingLeft( 100 );
        setListSpacing( 8 );
        text( "Presenter" );
        text( "Markus Knauer, RAP Co-Lead, @mknauer23" );
      }
    };
    new EceSlide(presentation) {
      @Override
      public String getTitle() {
        return "REMOTE";
      }
      @Override
      protected void createContent(Composite slideComposite) {
        super.createContent(slideComposite);
        text( "RAP 1.x was known as \"RCP for the web browser\"." );
        list( "we continue to support this",
            "extend the idea of <em>Single Sourcing</em>" );
        spacer(10);
        text( "RAP 2.x is an open platform for remote applications." );
        list( "Rename project to <em>Remote Application Platform</em>" );
        spacer(10);
        image( "images/clients.png", SWT.CENTER );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.0 Overview";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        list(
          "API clean-up (see RAP 2.0 Migration Guide)",
          "JSON protocol fully implemented",
          "Client Services API",
          "Custom Widget API",
          "Java EE Compatibility Mode",
          "Tabris 1.0 released for mobile devices (Android, iOS)",
          "Project renamed to <big style='line-height:80px'><b>R</b>emote <b>A</b>pplication <b>P</b>latform</big>"
        );
        image( "images/remote.png", SWT.CENTER );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.0 API";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        image( "images/RAP-API.png", SWT.CENTER );
        text( "Migration Guide 1.x - 2.x<br>http://eclipse.org/rap/noteworthy/2.0/migration-guide/" );
      };
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.0 JSON Protocol";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        spacer( 50 );
        text( "Exemplary JSON message" );
        spacer( 50 );
        snippet(
            "json", SWT.DEFAULT, 240,
            "  {\n" +
            "    \"head\": {\n" +
            "      \"requestCounter\": 23\n" +
            "    },\n" +
            "    \"operations\": [\n" +
            "      [ \"create\", \"w4\", \"rwt.widgets.Button\", {\n" +
            "          \"parent\": \"w2\",\n" +
            "          \"style\": [ \"PUSH\" ],\n" +
            "          \"bounds\": [ 5, 68, 131, 32 ],\n" +
            "          \"text\": \"I am a button\" } ],\n" +
            "      [ \"listen\", \"w4\", { \"Selection\": true } ]\n" +
            "    ]\n" +
            "  }\n"
            );
        spacer( 50 );
        text( "create, set, destroy, call, listen, notify" );
        text( "http://wiki.eclipse.org/RAP/Protocol" );
      };
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.0 Client Services API";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        text( "Getting the Client" );
        snippet(
            "java", SWT.DEFAULT, 90,
            "Client client = RWT.getClient()\n" +
            "if( client instanceof WebClient ) {\n" +
            "  // WebClient is the default\n" +
            "}"
        );
        spacer( 20 );
        text( "Retrieve client details" );
        snippet(
            "java", SWT.DEFAULT, 70,
            "ClientInfo clientInfo = client.getService( ClientInfo.class );\n" +
                "int timezoneOffset = clientInfo.getTimezoneOffset();\n" +
                "Locale clientLocale = clientInfo.getLocale();"
            );
        spacer( 20 );
        text( "Running JavaScript on the client" );
        snippet(
            "java", SWT.DEFAULT, 60,
            "JavaScriptExecutor executor = client.getService( JavaScriptExecutor.class );\n" +
            "executor.execute( \"document.title = 'New Title'\" );"
        );
        spacer( 20 );
        text( "Add entries to the Browser's history" );
        snippet(
            "java", SWT.DEFAULT, 80,
            "BrowserNavigation navigation = client.getService( BrowserNavigation.class );\n" +
            "navigation.pushState( \"main\", \"Main View\" );\n" +
            "navigation.addBrowserNavigationListener( listener );"
        );
      }
    };
    new EceSlide( presentation ) {
      @Override
      public String getTitle() {
        return "RAP 2.0 Java EE Compatibility Mode";
      }
      @Override
      protected void createContent( Composite slideComposite ) {
        super.createContent( slideComposite );
        image( "images/threads.png", SWT.CENTER );
        spacer( 50 );
        list( "Lightweight applications with RAP",
              "<em>No</em> blocking API, <em>no</em> Eclipse Workbench, but...",
              "Java EE contexts, clustering");
      }
    };
  }

}
