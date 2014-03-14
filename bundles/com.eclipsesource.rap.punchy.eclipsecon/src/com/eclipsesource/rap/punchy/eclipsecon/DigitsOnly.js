var handleEvent = function( event ) {
  var text = event.widget;
  if( text.getText().match( /^[0-9]*$/ ) === null ) {
    text.setBackground( [ 255, 255, 128 ] );
  } else {
    text.setBackground( null );
  }
};
