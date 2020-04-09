package shopping.demo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shopping.demo.Service.GoodsService;
import shopping.demo.Service.OrderService;
import shopping.demo.Service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(Logger.class);
    @GetMapping("/findAll")
    public String FindAll(HttpServletRequest request,Model model){
        Object level = request.getSession().getAttribute("level");
        if(level.equals("admin"))
            model.addAttribute("orders",orderService.FindAll());
        else if(level.equals("consumer"))
            model.addAttribute("orders",orderService.FindByUserName((String) request.getSession().getAttribute("username")));
        return "orderList";
    }
    @GetMapping("/findByName")
    public String FindByName(HttpServletRequest request,Model model){
        String username = (String)request.getSession().getAttribute("username");
        model.addAttribute("userOrders",orderService.FindByUserName(username));
        return "userOrder";
    }
    @GetMapping("/changeStatus/{id}")
    public String ChangeStatus(@PathVariable("id") Integer id){
        orderService.ChangeStatus(id); //完成订单
        return "redirect:/order/findAll";
    }
    @GetMapping("/delete/{id}")
    public String DeleteById(@PathVariable("id") Integer id){
        orderService.DeleteById(id);
        return "redirect:/order/findAll";
    }
    @PostMapping("/toBuy")
    @ResponseBody
    public String CreateOrder(@RequestParam("username") String username,
                              @RequestParam("gid") Integer gid,
                              @RequestParam("cost") Integer cost){
        if(userService.FindMoney(username) >= cost)
        {

            orderService.CreateOrder(username,gid,cost);
            goodsService.BuyIt(gid); //将商品的数量减少
            Integer left_money = userService.MoneyDown(username,cost); //用户的余额减少
            return "购买成功！您的余额还剩余："+left_money+"元";
        }
        else
        {
            return "余额不足，请尽快充值！";
        }
    }
    @PostMapping("/toKill")
    @ResponseBody
    public String CreateKillOrder(@RequestParam("username") String username,
                              @RequestParam("gid") Integer gid,
                              @RequestParam("cost") Integer cost){
        orderService.CreateOrder(username,gid,cost);
        goodsService.BuyIt(gid); //将商品的数量减少
        userService.MoneyDown(username,cost);
        return "购买成功！";
    }
}
