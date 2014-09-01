package spark;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.servlet.SparkApplication;
import spark.util.SparkTestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

/**
 * System tests for the Cookies support.
 * @author dreambrother
 */
public class CookiesIntegrationTest {

    private static final String COOKIE_NAME = "testCookieName";
    private static final String COOKIE_VALUE = "testCookieValue";
    private SparkTestUtil testUtil;

    @Before
    public void initRoutes() throws ServletException {
        testUtil = new SparkTestUtil(new SparkApplication() {
            @Override
            public void init(Spark spark) {
                spark.post(new Route("/assertNoCookies") {

                    @Override
                    public Object handle(Request request, Response response) {
                        if (!request.cookies().isEmpty()) {
                            halt(500);
                        }
                        return "";
                    }
                });

                spark.post(new Route("/setCookie") {

                    @Override
                    public Object handle(Request request, Response response) {
                        response.cookie(COOKIE_NAME, COOKIE_VALUE);
                        return "";
                    }
                });

                spark.post(new Route("/assertHasCookie") {

                    @Override
                    public Object handle(Request request, Response response) {
                        String cookieValue = request.cookie(COOKIE_NAME);
                        if (!COOKIE_VALUE.equals(cookieValue)) {
                            halt(500);
                        }
                        return "";
                    }
                });

                spark.post(new Route("/removeCookie") {

                    @Override
                    public Object handle(Request request, Response response) {
                        String cookieValue = request.cookie(COOKIE_NAME);
                        if (!COOKIE_VALUE.equals(cookieValue)) {
                            halt(500);
                        }
                        response.removeCookie(COOKIE_NAME);
                        return "";
                    }
                });
            }
        });
    }

    @Test
    public void testEmptyCookies() throws Exception {
        httpPost("/assertNoCookies");
    }

    @Test
    public void testParseCookiesFromRequest() throws Exception {
        httpPost("/assertHasCookie", new Cookie(COOKIE_NAME, COOKIE_VALUE));
    }

    @Test
    public void testCreateCookie() throws Exception {
        final SparkTestUtil.UrlResponse urlResponse = httpPost("/setCookie");
        Assert.assertEquals(1, urlResponse.cookies.size());
        final Cookie cookie = urlResponse.cookies.get(0);
        Assert.assertEquals(COOKIE_NAME, cookie.getName());
        Assert.assertEquals(COOKIE_VALUE, cookie.getValue());
    }

    @Test
    public void testRemoveCookie() throws Exception {
        final SparkTestUtil.UrlResponse urlResponse = httpPost("/removeCookie", new Cookie(COOKIE_NAME, COOKIE_VALUE));
        Assert.assertEquals(1, urlResponse.cookies.size());
        final Cookie cookie = urlResponse.cookies.get(0);
        Assert.assertEquals(COOKIE_NAME, cookie.getName());
        Assert.assertEquals("", cookie.getValue());
        Assert.assertEquals(0, cookie.getMaxAge());
    }

    private SparkTestUtil.UrlResponse httpPost(String relativePath, final Cookie... cookies) throws Exception {
        final SparkTestUtil.UrlResponse urlResponse = testUtil.doMethod("POST", relativePath, "", cookies);
        Assert.assertEquals("Request should have been successful", urlResponse.status, 200);
        Assert.assertFalse("Should have handled request", urlResponse.forwardedToRequestChain);
        return urlResponse;
    }
}
