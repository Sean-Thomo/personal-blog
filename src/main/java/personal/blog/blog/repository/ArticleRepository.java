package personal.blog.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.blog.blog.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    // Custom query methods can be defined here
}
