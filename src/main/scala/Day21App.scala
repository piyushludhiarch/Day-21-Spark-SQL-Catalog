import org.apache.spark.sql.SparkSession

object Day21App {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Day21 Spark SQL Catalog")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val employees = Seq(
      (1,"Amit","Data Engineering",70000),
      (2,"Rahul","Data Engineering",65000),
      (3,"Sara","Analytics",75000),
      (4,"John","Cloud",80000)
    ).toDF("id","name","department","salary")

    employees.createOrReplaceTempView("employees")

    spark.sql("""
      SELECT department,
             COUNT(*) AS employees,
             AVG(salary) AS avg_salary,
             MAX(salary) AS max_salary
      FROM employees
      GROUP BY department
      ORDER BY avg_salary DESC
    """).show(false)

    spark.stop()
  }
}
