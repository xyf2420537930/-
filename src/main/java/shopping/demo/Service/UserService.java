package shopping.demo.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.demo.Dao.UserDao;
import shopping.demo.Entity.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(Logger.class);

    public List<User> FindAll(){
        return userDao.FindAll();
    }
    public String FindPassword(String username) {
        return userDao.FindPassword(username);
    }
    @Cacheable(value = "users",key = "#username")
    public User FindById(String username) {
        return userDao.FindById(username);
    }
    @Cacheable(value = "money",key = "#username")
    public Integer FindMoney(String username) {
        logger.info(userDao.FindMoney(username).toString());
        return userDao.FindMoney(username);
    }
    @CacheEvict(value = {"users","money"},key = "#username")
    public void DeleteById(String username){
        userDao.DeleteById(username);
    }

    @Transactional
    @CachePut(value = {"users"},key = "#result.username")
    public User ChangePassword(String username,String password){
        userDao.ChangePassword(username,password);
        return userDao.FindById(username);
    }
    @Transactional
    @CachePut(value = "money",key = "#username")
    public Integer MoneyDown(String username,Integer cost){
        userDao.MoneyDown(username,cost);
        return userDao.FindMoney(username);
    }

    @CachePut(value = "money",key = "#username")
    public Integer MoneyUp(String username){
        userDao.MoneyUp(username);
        return userDao.FindMoney(username);
    }
    public String InsertUser(String username,String password,Integer money){
        if(userDao.FindById(username) != null)
        {
            return "NO";
        }
        else
        {
            userDao.InsertUser(username,password,money);
            logger.info("创建了新用户");
            return "YES";
        }
    }
}
