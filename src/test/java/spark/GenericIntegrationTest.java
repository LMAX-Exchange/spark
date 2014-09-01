package spark;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.servlet.SparkApplication;
import spark.util.SparkTestUtil;
import spark.util.SparkTestUtil.UrlResponse;

public class GenericIntegrationTest {

    private SparkTestUtil testUtil;
    private Spark spark;

    @Before
    public void setup() throws Exception {
        testUtil = new SparkTestUtil(new SparkApplication()
        {
            @Override
            public void init(final Spark spark)
            {
                GenericIntegrationTest.this.spark = spark;

                spark.before(new Filter("/protected/*")
                {

                    @Override
                    public void handle(Request request, Response response)
                    {
                        halt(401, "Go Away!");
                    }
                });

                spark.before(new Filter("/protected/*", "application/json")
                {

                    @Override
                    public void handle(Request request, Response response)
                    {
                        halt(401, "{\"message\": \"Go Away!\"}");
                    }
                });

                spark.get(new Route("/hi", "application/json")
                {

                    @Override
                    public Object handle(Request request, Response response)
                    {
                        return "{\"message\": \"Hello World\"}";
                    }

                });

                spark.get(new Route("/hi")
                {

                    @Override
                    public Object handle(Request request, Response response)
                    {
                        return "Hello World!";
                    }
                });

                spark.get(new Route("/param/:param")
                {

                    @Override
                    public Object handle(Request request, Response response)
                    {
                        return "echo: " + request.params(":param");
                    }
                });

                spark.get(new Route("/paramandwild/:param/stuff/*")
                {
                    @Override
                    public Object handle(Request request, Response response)
                    {
                        return "paramandwild: " + request.params(":param") + request.splat()[0];
                    }
                });

                spark.get(new Route("/paramwithmaj/:paramWithMaj")
                {

                    @Override
                    public Object handle(Request request, Response response)
                    {
                        return "echo: " + request.params(":paramWithMaj");
                    }
                });

                spark.get(new TemplateViewRoute("/templateView")
                {

                    @Override
                    public String render(ModelAndView modelAndView)
                    {
                        return modelAndView.getModel() + " from " + modelAndView.getViewName();
                    }

                    @Override
                    public ModelAndView handle(Request request, Response response)
                    {
                        return new ModelAndView("Hello", "my view");
                    }
                });

                spark.get(new Route("/")
                {

                    @Override
                    public Object handle(Request request, Response response)
                    {
                        return "Hello Root!";
                    }
                });

                spark.post(new Route("/poster")
                {
                    @Override
                    public Object handle(Request request, Response response)
                    {
                        String body = request.body();
                        response.status(201); // created
                        return "Body was: " + body;
                    }
                });

                spark.patch(new Route("/patcher")
                {
                    @Override
                    public Object handle(Request request, Response response)
                    {
                        String body = request.body();
                        response.status(200);
                        return "Body was: " + body;
                    }
                });

                spark.after(new Filter("/hi")
                {
                    @Override
                    public void handle(Request request, Response response)
                    {
                        response.header("after", "foobar");
                    }
                });
            }
        });
    }

    @Test
    public void filters_should_be_accept_type_aware() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/protected/resource", null, "application/json");
        Assert.assertTrue(response.status == 401);
        Assert.assertEquals("{\"message\": \"Go Away!\"}", response.body);
    }

    @Test
    public void routes_should_be_accept_type_aware() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/hi", null, "application/json");
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("{\"message\": \"Hello World\"}", response.body);
    }

    @Test
    public void template_view_should_be_rendered_with_given_model_view_object() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/templateView", null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("Hello from my view", response.body);
    }

    @Test
    public void testGetHi() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/hi", null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("Hello World!", response.body);
    }

    @Test
    public void testHiHead() throws Exception {
        UrlResponse response = testUtil.doMethod("HEAD", "/hi", null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("", response.body);
    }

    @Test
    public void testGetHiAfterFilter() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/hi", null);
        Assert.assertTrue(response.headers.get("after").contains("foobar"));
    }

    @Test
    public void testGetRoot() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/", null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("Hello Root!", response.body);
    }

    @Test
    public void testParamAndWild() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/paramandwild/thedude/stuff/andits", null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("paramandwild: thedudeandits", response.body);
    }

    @Test
    public void testEchoParam1() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/param/shizzy", null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("echo: shizzy", response.body);
    }

    @Test
    public void testEchoParam2() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/param/gunit", null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("echo: gunit", response.body);
    }

    @Test
    public void testEchoParamWithUpperCaseInValue() throws Exception {
        final String camelCased = "ThisIsAValueAndSparkShouldRetainItsUpperCasedCharacters";
        UrlResponse response = testUtil.doMethod("GET", "/param/" + camelCased, null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals("echo: " + camelCased, response.body);
    }

    @Test
    public void testTwoRoutesWithDifferentCaseButSameName() throws Exception {
        String lowerCasedRoutePart = "param";
        String uppperCasedRoutePart = "PARAM";

        registerEchoRoute(lowerCasedRoutePart);
        registerEchoRoute(uppperCasedRoutePart);
        assertEchoRoute(lowerCasedRoutePart);
        assertEchoRoute(uppperCasedRoutePart);
    }

    private void registerEchoRoute(final String routePart) {
        spark.get(new Route("/tworoutes/" + routePart + "/:param")
        {
            @Override
            public Object handle(Request request, Response response)
            {
                return routePart + " route: " + request.params(":param");
            }
        });
    }

    private void assertEchoRoute(String routePart) throws Exception {
        final String expected = "expected";
        UrlResponse response = testUtil.doMethod("GET", "/tworoutes/" + routePart + "/" + expected, null);
        Assert.assertEquals(200, response.status);
        Assert.assertEquals(routePart + " route: " + expected, response.body);
    }

    @Test
    public void testEchoParamWithMaj() {
        try {
            UrlResponse response = testUtil.doMethod("GET", "/paramwithmaj/plop", null);
            Assert.assertEquals(200, response.status);
            Assert.assertEquals("echo: plop", response.body);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUnauthorized() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/protected/resource", null);
        Assert.assertTrue(response.status == 401);
    }

    @Test
    public void testNotFound() throws Exception {
        UrlResponse response = testUtil.doMethod("GET", "/no/resource", null);
        Assert.assertTrue(response.forwardedToRequestChain);
    }

    @Test
    public void testPost() throws Exception {
        UrlResponse response = testUtil.doMethod("POST", "/poster", "Fo shizzy");
        System.out.println(response.body);
        Assert.assertEquals(201, response.status);
        Assert.assertTrue(response.body.contains("Fo shizzy"));
    }

    @Test
    public void testPatch() throws Exception {
        UrlResponse response = testUtil.doMethod("PATCH", "/patcher", "Fo shizzy");
        System.out.println(response.body);
        Assert.assertEquals(200, response.status);
        Assert.assertTrue(response.body.contains("Fo shizzy"));
    }
}
