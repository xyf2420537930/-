package shopping.demo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shopping.demo.Entity.Goods;
import shopping.demo.Service.GoodsService;
import shopping.demo.Service.OrderService;
import shopping.demo.Service.UserService;

import javax.annotation.PostConstruct;

@Controller
public class KillController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(Logger.class);
    @PostConstruct
    public void init(){
        for(Goods goods : goodsService.FindKilled())
        {
            stringRedisTemplate.opsForValue().set(goods.getId().toString(),goods.getCount().toString());
        }
    }
    @PostMapping("/toKill")
    @ResponseBody
    public String CreateKillOrder(@RequestParam("username") String username,
                                  @RequestParam("gid") Integer gid,
                                  @RequestParam("cost") Integer cost){

        long count = stringRedisTemplate.opsForValue().decrement(gid.toString());
        if(count>=0)
        {
            orderService.CreateOrder(username,gid,cost);
            goodsService.BuyIt(gid); //将商品的数量减少
            userService.MoneyDown(username,cost);
            System.out.println("秒杀成功，剩余库存"+count);
            return "购买成功！";
        }
        else
        {
            stringRedisTemplate.opsForValue().increment(gid.toString());
            return "库存不足";
        }
    }

}
