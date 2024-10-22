package personal.blog.blog.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import personal.blog.blog.entity.Article;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    List<Article> articles = new ArrayList<>();
    int maxId = 0;

    public List<Article> getArticles() {
        try (FileReader reader = new FileReader(ARTICLE_FILE)) {
            JsonArray articlesArray = (JsonArray) JsonParser.parseReader(reader);
            for (int i = 0; i < articlesArray.size(); i++) {
                JsonObject item = articlesArray.get(i).getAsJsonObject();
                int id = item.get("id").getAsInt();
                String title = item.get("title").getAsString();
                String content = item.get("content").getAsString();
                String date = item.get("date").getAsString();
                Article article = new Article(title, content, date, id);
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
                String title = item.get("title").getAsString();
                String content = item.get("content").getAsString();
                String date = item.get("date").getAsString();
                int articleId = item.get("id").getAsInt();
                Article article = new Article(title, content, date, articleId);

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

    public void addArticle(Article article) {
        maxId++;
        article.setId(maxId);
        articles.add(article);
        saveArticlesToFile();
    }

    public void updateArticle(Article updatedArticle) {
        List<Article> tempArticleList = new ArrayList<Article>();
        for (Article article : articles) {
            if (article.getId() == updatedArticle.getId()) {
                article.setTitle(updatedArticle.getTitle());
                article.setContent(updatedArticle.getContent());
                article.setDate(DATE_FORMAT.format(java.time.LocalDate.now()));
                article.setId(updatedArticle.getId());
            }
            tempArticleList.add(article);
        }
        this.articles = tempArticleList;
    }

    public void deleteArticle(int id) {
        articles.removeIf(article -> article.getId() == id);
        saveArticlesToFile();
    }

    public String login(String email, String password) {
        System.out.println("User Credentials:" + userCredentials.toString());
        if (userCredentials.containsKey(email) && userCredentials.get(email).equals(password)) {
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
            return "success";
        } else {
            System.out.println("User already exists: " + email);
            return "login";
        }
    }

    private void saveArticlesToFile() {
        JsonArray articlesArray = new JsonArray();
        for (Article article : articles) {
            JsonObject articleObject = new JsonObject();
            articleObject.addProperty("id", article.getId());
            articleObject.addProperty("title", article.getTitle());
            articleObject.addProperty("content", article.getContent());
            articleObject.addProperty("date", article.getDate());
            articlesArray.add(articleObject);
        }
        saveArticles(articlesArray);
    }

    public void setUserCredentials() {
        try (FileReader reader = new FileReader(LOGIN_FILE)) {
            userCredentialsArray = (JsonArray) JsonParser.parseReader(reader);
            for (int i = 0; i < userCredentialsArray.size(); i++) {
                JsonObject user = userCredentialsArray.get(i).getAsJsonObject();
                String email = user.get("email").getAsString();
                String password = user.get("password").getAsString();
                userCredentials.put(email, password);
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
