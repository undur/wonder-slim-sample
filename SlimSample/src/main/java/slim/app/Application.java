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

		// All URLs that start with "/wildcard/" will get handled by this one
		routes.map( "/wildcard/*", routeRequest -> {
			final WOResponse r = new WOResponse();
			r.setContent( "We're using wildcards. The URL you used is: " + routeRequest.url() );
			return r;
		} );

		// Mapping the Main component again to  show return of a WOComponent (WOActionResults, really) using pageWithName
		routes.map( "/also-main", routeRequest -> {
			return pageWithName( Main.class, routeRequest.context() );
		} );

		// Mapping the default URL a WO app will open at in direct connect mode.
		// NOTE: This is really redundant — you should really just open the app to the root, "/", during development
		routes.map( "/cgi-bin/WebObjects/SlimSample.woa", Main.class );
	}
}
