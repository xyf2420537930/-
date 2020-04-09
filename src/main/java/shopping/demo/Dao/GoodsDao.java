package shopping.demo.Dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import shopping.demo.Entity.Goods;
import shopping.demo.Entity.Order;

import java.util.List;

@Repository
@Mapper
public interface GoodsDao {
    @Select("select * from goods")
    public List<Goods> FindAll();
    @Select("select * from goods where id=#{id}")
    public Goods FindById(Integer id);
    @Select("select * from goods where `kill`=1")
    public List<Goods> FindKilled();
    @Update("update goods set price=#{price} where id=#{id}")
    public void ChangePrice(Integer id ,Integer price);
    @Update("update goods set count=count-1 where id=#{id}")
    public void BuyIt(Integer id);
    @Update("update goods set `kill`=1 where id=#{id}")
    public void KillIt(Integer id);
    @Delete("delete from goods where id=#{id}")
    public void DeleteById(Integer id);
    @Insert("insert into goods values(#{id},#{name},#{count},#{cost},0)")
    public void InsertGoods(Integer id,String name,Integer count,Integer cost);
}
