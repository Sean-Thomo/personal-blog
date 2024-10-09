package personal.blog.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BlogController {

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
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setLoginDetails(email, password);

        loginDetails.getLoginDetails();
        return "dashboard";
    }
}
