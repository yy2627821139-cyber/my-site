package session.example01;

/**
 * 封装图书信息的 Book 类
 */
public class Book {
    private String id;   // 图书编号
    private String name; // 图书名称

    public Book() {
    }

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
