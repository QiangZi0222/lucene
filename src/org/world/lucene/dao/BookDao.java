package org.world.lucene.dao;

import java.util.List;

import org.world.lucene.pojo.Book;

public interface BookDao {
	
	List<Book> queryListBook();
	
}
