package org.world.lucene.dao.Impl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.world.lucene.dao.BookDao;
import org.world.lucene.pojo.Book;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class BookDaoImpl implements BookDao {

	@Override
	public List<Book> queryListBook() {
		// 创建数据库连接
		Connection connection = null;
		// 预编译Statement
		PreparedStatement preparedStatement = null;
		// 结果集
		ResultSet resultSet = null;
		// 图书列表
		List<Book> list = new ArrayList<Book>();
		
		try {
			// 加载数据库去驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 连接数据库
			connection = (Connection) DriverManager.getConnection("jdbc:mysql:///Test", "root", "123");
			String sql = "SELECT * FROM book";
			// 创建preparedStatement
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			// 获取结果集
			resultSet = preparedStatement.executeQuery();
			// 解析结果集
			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setName(resultSet.getString("name"));
				book.setPrice(resultSet.getFloat("price"));
				book.setPic(resultSet.getString("pic"));
				book.setDesc(resultSet.getString("description"));
				list.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
