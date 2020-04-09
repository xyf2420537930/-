package shopping.demo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shopping.demo.Entity.User;
import shopping.demo.Service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(Logger.class);
    @PostMapping("/check")
    public String login(HttpSession session,
                        Map<String,String> map,
                        @RequestParam("username")String username,
                        @RequestParam("password") String password,
                        Model model){
        session.setAttribute("username",username); //设置session防止拦截
        if(username.equals("admin")&&password.equals("xyj990526")) {
            session.setAttribute("level", "admin");
            return "adminHome";
        }
        else if(userService.FindPassword(username) == null)
        {
            map.put("message","账号不存在！");
            return "index";
        }
        else if(!userService.FindPassword(username).equals(password))
        {
            map.put("message","账号或者密码错误");
            return "index";
        }
        else {
            session.setAttribute("level","consumer");
            model.addAttribute("money",userService.FindMoney(username));
            return "userHome";
        }
    }

    @GetMapping("/findAll")
    public String FindAll(Map<String,List<User>> map){
        map.put("users",userService.FindAll());
        return "userList";
    }

    @GetMapping("/delete/{username}")
    public String DeleteById(@PathVariable("username") String username){
        userService.DeleteById(username);
        return "redirect:/user/findAll";
    }

    @PostMapping("/insert")
    public String InsertUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("money") Integer money, Model model){
        String message = userService.InsertUser(username,password,money);
        logger.info(message);
        if(message.equals("YES")){
            model.addAttribute("message","注册成功！");
            return "index";
        }
        else{
            model.addAttribute("message","用户名重复，请重新输入！");
            return "userInsert";
        }
    }
    @PostMapping("/changePassword")
    public String ChangePassword(@RequestParam("username") String username,
                                 @RequestParam("oldpassword") String oldpassword,
                                 @RequestParam("newpassword_first") String first,
                                 @RequestParam("newpassword_twice") String twice,
                                 Model model){
        if(userService.FindById(username) == null)
        {
            model.addAttribute("message","用户名输入错误");
            return "changePassword";
        }
        if(userService.FindPassword(username).equals(oldpassword))
        {
            if(first.equals(twice))
            {
                userService.ChangePassword(username,first);
                model.addAttribute("message","修改成功！");
                return "index";
            }
            else
            {
                model.addAttribute("message","两次密码输入不一致！");
                return "changePassword";
            }
        }
        else
        {
            model.addAttribute("message","密码输入错误，请重新输入");
            return "changePassword";
        }
    }
    @GetMapping("/moneyUp")
    public String ChangeMoney(HttpServletRequest request,
                              Model model){
        String username = (String) request.getSession().getAttribute("username");
        model.addAttribute("money",userService.MoneyUp(username));
        return "userHome";
    }
}
