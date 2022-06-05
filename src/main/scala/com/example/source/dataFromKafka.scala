package com.example.source

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011

object dataFromKafka {
  val env = StreamExecutionEnvironment.getExecutionEnvironment
  val properties = new Properties()
  properties.getProperty("bootstrap.servers", "localhost:9092")
  properties.setProperty("group.id", "consumer-group")
  properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
  properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
  properties.setProperty("auto.offset.reset", "latest")

  val stream3 = env.addSource(new FlinkKafkaConsumer011[String]("sensor", new
      SimpleStringSchema(), properties))
}
