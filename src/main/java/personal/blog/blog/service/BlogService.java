package personal.blog.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import personal.blog.blog.entity.Article;
import personal.blog.blog.repository.ArticleRepository;

@Service
public class BlogService {
    @Autowired
    ArticleRepository articleRepository;

    List<Article> articleList = new ArrayList<>(Arrays.asList(
            new Article(1,"Article 1", "This is the first article content."),
            new Article(2,"Article 2", "This is the second article content."),
            new Article(3,"Article 3", "This is the third article content.")
    ));

    public List<Article> getArticles() {
        return articleList;
    }

    public Article getArticleById(int id) {
        return articleList.stream().filter(e -> (e.getId() == id)).findFirst().get();
    }

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public void updateArticle(Article updatedArticle) {
        articleRepository.save(updatedArticle);
    }

    public void deleteArticle(int id) {
        articleRepository.delete(articleRepository.getReferenceById(id));
    }
}
