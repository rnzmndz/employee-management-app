package employee_management_app.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final Pattern numericPattern = Pattern.compile("^[0-9]+$");

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        try {
            HttpServletRequest request = context.getRequest();
            
            // Get the current authenticated user
            Authentication auth = authentication.get();
            if (auth == null || !auth.isAuthenticated()) {
                return new AuthorizationDecision(false);
            }

            Object principal = auth.getPrincipal();
            if (!(principal instanceof UserDetails)) {
                return new AuthorizationDecision(false);
            }

            // Extract user ID from the current request
            String requestedUserId = extractPathVariable(request.getRequestURI());
            if (requestedUserId == null) {
                return new AuthorizationDecision(false);
            }

            // Get current user's ID
            String currentUserId = ((UserDetails) principal).getUsername();

            // Validate both IDs are numeric (if using numeric IDs)
            if (!numericPattern.matcher(requestedUserId).matches() || 
                !numericPattern.matcher(currentUserId).matches()) {
                return new AuthorizationDecision(false);
            }

            // Check if the current user is accessing their own data
            boolean isAuthorized = currentUserId.equals(requestedUserId);
            
            // You could also add additional checks here, like admin override
            // boolean isAdmin = auth.getAuthorities().stream()
            //     .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            // isAuthorized = isAuthorized || isAdmin;

            return new AuthorizationDecision(isAuthorized);
            
        } catch (Exception e) {
            // Log the error here
            return new AuthorizationDecision(false);
        }
    }

    private String extractPathVariable(String uri) {
        String[] segments = uri.split("/");
        for (int i = 0; i < segments.length - 1; i++) {
            if (segments[i].equals("users") && i + 1 < segments.length) {
                // Handle potential query parameters
                String id = segments[i + 1];
                int queryParamIndex = id.indexOf('?');
                return queryParamIndex > 0 ? id.substring(0, queryParamIndex) : id;
            }
        }
        return null;
    }
}