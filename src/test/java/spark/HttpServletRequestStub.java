package spark;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class HttpServletRequestStub implements HttpServletRequest {

    private final String method;
    private final String path;
    private final Cookie[] cookies;
    private final ByteArrayInputStream body;
    private final String acceptType;

    public HttpServletRequestStub(String method, String path, String body, String acceptType, final Cookie... cookies) {
        this.method = method;
        this.path = path;
        this.cookies = cookies;
        this.body = body != null ? new ByteArrayInputStream(body.getBytes(Charset.forName("UTF-8"))) : null;
        this.acceptType = acceptType;
    }

    @Override
    public String getAuthType() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Cookie[] getCookies() {
        return cookies;
    }

    @Override
    public long getDateHeader(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getHeader(final String headerName) {
        if (headerName.equals("Accept"))
        {
            return acceptType;
        }
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Enumeration<String> getHeaders(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public int getIntHeader(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getPathTranslated() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getContextPath() {
        return "";
    }

    @Override
    public String getQueryString() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getRemoteUser() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isUserInRole(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Principal getUserPrincipal() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getRequestedSessionId() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getRequestURI() {
        return path;
    }

    @Override
    public StringBuffer getRequestURL() {
        return new StringBuffer("http://localhost" + path);
    }

    @Override
    public String getServletPath() {
        return "";
    }

    @Override
    public HttpSession getSession(final boolean b) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public HttpSession getSession() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean authenticate(final HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void login(final String s, final String s2) throws ServletException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void logout() throws ServletException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Part getPart(final String s) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Object getAttribute(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setCharacterEncoding(final String s) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public int getContentLength() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return body.read();
            }
        };
    }

    @Override
    public String getParameter(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Enumeration<String> getParameterNames() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String[] getParameterValues(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getScheme() {
        return "http";
    }

    @Override
    public String getServerName() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public int getServerPort() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public BufferedReader getReader() throws IOException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getRemoteAddr() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getRemoteHost() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setAttribute(final String s, final Object o) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void removeAttribute(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Enumeration<Locale> getLocales() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getRealPath(final String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public int getRemotePort() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getLocalName() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getLocalAddr() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public int getLocalPort() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public AsyncContext startAsync(final ServletRequest servletRequest, final ServletResponse servletResponse) throws IllegalStateException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isAsyncStarted() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isAsyncSupported() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public AsyncContext getAsyncContext() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public DispatcherType getDispatcherType() {
        throw new UnsupportedOperationException("Not stubbed");
    }
}
