package personal.blog.blog.entity;

public class Article {
    private String title;
    private String content;
    private String date;
    private int id;

    public Article(String title, String content, String date, int id) {
        this.title = title;
        this.content = content;
        this.date = date;
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }
}
