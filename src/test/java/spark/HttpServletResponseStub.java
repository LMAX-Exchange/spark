package spark;

import spark.util.SparkTestUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Locale;

public class HttpServletResponseStub implements HttpServletResponse {

    private final SparkTestUtil.UrlResponse urlResponse = new SparkTestUtil.UrlResponse();
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private boolean committed = false;
    private String contentType;

    @Override
    public void addCookie(Cookie cookie) {
        urlResponse.cookies.add(cookie);
    }

    @Override
    public boolean containsHeader(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String encodeURL(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String encodeRedirectURL(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String encodeUrl(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String encodeRedirectUrl(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void sendError(int i, String s) throws IOException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void sendError(int i) throws IOException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void sendRedirect(String s) throws IOException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setDateHeader(String s, long l) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void addDateHeader(String s, long l) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setHeader(String name, String value) {
        urlResponse.headers.put(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        urlResponse.headers.put(name, value);
    }

    @Override
    public void setIntHeader(String s, int i) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void addIntHeader(String s, int i) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setStatus(int statusCode) {
        urlResponse.status = statusCode;
    }

    @Override
    public void setStatus(int statusCode, String message) {
        setStatus(statusCode);
    }

    @Override
    public int getStatus() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getHeader(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Collection<String> getHeaders(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Collection<String> getHeaderNames() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        committed = true;
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setCharacterEncoding(String s) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setContentLength(int i) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setBufferSize(int i) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public int getBufferSize() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void flushBuffer() throws IOException {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void resetBuffer() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public void setLocale(Locale locale) {
        throw new UnsupportedOperationException("Not stubbed");
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("Not stubbed");
    }

    public SparkTestUtil.UrlResponse toUrlResponse() throws UnsupportedEncodingException {
        urlResponse.body = outputStream.toString("UTF-8");
        return urlResponse;
    }
}
