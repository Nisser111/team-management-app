package pl.summaryGenerator.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class JobNumberFilter implements Filter {

    private static final String JOB_NUMBER_KEY = "Job-number";

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String jobNumber = UUID.randomUUID().toString();

        MDC.put(JOB_NUMBER_KEY, jobNumber);

        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).setHeader(JOB_NUMBER_KEY, jobNumber);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(JOB_NUMBER_KEY);
        }
    }
}