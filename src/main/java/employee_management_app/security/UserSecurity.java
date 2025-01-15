package employee_management_app.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Supplier;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String employeeId = extractPathVariable(request.getRequestURI(), "users");
        String currentUserId = ((UserDetails) authentication.get().getPrincipal()).getUsername();

        boolean isAuthorized = currentUserId.equals(employeeId);
        return new AuthorizationDecision(isAuthorized);
    }

    private String extractPathVariable(String uri, String variableName) {
        // Assuming a simple URI structure for illustration, adjust as needed
        String[] parts = uri.split("/");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals(variableName) && i + 1 < parts.length) {
                return parts[i + 1];
            }
        }
        return null;
    }
}

