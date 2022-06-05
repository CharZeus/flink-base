package com.example.source

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment

object dataFromMySource {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val stream4 = env.addSource(new MySource())
    stream4.print()
  }

}
