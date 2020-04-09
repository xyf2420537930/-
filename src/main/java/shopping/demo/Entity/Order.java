package shopping.demo.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private Integer id;
    private String time;
    private String username;
    private Integer gid;
    private Integer price;
    private String status;
}
