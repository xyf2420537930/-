package shopping.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.demo.Dao.GoodsDao;


@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private GoodsDao goodsDao;
    @Test
    void contextLoads() {
    }
}
