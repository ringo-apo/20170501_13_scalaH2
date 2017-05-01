package example

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

object H2 {
    def main(args: Array[String]): Unit = {

      //Create Schema
      createSchemaStructure()

      //Create Table
      createTableStructure()

      //Menu
      println("H2データベースを使ったサンプルプログラムです。")
      println("作業を選択してください。1:表示 2:挿入 3:編集 4:削除 5:終了")
      var key = io.StdIn.readLine()
      while (key != "5") {
        key match {
          //表示処理
          case "1" =>
            println("表示処理")
            selectDbStructure()

            println("作業を選択してください。1:表示 2:挿入 3:編集 4:削除 5:終了")
            key = io.StdIn.readLine()

          //挿入処理
          case "2" =>
            println("挿入")
            insertDbStructure()

            println("作業を選択してください。1:表示 2:挿入 3:編集 4:削除 5:終了")
            key = io.StdIn.readLine()

          //編集処理
          case "3" =>
            println("編集")
            updateDbStructure()

            println("作業を選択してください。1:表示 2:挿入 3:編集 4:削除 5:終了")
            key = io.StdIn.readLine()

          //削除処理
          case "4" =>
            println("削除")
            deleteDbStructure()

            println("作業を選択してください。1:表示 2:挿入 3:編集 4:削除 5:終了")
            key = io.StdIn.readLine()
        }
        println("終了します")
      }
    }

  //Create Schema
  private def createSchemaStructure(): Unit = {
    Class.forName("org.h2.Driver")
    val conn: Connection = DriverManager.getConnection( "jdbc:h2:./h2db/TEST_SCHEMA", "sa", "" )

    try {
      val sql = "create schema if not exists TEST_SCHEMA;"
      val stmt = conn.createStatement()

      try {
        stmt.execute(sql)
        println("createSchemaStructure ok")
      }
      finally {
        stmt.close
      }
    }
    finally {
      conn.close()
    }
  }

  //Create Table
  private def createTableStructure(): Unit = {
    Class.forName("org.h2.Driver")
    val conn: Connection = DriverManager.getConnection( "jdbc:h2:./h2db/TEST_SCHEMA", "sa", "" )

    try {
      val sql = "create table if not exists TEST_TABLE (ID int auto_increment primary key,NAME varchar(50));"
      val stmt = conn.createStatement()

      try {
        stmt.execute(sql)
        println("createTableStructure ok")
      }
      finally {
        stmt.close
      }
    }
    finally {
      conn.close()
    }
  }


  //Insert
  private def insertDbStructure(): Unit = {
    Class.forName("org.h2.Driver")
    val conn: Connection = DriverManager.getConnection( "jdbc:h2:./h2db/TEST_SCHEMA", "sa", "" )

    println("文字列を入力してください")
    val name = io.StdIn.readLine()

    try {
      val sql = "insert into TEST_TABLE (NAME) values ('" + name + "');"
      val stmt = conn.createStatement()

      try {
        stmt.executeUpdate(sql)
        println( "insert ok")
      }
      finally {
        stmt.close
      }
    }
    finally {
      conn.close()
    }
  }


  //Select
  private def selectDbStructure(): Unit = {
    Class.forName("org.h2.Driver")
    val conn: Connection = DriverManager.getConnection( "jdbc:h2:./h2db/TEST_SCHEMA", "sa", "" )

    try {
      val sql = "select * from TEST_TABLE;"
      val stmt = conn.createStatement()

      try {
        val rs: ResultSet = stmt.executeQuery(sql)
        println("ID, NAME")
        while (rs.next()) {
          println(s"""${rs.getInt("id")}, ${rs.getString("name")}""")
        }
        rs.close()
      }
      finally {
        stmt.close
      }
      println("select ok")
    }
    finally {
      conn.close()
    }
  }

  //Update
  private def updateDbStructure(): Unit = {
    Class.forName("org.h2.Driver")
    val conn: Connection = DriverManager.getConnection( "jdbc:h2:./h2db/TEST_SCHEMA", "sa", "" )

    println("編集するデータのIDを入力してください")
    val ids = io.StdIn.readLine()
    val idn: Int = ids.toInt

    println("文字列を入力してください")
    val name = io.StdIn.readLine()

    try {
      val sql = "update TEST_TABLE set NAME='" + name + "' where ID="+ idn +";"
      val stmt = conn.createStatement()

      try {
        stmt.executeUpdate(sql)
        println( "update ok")
      }
      finally {
        stmt.close
      }
    }
    finally {
      conn.close()
    }
  }

  //Delete
  private def deleteDbStructure(): Unit = {
    Class.forName("org.h2.Driver")
    val conn: Connection = DriverManager.getConnection( "jdbc:h2:./h2db/TEST_SCHEMA", "sa", "" )

    println("削除するデータのIDを入力してください")
    val ids = io.StdIn.readLine()
    val idn: Int = ids.toInt

    try {
      val sql = "delete from TEST_TABLE where ID=" + idn + ";"
      val stmt = conn.createStatement()

      try {
        stmt.executeUpdate(sql)
        println( "delete ok")
      }
      finally {
        stmt.close
      }
    }
    finally {
      conn.close()
    }
  }

}
