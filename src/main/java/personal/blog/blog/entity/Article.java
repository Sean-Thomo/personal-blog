package personal.blog.blog.entity;

public class Article {
    private int id;
    private String title;
    private String content;

    public Article(int id, String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }
}
