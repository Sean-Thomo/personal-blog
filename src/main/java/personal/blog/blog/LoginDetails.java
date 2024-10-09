package personal.blog.blog;

import java.util.HashMap;


class LoginDetails {
    
    private HashMap loginDetails;

    public void setLoginDetails(String email, String password) {
        this.loginDetails = (HashMap) loginDetails.put(email, password);
    }

    public HashMap getLoginDetails() {
        return this.loginDetails;
    }
        


}
