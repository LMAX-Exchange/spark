package spark.util;

import spark.FilterChainStub;
import spark.HttpServletRequestStub;
import spark.HttpServletResponseStub;
import spark.servlet.SparkApplication;
import spark.servlet.SparkFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.util.*;

public class SparkTestUtil {

    private final SparkFilter sparkFilter;

    public SparkTestUtil(final SparkApplication sparkApplication) throws ServletException
    {
        sparkFilter = new SparkFilter()
        {
            @Override
            protected SparkApplication getApplication(final FilterConfig filterConfig) throws ServletException
            {
                return sparkApplication;
            }
        };
        sparkFilter.init(new FilterConfig()
        {
            @Override
            public String getFilterName()
            {
                return "test-filter";
            }

            @Override
            public ServletContext getServletContext()
            {
                return null;
            }

            @Override
            public String getInitParameter(final String s)
            {
                return null;
            }

            @Override
            public Enumeration<String> getInitParameterNames()
            {
                return null;
            }
        });
    }

    public UrlResponse doMethod(String requestMethod, String path, String body, final Cookie... cookies) throws Exception {
		return doMethod(requestMethod, path, body, "text/html", cookies);
	}

    public UrlResponse doMethod(String requestMethod, String path, String body, String acceptType, final Cookie... cookies) throws Exception {

        final HttpServletResponseStub response = new HttpServletResponseStub();
        final FilterChainStub filterChainStub = new FilterChainStub();
        sparkFilter.doFilter(new HttpServletRequestStub(requestMethod, path, body, acceptType, cookies), response, filterChainStub);

        final UrlResponse urlResponse = response.toUrlResponse();
        urlResponse.forwardedToRequestChain = filterChainStub.wasCalled();
        return urlResponse;
	}

    public static class UrlResponse {

		public Map<String, String> headers = new HashMap<String, String>();
		public String body;
		public int status = 200;
        public List<Cookie> cookies = new ArrayList<Cookie>();
        public boolean forwardedToRequestChain;
    }

}
