package session.example01;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库模拟类 BookDB：用静态 Map 模拟数据库中保存的图书
 */
public class BookDB {

    private static Map<String, Book> books = new LinkedHashMap<String, Book>();

    static {
        books.put("1", new Book("1", "javaweb开发"));
        books.put("2", new Book("2", "jdbc开发"));
        books.put("3", new Book("3", "java基础"));
        books.put("4", new Book("4", "struts开发"));
        books.put("5", new Book("5", "spring开发"));
    }

    // 获取全部图书
    public static Collection<Book> getAllBooks() {
        return books.values();
    }

    // 根据编号获取指定图书
    public static Book getBook(String id) {
        return books.get(id);
    }
}
