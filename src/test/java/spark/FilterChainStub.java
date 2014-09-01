package spark;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class FilterChainStub implements FilterChain {
    private boolean called = false;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        called = true;
    }

    public boolean wasCalled() {
        return called;
    }
}
