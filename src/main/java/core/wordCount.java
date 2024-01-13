package core;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.*;


public class wordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String dataSourcePath =  "D:\\Java_Projects\\flink-base\\src\\main\\scala\\com\\example\\data\\test1.txt";

//        DataStream<String> source = env.readTextFile(dataSourcePath);
        DataStream<String> source = env.socketTextStream("127.0.0.1", 9999, "\n"); // 模拟流式，终端nc -lp 9999,输入
        SingleOutputStreamOperator<Tuple2<String,Integer>> result = source.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] words = s.split(" ");
                for (String word : words){
                    System.out.print(word+" ");
                    collector.collect(new Tuple2<>(word, 1));
                }
            }
        }).filter(new FilterFunction<Tuple2<String, Integer>>() {
            @Override
            public boolean filter(Tuple2<String, Integer> tuple2) throws Exception {
                List<String> other = Arrays.asList(";",",",".","'");
                return !other.contains(tuple2.f0);
            }
        }).
                keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> tuple) throws Exception {
                return tuple.f0;
            }
        })
//                .timeWindow(Time.seconds(15)) // 窗口大小
                .sum(1);

        result.print();
        env.execute();
    }
}
