package scalable.solutions.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

public class ZuulPostFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ZuulPostFilter.class);

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        logger.info(String.format("Response [Status: %d]", response.getStatus()));
        Collection<String> headerNames = response.getHeaderNames();
        for (String name : headerNames) {
            Collection<String> headers = response.getHeaders(name);
            for (String header : headers) {
                logger.info(String.format("Response [%s] : [%s]", name, header));
            }
        }
        return null;
    }
}

