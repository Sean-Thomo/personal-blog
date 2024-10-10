package personal.blog.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;


@Controller
public class BlogController {

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
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password) {
        if (USERNAME.equals(email) && PASSWORD.equals(password)) {
            System.out.println("Login successful for user: " + email);
            return "dashboard";
        } else {
            // Invalid credentials
            System.out.println("Login failed for user: " + email);
            return "login";
        }
    }
}
