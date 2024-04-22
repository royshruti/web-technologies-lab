package com.royshruti.q22;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(value = "/question22/accounts/username/validate", filterName = "IsUsernamePresentFilter", description = "Confirms whether username is present in request URL.")
public class IsUsernamePresentFilter implements Filter {
    FilterConfig conf;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.conf = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String errorMessage = "username validation error";
        if (username == null || username.isEmpty()) {
            errorMessage = "username must be present to be validated";
        } else {
            // System.out.println("username passes basic validation via filter");
            // this will lead to early return in the chaining, similar to `return`
            chain.doFilter(request, response);
        }

        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST,
                    errorMessage);
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(errorMessage);
            out.close();
        }
    }

    @Override
    public void destroy() {
        this.conf = null;
    }

}
