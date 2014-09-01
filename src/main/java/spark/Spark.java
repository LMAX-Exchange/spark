/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spark;

import spark.route.HttpMethod;
import spark.route.RouteMatcher;
import spark.route.SimpleRouteMatcher;

/**
 * The main building block of a Spark application is a set of routes. A route is
 * made up of three simple pieces:
 * <p/>
 * <ul>
 * <li>A verb (get, post, put, delete, head, trace, connect, options)</li>
 * <li>A path (/hello, /users/:name)</li>
 * <li>A callback ( handle(Request request, Response response) )</li>
 * </ul>
 * <p/>
 * Example:
 * <p/>
 * <pre>
 * {@code
 * new Spark().get(new Route("/hello") {
 *    public Object handle(Request request, Response response) {
 *       return "Hello World!";
 *    }
 * });
 * </pre>
 * <p/>
 * <code>
 * <p/>
 * </code>
 *
 * @author Per Wendel
 */
public final class Spark {

    private boolean initialized = false;

    private RouteMatcher routeMatcher;

    public Spark()
    {
        routeMatcher = new SimpleRouteMatcher();
    }

    public Spark(final SimpleRouteMatcher routeMatcher)
    {
        this.routeMatcher = routeMatcher;
    }

    /**
     * Map the route for HTTP GET requests
     *
     * @param route The route
     */
    public synchronized void get(Route route) {
        addRoute(HttpMethod.get.name(), route);
    }

    /**
     * Map the route for HTTP POST requests
     *
     * @param route The route
     */
    public synchronized void post(Route route) {
        addRoute(HttpMethod.post.name(), route);
    }

    /**
     * Map the route for HTTP PUT requests
     *
     * @param route The route
     */
    public synchronized void put(Route route) {
        addRoute(HttpMethod.put.name(), route);
    }

    /**
     * Map the route for HTTP PATCH requests
     *
     * @param route The route
     */
    public synchronized void patch(Route route) {
        addRoute(HttpMethod.patch.name(), route);
    }

    /**
     * Map the route for HTTP DELETE requests
     *
     * @param route The route
     */
    public synchronized void delete(Route route) {
        addRoute(HttpMethod.delete.name(), route);
    }

    /**
     * Map the route for HTTP HEAD requests
     *
     * @param route The route
     */
    public synchronized void head(Route route) {
        addRoute(HttpMethod.head.name(), route);
    }

    /**
     * Map the route for HTTP TRACE requests
     *
     * @param route The route
     */
    public synchronized void trace(Route route) {
        addRoute(HttpMethod.trace.name(), route);
    }

    /**
     * Map the route for HTTP CONNECT requests
     *
     * @param route The route
     */
    public synchronized void connect(Route route) {
        addRoute(HttpMethod.connect.name(), route);
    }

    /**
     * Map the route for HTTP OPTIONS requests
     *
     * @param route The route
     */
    public synchronized void options(Route route) {
        addRoute(HttpMethod.options.name(), route);
    }

    /**
     * Maps a filter to be executed before any matching routes
     *
     * @param filter The filter
     */
    public synchronized void before(Filter filter) {
        addFilter(HttpMethod.before.name(), filter);
    }

    /**
     * Maps a filter to be executed after any matching routes
     *
     * @param filter The filter
     */
    public synchronized void after(Filter filter) {
        addFilter(HttpMethod.after.name(), filter);
    }

    private void addRoute(String httpMethod, Route route) {
        routeMatcher.parseValidateAddRoute(httpMethod + " '" + route.getPath()
                + "'", route.getAcceptType(), route);
    }

    private void addFilter(String httpMethod, Filter filter) {
        routeMatcher.parseValidateAddRoute(httpMethod + " '" + filter.getPath()
                + "'", filter.getAcceptType(), filter);
    }

    private void throwBeforeRouteMappingException() {
        throw new IllegalStateException(
                "This must be done before route mapping has begun");
    }

    /*
     * TODO: discover new TODOs.
     *
     *
     * TODO: Make available as maven dependency, upload on repo etc...
     * TODO: Add *, splat possibility
     * TODO: Add validation of routes, invalid characters and stuff, also validate parameters, check static, ONGOING
     *
     * TODO: Javadoc
     *
     * TODO: Create maven archetype, "ONGOING"
     * TODO: Add cache-control helpers
     *
     * advanced TODO list:
     * TODO: Add regexp URIs
     *
     * Ongoing
     *
     * Done
     * TODO: Routes are matched in the order they are defined. The rirst route that matches the request is invoked. ???
     * TODO: Before method for filters...check sinatra page
     * TODO: Setting Headers
     * TODO: Do we want get-prefixes for all *getters* or do we want a more ruby like approach???
     * (Maybe have two choices?)
     * TODO: Setting Body, Status Code
     * TODO: Add possibility to set content type on return, DONE
     * TODO: Add possibility to access HttpServletContext in method impl, DONE
     * TODO: Redirect func in web context, DONE
     * TODO: Refactor, extract interfaces, DONE
     * TODO: Figure out a nice name, DONE - SPARK
     * TODO: Add /uri/{param} possibility, DONE
     * TODO: Tweak log4j config, DONE
     * TODO: Query string in web context, DONE
     * TODO: Add URI-param fetching from webcontext ie. ?param=value&param2=...etc, AND headers, DONE
     * TODO: sessions? (use session servlet context?) DONE
     */
}
