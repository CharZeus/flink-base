package dto;

import java.util.Arrays;
import java.util.List;

public class UserAction {

    public String id;
    public long recordTimeStamp;
    public String action;
    public String product;
    public int price;

    public String getId() {
        return id;
    }

    public long getRecordTimeStamp() {
        return recordTimeStamp;
    }

    public String getAction() {
        return action;
    }

    public String getProduct() {
        return product;
    }

    public int getPrice() {
        return price;
    }

    public UserAction() {

    }

    public UserAction(String id, long recordTimeStamp, String action, String product, int price) {
        this.id = id;
        this.recordTimeStamp = recordTimeStamp;
        this.action = action;
        this.product = product;
        this.price = price;
    }

    @Override
    public String toString() {
        return "UserAction{" +
                "id='" + id + '\'' +
                ", recordTimeStamp=" + recordTimeStamp +
                ", action='" + action + '\'' +
                ", product='" + product + '\'' +
                ", price=" + price +
                '}';
    }

    public List<UserAction> getMockData() {
        return Arrays.asList(
                new UserAction("user1",1705138380,"click","product1",10),
                new UserAction("user1",1705238380,"click","product2",8),
                new UserAction("user2",1705348380,"search","product2",8),
                new UserAction("user1",1705448380,"browse","product2",8),
                new UserAction("user2",1705538380,"buy","product1",10)
        );
    }
}
