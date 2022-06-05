package com.example.source

import org.apache.flink.streaming.api.functions.source.SourceFunction

import scala.util.Random

class MySource extends SourceFunction[dataFromSet] {
  var running = true

  override def run(sourceContext: SourceFunction.SourceContext[dataFromSet]): Unit = {

    val rand = new Random() //随机数生成器
    var currTemp = 1.to(10).map(i => ("sensor_" + i, 65 + rand.nextGaussian() * 20))

    while (running) {
      currTemp = currTemp.map(t => (t._1, t._2 + rand.nextGaussian())) //更新值
      val currTime = System.currentTimeMillis()
      currTemp.foreach(t => sourceContext.collect(dataFromSet(t._1, currTime, t._2)))
      Thread.sleep(100)
    }
  }

  override def cancel(): Unit = {
    running = false
  }
}
