package personal.blog.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import personal.blog.blog.entity.Article;
import personal.blog.blog.repository.ArticleRepository;

@Service
public class BlogService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(int id) {
        return articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found."));
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
