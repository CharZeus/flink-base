package functions;

import dto.UserAction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

// 统计各个用户动作次数
public class FlatMapFunctionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<UserAction> source = env.fromCollection(new UserAction().getMockData());
        SingleOutputStreamOperator<Tuple2<String, Integer>> result = source.flatMap(new FlatMapFunction<UserAction, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(UserAction userAction, Collector<Tuple2<String, Integer>> collector) throws Exception {
                collector.collect(new Tuple2<>(userAction.getId(),1));
            }
        }).keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> tuple) {
                return tuple.f0;
            }
        }).sum(1);
        result.print();
        env.execute();
    }
}
