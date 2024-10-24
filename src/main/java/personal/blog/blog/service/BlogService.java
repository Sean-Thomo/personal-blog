package personal.blog.blog.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import personal.blog.blog.entity.Article;

@Service
public class BlogService {
    List<Article> articleList = new ArrayList<>(Arrays.asList(
            new Article(1,"Article 1", "This is the first article content.", "01-01-2022"),
            new Article(2,"Article 2", "This is the second article content.", "02-01-2022"),
            new Article(3,"Article 3", "This is the third article content.", "03-01-2022")
    ));

    public List<Article> getArticles() {
        return articleList;
    }

    public Article getArticleById(int id) {
        return articleList.stream().filter(e -> (e.getId() == id)).findFirst().get();
    }

    public void createArticle(Article article) {
        articleList.add(article);
    }

    public void updateArticle(Article updatedArticle) {
        articleList.removeIf(article -> article.getId() == updatedArticle.getId());
        articleList.add(updatedArticle);
    }

    public void deleteArticle(int id) {
        articleList.removeIf(article -> article.getId() == id);
    }
}
