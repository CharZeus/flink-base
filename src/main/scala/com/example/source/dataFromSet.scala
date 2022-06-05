package com.example.source

import org.apache.flink.api.scala._

case class dataFromSet(id:String,timestamp: Long,temperature:Double)

object Sensor{
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val stream1 = env.fromCollection(List(
      dataFromSet("sensor_1", 1547718199, 35.80018327300259),
      dataFromSet("sensor_2", 1547718201, 15.402984393403084),
      dataFromSet("sensor_3", 1547718202, 6.720945201171228)
    ))
    stream1.print("stream1:").setParallelism(1)
    env.execute()
  }
}
