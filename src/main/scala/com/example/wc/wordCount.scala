import org.apache.flink.api.scala._ //{AggregateDataSet, DataSet, ExecutionEnvironment}

object wordCount {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment //创建执行环境
    val inputPath = "D:\\Java_Projects\\flink-base\\src\\main\\scala\\com\\example\\data\\test1.txt"
    val inputDS: DataSet[String] = env.readTextFile(inputPath)
    val wordCountDS: AggregateDataSet[(String, Int)] = inputDS.flatMap(_.split(" ")).map((_, 1)).groupBy(0).sum(1)
    wordCountDS.print()
  }
}