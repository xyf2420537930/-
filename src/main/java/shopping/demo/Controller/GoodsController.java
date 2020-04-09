package shopping.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shopping.demo.Entity.Goods;
import shopping.demo.Service.GoodsService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping("/findAll")
    public String FindAll(Map<String,List<Goods>> map){
        List<Goods> goods = goodsService.FindAll();
        map.put("goods",goods);
        return "goodsList";
    }
    @GetMapping("/find/{id}")
    public String FindById(@PathVariable("id") Integer id,
                           Map<String,Goods> map) {
        map.put("goods",goodsService.FindById(id));
        return "changePrice";
    }

    @GetMapping("/findKilled")
    public String FindKilled(Model model) {
        model.addAttribute("killed",goodsService.FindKilled());
        return "kill";
    }
    @GetMapping("/kill/{id}")
    public String KillById(@PathVariable("id") Integer id) {
        goodsService.KillIt(id);
        return "redirect:/goods/findAll";
    }

    @Transactional
    @GetMapping("/delete/{id}")
    public String DeleteById(@PathVariable("id") Integer id){
        goodsService.DeleteById(id);
        return "redirect:/goods/findAll";
    }

    @Transactional
    @PostMapping("/changePrice")
    public String changePrice(@RequestParam("id") Integer id,
                             @RequestParam("price") Integer cost){
        goodsService.ChangePrice(id,cost);
        return "redirect:/goods/findAll";
    }

    @Transactional
    @PostMapping("/insert")
    public String insertGoods(@RequestParam("id") Integer id,
                             @RequestParam("name") String name,
                             @RequestParam("count") Integer count,
                             @RequestParam("cost") Integer cost){
        goodsService.InsertGoods(id,name,count,cost);
        return "redirect:/goods/findAll";
    }
}
