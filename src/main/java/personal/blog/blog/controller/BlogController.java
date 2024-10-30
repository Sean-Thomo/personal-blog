package personal.blog.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import personal.blog.blog.entity.Article;
import personal.blog.blog.service.BlogService;

import java.util.List;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService;

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> findAllArticles() {
        List<Article> articles = blogService.getArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> findAnArticle(@PathVariable int id) {
        Article article = blogService.getArticleById(id);
        if (article != null) {
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/articles")
    public ResponseEntity<Void> createArticle(@RequestBody Article updatedArticle) {
        blogService.createArticle(updatedArticle);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable int id, @RequestBody Article updatedArticle) {
        updatedArticle.setId(id);
        blogService.updateArticle(updatedArticle);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
        blogService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
