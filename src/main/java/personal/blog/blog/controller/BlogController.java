package personal.blog.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import personal.blog.blog.entity.Article;
import personal.blog.blog.service.BlogService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService;

    @GetMapping("/")
    public String home(Model model) {
        blogService.setUserCredentials();
        List<Article> articles = blogService.getArticles();

        if (!articles.isEmpty()) {
            model.addAttribute("articles", articles);
        } else {
            model.addAttribute("articles", new ArrayList<>());
        }
        return "index";
    }

    @GetMapping("/article")
    public String article() {
        return "article";
    }

    @GetMapping("/article/{id}")
    public String getArticleById(@PathVariable int id, Model model) {
        Article foundArticle = blogService.getArticleById(id);
        model.addAttribute("article", foundArticle);
        return "article";
    }

    @GetMapping("/edit")
    public String edit() {
        return "edit";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String title, @RequestParam String content) {
        blogService.addArticle(title, content);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password) {
        return blogService.login(email, password);
    }

    @GetMapping("/signup")
    public String signup() {
        blogService.setUserCredentials();
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@RequestParam String email, @RequestParam String password) {
        String singnup = blogService.signup(email, password);
        if (singnup.equals("success")) {
            return "dashboard";
        } else {
            System.out.println("User already exists: " + email);
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public List<Article> dashboard(Model model) {
        List<Article> articles = blogService.getArticles();
        model.addAttribute("articles", articles);
        return articles;
    }

}
