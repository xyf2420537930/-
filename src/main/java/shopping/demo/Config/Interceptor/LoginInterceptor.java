package shopping.demo.Config.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("username") == null) {
            Map<String, String> map = new HashMap<>();
            request.setAttribute("message", "禁止不登录直接访问");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }
        else
            return true;
    }
}
