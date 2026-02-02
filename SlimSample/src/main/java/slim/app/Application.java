package slim.app;

import com.webobjects.appserver.WOResponse;

import er.extensions.appserver.ERXApplication;
import er.extensions.routes.RouteTable;
import slim.components.Main;

public class Application extends ERXApplication {

	public static void main( String[] argv ) {
		ERXApplication.main( argv, Application.class );
	}

	public Application() {
		final RouteTable routes = RouteTable.defaultRouteTable();

		// Maps our start page. Most apps will probably have to do this.
		routes.map( "/", Main.class );

		// Simple route that returns a plain WOResponse.
		routes.map( "/plain-response", routeRequest -> {
			final WOResponse r = new WOResponse();
			r.setContent( "I'm a string response. Our form values are: " + routeRequest.context().request().formValues() );
			return r;
		} );

		// Mapping the Main component again to  show return of a WOComponent (WOActionResults, really) using pageWithName
		routes.map( "/also-main", routeRequest -> {
			return pageWithName( Main.class, routeRequest.context() );
		} );
	}
}
