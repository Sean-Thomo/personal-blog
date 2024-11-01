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
    List<Article> articleList = new ArrayList<>(Arrays.asList(
            new Article(1,"Article 1", "This is the first article content.", "01-01-2022"),
            new Article(2,"Article 2", "This is the second article content.", "02-01-2022"),
            new Article(3,"Article 3", "This is the third article content.", "03-01-2022")
    ));

    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getArticles() {
        return articleList;
    }

    public Article getArticleById(int id) {
        return articleList.stream().filter(e -> (e.getId() == id)).findFirst().get();
    }

    public void createArticle(Article article) {
//        articleList.add(article);
        articleRepository.save(article);
    }

    public void updateArticle(Article updatedArticle) {
        ArrayList<Article> tempArticleList = new ArrayList<>();
        for (Article article : articleList) {
            if((article.getId() == updatedArticle.getId())) {
                article.setTitle(updatedArticle.getTitle());
                article.setContent(updatedArticle.getContent());
            }
        }
        this.articleList = tempArticleList;
    }

    public void deleteArticle(int id) {
//        articleList.removeIf(article -> article.getId() == id);
        ArrayList<Article> tempArticleList = new ArrayList<>();
        for (Article article : articleList) {
            if(article.getId() == id)
                continue;
            tempArticleList.add(article);
        }
        this.articleList = tempArticleList;
    }
}
