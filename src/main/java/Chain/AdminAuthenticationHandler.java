package Chain;

import com.PjGl.pjgl.Model.*;
import com.PjGl.pjgl.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AdminAuthenticationHandler implements AuthenticationHandler {
    private AuthenticationHandler nextHandler;
    @Autowired
    private AdminRepo adminRepo;

    @Override
    public void setNextHandler(AuthenticationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String authenticate(String email, String password) {
        if (email.endsWith("@adm.com")) {
            Admin admin = adminRepo.findByEmailAndPassword(email, password);
            if (admin != null) {
                return "redirect:/managers";
            }
        }
        if (nextHandler != null) {
            return nextHandler.authenticate(email, password);
        }
        return "pages/Login";
    }
}
