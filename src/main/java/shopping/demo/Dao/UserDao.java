package shopping.demo.Dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import shopping.demo.Entity.User;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    @Select("select * from user")
    public List<User> FindAll();
    @Select("select password from user where username=#{username}")
    public String FindPassword(String username);
    @Select("select money from user where username=#{username}")
    public Integer FindMoney(String username);
    @Select("select * from user where username=#{username}")
    public User FindById(String username);
    @Update("update user set password=#{password} where username=#{username}")
    public void ChangePassword(String username,String password);
    @Update("update user set money=money-#{cost} where username=#{username}")
    public void MoneyDown(String username,Integer cost);
    @Update({"update user set money=money+100 where username=#{username}"})
    public void MoneyUp(String username);
    @Delete("delete from user where username=#{username}")
    public void DeleteById(String username);
    @Insert("insert into user values(#{username},#{password},#{money})")
    public void InsertUser(String username,String password,Integer money);
}
