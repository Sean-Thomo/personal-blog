package personal.blog.blog;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


@Controller
public class BlogController {
    private HashMap<String, String> userCredentials = new HashMap<>();

    private static final String FILE_NAME = "articles.json";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Gson GSON = new Gson();

    JsonArray articlesArray = new JsonArray();

    private static final String USERNAME = "seansthomo@gmail.com";
    private static final String PASSWORD = "password123";

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/article")
    public String article() {
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
        JsonObject newArticleObject = new JsonObject();
        newArticleObject.addProperty("title", title);
        newArticleObject.addProperty("content", content);
        newArticleObject.addProperty("date", DATE_FORMAT.format(java.time.LocalDate.now()));
        articlesArray.add(newArticleObject);
        saveArticles(articlesArray);
        System.out.println("New article added: " + title + " - " + content);
        return "dashboard";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password) {
        if (userCredentials.containsKey(email) && userCredentials.containsKey(password)) {
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
        if (!userCredentials.containsKey(email)) {
            userCredentials.put(email, password);
            System.out.println("User registered: " + email);
            return "dashboard";
        } else {
            System.out.println("User already exists: " + email);
            return "login";
        }
    }

    private static void saveArticles(JsonArray articlesArray) {
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(GSON.toJson(articlesArray));
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving article: " + e.getMessage());
        }
    }
}
