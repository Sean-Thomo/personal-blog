package personal.blog.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.blog.blog.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
//    CRUD Operations
}
