package lb.com.web.comm;

import org.apache.ibatis.parsing.TokenHandler;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by alber.lb on 2016/5/19 0019.
 */
public class TokenTagSub extends TagSupport {

    private static final Logger logger = Logger.getLogger(TokenTagSub.class);

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try{
            out.println("<input type=\"hidden\" value=\"token\" name=\"" + TokenHandlerSub.TOKEN_NAME_FIELD+ "\"/>"
                        + "<input type=\"hidden\" value=\""+TokenHandlerSub.setToken()+"\" name=\"" + TokenHandlerSub.DEFAULT_TOKEN_NAME+ "\"/>");
        }catch(IOException ex){
            throw new JspException(ex);
        }
        return SKIP_BODY;
    }
}
