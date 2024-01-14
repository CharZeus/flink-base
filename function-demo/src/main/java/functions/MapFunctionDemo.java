package functions;

import dto.UserAction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

// 用户操作的产品的价格提高10倍
public class MapFunctionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<UserAction> source = env.fromCollection(new UserAction().getMockData());
        // 输入输出同类型
        SingleOutputStreamOperator<UserAction> result = source.map(new MapFunction<UserAction, UserAction>() {
            @Override
            public UserAction map(UserAction value) {
                int newPrice = value.getPrice() * 10;
                return new UserAction(value.getId(),value.getRecordTimeStamp(),value.getAction(),value.getProduct(),newPrice);
            }
        });
        result.print();
        env.execute("job");
    }
}
