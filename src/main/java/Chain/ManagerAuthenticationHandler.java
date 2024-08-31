package Chain;

import com.PjGl.pjgl.Model.*;

import com.PjGl.pjgl.Repository.ManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component
public class ManagerAuthenticationHandler implements AuthenticationHandler {
    private AuthenticationHandler nextHandler;
    @Autowired
    private ManagerRepo managerRepo;

    @Override
    public void setNextHandler(AuthenticationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String authenticate(String email, String password) {
        if (email.endsWith("@mng.com")) {
            Manager manager = managerRepo.findByEmailAndPassword(email, password);
            if (manager != null) {
                return "redirect:/managerpage";
            }
        }
        if (nextHandler != null) {
            return nextHandler.authenticate(email, password);
        }
        return "pages/Login";
    }
}
