package lb.com.web.comm;

import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TokenHelper;
import org.apache.tiles.request.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by alber.lb on 2016/5/19 0019.
 */
public class TokenHandlerSub extends TokenHelper {

    private static final Logger logger = Logger.getLogger(TokenTagSub.class);

    public static void setSessionToken( String tokenName, String token ) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        try {
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
            HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
//            ServletUtil.putSession(request, buildTokenSessionAttributeName(tokenName), token);
        } catch ( IllegalStateException e ) {
            // WW-1182 explain to user what the problem is
            String msg = "Error creating HttpSession due response is committed to client. You can use the CreateSessionInterceptor or create the HttpSession from your action before the result is rendered to the client: " + e.getMessage();
            logger.error(msg, e);
            throw new IllegalArgumentException(msg);
        }
    }

}
