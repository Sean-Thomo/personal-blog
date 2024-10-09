package personal.blog.blog;

import java.util.HashMap;


class LoginDetails {
    
    private HashMap loginDetails;

    public HashMap setLoginDetails(String email, String password) {
        return this.loginDetails = (HashMap) loginDetails.put(email, password);
    }

    public HashMap getLoginDetails() {
        return this.loginDetails;
    }
        


}
