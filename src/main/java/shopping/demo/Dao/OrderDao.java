package shopping.demo.Dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import shopping.demo.Entity.Goods;
import shopping.demo.Entity.Order;

import java.util.List;

@Repository
@Mapper
public interface OrderDao {
    @Select("select * from `order`")
    public List<Order> FindAll();
    @Select("select * from `order` where id=#{id}")
    public Order FindById(Integer id);
    @Select("select * from `order` where username=#{username}")
    public List<Order> FindByUserName(String username);
    @Select("select * from `order` where gid=#{gid}")
    public List<Order> FindByGId(Integer gid);
    @Update("update`order` set status='finished' where id=#{id}")
    public void ChangeStatus(Integer id);
    @Delete("delete from `order` where id=#{id}")
    public void DeleteById(Integer id);
    @Insert("insert into `order`(time,username,gid,price,status) values(#{time},#{username},#{gid},#{price},'doing')")
    public void CreateOrder(String time,String username,Integer gid,Integer price);
}
