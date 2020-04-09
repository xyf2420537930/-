package shopping.demo.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.demo.Dao.GoodsDao;
import shopping.demo.Entity.Goods;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public List<Goods> FindAll(){
        return goodsDao.FindAll();
    }
    public List<Goods> FindKilled(){
        return goodsDao.FindKilled();
    }
    @Cacheable(value = "goods",key = "#id")
    public Goods FindById(Integer id) {
        return goodsDao.FindById(id);
    }
    @Transactional
    @CacheEvict(value = "goods",key = "#id")
    public void DeleteById(Integer id){
        goodsDao.DeleteById(id);
    }

    @Transactional
    @CachePut(value = "goods",key = "#result.id")
    public Goods ChangePrice(Integer id,Integer cost){
        goodsDao.ChangePrice(id,cost);
        return goodsDao.FindById(id);
    }

    @Transactional
    @CachePut(value = "goods",key = "#result.id")
    public Goods BuyIt(Integer id){
        goodsDao.BuyIt(id);
        return goodsDao.FindById(id);
    }

    @Transactional
    @CachePut(value = "goods",key = "#result.id")
    public Goods KillIt(Integer id){
        goodsDao.KillIt(id);
        return goodsDao.FindById(id);
    }
    @Transactional
    public void InsertGoods(Integer id,String name,Integer count,Integer cost){
        goodsDao.InsertGoods(id,name,count,cost);
    }
}
