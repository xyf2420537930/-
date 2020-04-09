package shopping.demo.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.demo.Dao.OrderDao;
import shopping.demo.Entity.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    private Logger logger = LoggerFactory.getLogger(Logger.class);

    public List<Order> FindAll(){
        return orderDao.FindAll();
    }

    @Cacheable(value = "orders",key = "#id")
    public Order FindById(Integer id) {
        return orderDao.FindById(id);
    }
//    @Cacheable(value = "orders",key = "#gid")
    public List<Order> FindByGId(Integer gid) {
        return orderDao.FindByGId(gid);
    }
//    @Cacheable(value = "orders",key = "#username") // 如何解决数据不一致问题
    public List<Order> FindByUserName(String username) {
        return orderDao.FindByUserName(username);
    }

    @Transactional
    @CacheEvict(value = "orders",key = "#id")
    public void DeleteById(Integer id){
        logger.info("删除了订单");
        orderDao.DeleteById(id);
    }

    @Transactional
    @CachePut(value = "orders",key = "#result.id")
    public Order ChangeStatus(Integer id){
        orderDao.ChangeStatus(id);
        return orderDao.FindById(id);
    }

    @Transactional
    public void CreateOrder(String username,Integer gid,Integer price){
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        orderDao.CreateOrder(time,username,gid,price);
    }

}
