package personal.blog.blog.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import personal.blog.blog.entity.Article;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ArticlesController {
    private HashMap<String, String> userCredentials = new HashMap<>();

    private static final String ARTICLE_FILE = "articles.json";
    private static final String LOGIN_FILE = "login_details.json";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Gson GSON = new Gson();

    JsonArray articlesArray = new JsonArray();
    JsonArray userCredentialsArray = new JsonArray();
    int maxId = 0;

    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = getArticles();

        if (!articles.isEmpty()) {
            model.addAttribute("articles", articles);
        } else {
            model.addAttribute("articles", new ArrayList<>());
        }
        return "index";
    }

    private List<Article> getArticles() {
        List<Article> articles = new ArrayList<>();
        try (FileReader reader = new FileReader(ARTICLE_FILE)) {
            JsonArray articlesArray = (JsonArray) JsonParser.parseReader(reader);
            for (int i = 0; i < articlesArray.size(); i++) {
                JsonObject item = articlesArray.get(i).getAsJsonObject();
                Article article = new Article();
                article.setId(item.get("id").getAsInt());
                article.setTitle(item.get("title").getAsString());
                article.setContent(item.get("content").getAsString());
                article.setDate(item.get("date").getAsString());
                articles.add(article);
            }
        } catch (Exception e) {
            System.out.println("No existing article file found, creating a new one.");
        }
        return articles;
    }

    @GetMapping("/article")
    public String article() {
        return "article";
    }

    @GetMapping("/article/{id}")
    public String getArticleById(@PathVariable int id, Model model) {
        Article foundArticle = null;

        try (FileReader reader = new FileReader(ARTICLE_FILE)) {
            JsonArray articlesArray = (JsonArray) JsonParser.parseReader(reader);
            for (int i = 0; i < articlesArray.size(); i++) {
                JsonObject item = articlesArray.get(i).getAsJsonObject();
                Article article = new Article();
                article.setId(item.get("id").getAsInt());
                article.setTitle(item.get("title").getAsString());
                article.setContent(item.get("content").getAsString());
                article.setDate(item.get("date").getAsString());

                if (article.getId() == id) {
                    foundArticle = article;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading articles: " + e.getMessage());
        }

        if (foundArticle != null) {
            model.addAttribute("article", foundArticle);
            return "article";
        } else {
            return "404";
        }
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
        JsonObject newArticleObject = new JsonObject();
        newArticleObject.addProperty("title", title);
        newArticleObject.addProperty("content", content);
        newArticleObject.addProperty("date", DATE_FORMAT.format(LocalDate.now()));
        newArticleObject.addProperty("id", ++maxId);
        articlesArray.add(newArticleObject);
        saveArticles(articlesArray);
        System.out.println("New article added: " + title + " - " + content);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        try (FileReader reader = new FileReader(LOGIN_FILE)) {
            userCredentialsArray = (JsonArray) JsonParser.parseReader(reader);
            for (int i = 0; i < userCredentialsArray.size(); i++) {
                JsonObject user = userCredentialsArray.get(i).getAsJsonObject();
                String email = user.get("email").getAsString();
                String password = user.get("password").getAsString();
                userCredentials.put("email", email);
                userCredentials.put("password", password);
            }
        } catch (Exception e) {
            System.out.println("No existing credentials file found, creating a new one.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password) {
        if (userCredentials.containsValue(email) && userCredentials.containsValue(password)) {
            System.out.println("Login successful for user: " + email);
            return "dashboard";
        } else {
            System.out.println("Login failed for user: " + email);
            return "signup";
        }
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@RequestParam String email, @RequestParam String password) {
        if (!userCredentials.containsValue(email)) {
            JsonObject newUserObject = new JsonObject();
            newUserObject.addProperty("email", email);
            newUserObject.addProperty("password", password);
            userCredentialsArray.add(newUserObject);
            saveUserCredentials(userCredentialsArray);
            System.out.println("User registered: " + email);
            return "dashboard";
        } else {
            System.out.println("User already exists: " + email);
            return "login";
        }
    }

    private void saveUserCredentials(JsonArray userArray) {
        try (FileWriter file = new FileWriter(LOGIN_FILE)) {
            file.write(GSON.toJson(userArray));
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving user credentials: " + e.getMessage());
        }
    }

    private static void saveArticles(JsonArray articlesArray) {
        try (FileWriter file = new FileWriter(ARTICLE_FILE)) {
            file.write(GSON.toJson(articlesArray));
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving article: " + e.getMessage());
        }
    }

    @GetMapping("/dashboard")
    public List<Article> dashboard(Model model) {
        List<Article> articles = getArticles();
        model.addAttribute("articles", articles);
//        return "dashboard";
        return articles;
    }

}
