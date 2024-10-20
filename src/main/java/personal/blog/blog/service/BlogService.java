package personal.blog.blog.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import personal.blog.blog.entity.Article;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;


@Service
public class BlogService {
    private static final String LOGIN_FILE = "login_details.json";
    private static final String ARTICLE_FILE = "articles.json";
    private static final Gson GSON = new Gson();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private HashMap<String, String> userCredentials = new HashMap<>();
    JsonArray userCredentialsArray = new JsonArray();
    int maxId = 0;

    public List<Article> getArticles() {
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

    public Article getArticleById(int id) {
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
        return foundArticle;
    }

    public String login(String email, String password) {
        System.out.println("User Credentials:" + userCredentials.toString());

        if (userCredentials.containsValue(email) && userCredentials.containsValue(password)) {
            System.out.println("Login successful for user: " + email);
            return "dashboard";
        } else {
            System.out.println("Login failed for user: " + email);
            return "signup";
        }
    }

    public String signup(String email, String password) {
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

    public String addArticle(String title, String content) {
        JsonArray articlesArray = new JsonArray();

        try (FileReader reader = new FileReader(ARTICLE_FILE)) {
            articlesArray = (JsonArray) JsonParser.parseReader(reader);
            for (int i = 0; i < articlesArray.size(); i++) {
                JsonObject item = articlesArray.get(i).getAsJsonObject();
                maxId = item.get("id").getAsInt();
            }
        } catch (Exception e) {
            System.out.println("Error reading existing articles: " + e.getMessage());
        }

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

    public void setUserCredentials() {
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
}
