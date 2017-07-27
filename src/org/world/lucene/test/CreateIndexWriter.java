package org.world.lucene.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.world.lucene.dao.BookDao;
import org.world.lucene.dao.Impl.BookDaoImpl;
import org.world.lucene.pojo.Book;

/**
 * 创建索引
 * 
 * @author QiangZi
 *
 */
public class CreateIndexWriter {

	/**
	 * 创建索引
	 * 
	 * @throws IOException
	 */
	@Test
	public void createIndexWriter() throws IOException {
		// 采集数据
		BookDao bookDao = new BookDaoImpl();
		List<Book> listBook = bookDao.queryListBook();
		// 创建文档
		List<Document> documents = new ArrayList<>();
		for (Book book : listBook) {
			Document document = new Document();
			// Document文档添加到Field域中
			// Store.YES:表示存储到文档域中
			document.add(new StoredField("id", book.getId().toString()));
			document.add(new StringField("name", book.getName().toString(), Store.YES));
			document.add(new FloatField("price", book.getPrice(), Store.YES));
			document.add(new StoredField("pic", book.getPic().toString()));
			document.add(new TextField("desc", book.getDesc().toString(), Store.NO));
			// 将document放到list中
			documents.add(document);
		}
		// 创建分词器
		Analyzer analyzer = new IKAnalyzer();
		// 创建Directory对象,声明索引库的位置
		Directory directory = FSDirectory.open(new File("/Users/apple/lucene/index"));
		// 创建IndexWriterConfig对像，写入索引需要的配置
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		// 创建IndexWriter写入对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// 写入到索引库，通过IndexWriter添加文档对象Document
		for (Document doc : documents) {
			indexWriter.addDocument(doc);
		}
		// 释放资源
		indexWriter.close();
	}

	/**
	 * 删除索引
	 * 
	 * @throws IOException
	 */
	@Test
	public void indexDelete() throws IOException {
		// 创建Directiory流对象
		Directory directory = FSDirectory.open(new File("/Users/apple/lucene/index"));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, new IKAnalyzer());
		// 创建写入对象
		IndexWriter writer = new IndexWriter(directory, config);
		// 根据Team删除索引库
		writer.deleteDocuments(new Term("name", "java"));
		// 释放资源
		writer.close();
	}

}
