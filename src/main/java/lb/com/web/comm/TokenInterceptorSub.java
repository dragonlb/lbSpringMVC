package lb.com.web.comm;

import com.opensymphony.xwork2.ActionInvocation;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.TokenInterceptor;

/**
 * Created by alber.lb on 2016/5/19 0019.
 */
public class TokenInterceptorSub extends TokenInterceptor {

    private Logger logger = Logger.getLogger(TokenInterceptorSub.class);

    protected String doIntercept(ActionInvocation invocation) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Intercepting invocation to check for valid transaction token.");
        }
//        if (!TokenInterceptorSub.validToken()) {
//            return handleValidToken(invocation);
//        }
        return handleToken(invocation);
    }
}
