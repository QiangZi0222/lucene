package org.world.lucene.test;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

/**
 * 搜索索引
 * 
 * @author QiangZi
 *
 */
public class SearchIndexTest {

	@Test
	public void testSearchIndex() throws Exception {
		// 创建分词器
		Analyzer analyzer = new StandardAnalyzer();
		// 创建搜索解析器
		QueryParser queryParser = new QueryParser("desc", analyzer);
		// 创建搜索对象
		Query query = queryParser.parse("desc:java AND lucene");
		// 创建Directory流对象,声明索引库位置
		Directory directory = FSDirectory.open(new File("/Users/apple/lucene/index"));
		// 创建索引读取对象IndexReander
		IndexReader reader = DirectoryReader.open(directory);
		// 创建索引搜索对象
		IndexSearcher searcher = new IndexSearcher(reader);
		// 使用索引搜索对象，执行搜索，返回结果集TopDocs
		// 第一个参数：搜索对象，第二个参数：返回的数据条数，指定查询结果最顶部的n条数据返回
		TopDocs topDocs = searcher.search(query, 5);
		System.out.println("查询到得数据总条数是: " + topDocs.totalHits);
		// 获取查询结果集
		ScoreDoc[] docs = topDocs.scoreDocs;
		// 解析结果集
		for (ScoreDoc scoreDoc : docs) {
			// 获取文档
			int docID = scoreDoc.doc;
			Document doc = searcher.doc(docID);
			System.out.println("=============================");
			System.out.println("docID:" + docID);
			System.out.println("bookId:" + doc.get("id"));
			System.out.println("name:" + doc.get("name"));
			System.out.println("price:" + doc.get("price"));
			System.out.println("pic:" + doc.get("pic"));
		}
		// 释放资源
		reader.close();
	}
}
