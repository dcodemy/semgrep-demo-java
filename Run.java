import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MyController {

    @GetMapping("/api")
    public void api(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the user input directly from the request
        String userInput = request.getParameter("userInput");
        
        // Sink: Dangerous output (unsanitized user input)
        response.getWriter().write("<html><body><p>" + userInput + "</p></body></html>"); // Vulnerable to XSS
    }

    @GetMapping("/vulnerable")
    public void reflect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the user input directly from the request
        String userInput = request.getParameter("userInput");
        
        // Sink: Dangerous output (unsanitized user input)
        response.getWriter().write("<html><body><p>" + userInput + "</p></body></html>"); // Vulnerable to XSS
    }

    // Safe version: Sanitizing user input to prevent XSS
    @GetMapping("/safe")
    public void safeReflect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the user input directly from the request
        String userInput = request.getParameter("userInput");

        // Sanitizer: Clean the user input to prevent XSS
        String sanitizedInput = sanitizeInput(userInput);

        // Safe output
        response.getWriter().write("<html><body><p>" + sanitizedInput + "</p></body></html>"); // Safe from XSS
    }

    // Sanitizer method to clean the user input
    public String sanitizeInput(String input) {
        // Prevent basic XSS by escaping <, >, &, " and '
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}
