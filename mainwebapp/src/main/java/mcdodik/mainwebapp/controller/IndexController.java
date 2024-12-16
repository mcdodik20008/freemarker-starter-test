package mcdodik.mainwebapp.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    private Configuration freemarkerConfig;

    @GetMapping(value = {"/index", "/"}, produces = "application/json")
    public ResponseEntity<String> getIndex(HttpServletRequest request) {
        try {
            Map<String, Object> ftlParams = new HashMap<>();
            ftlParams.put("requestUri", request.getRequestURI());
            ftlParams.put("name", "John Doe");
            ftlParams.put("age", 30);
            ftlParams.put("message", "This is a FreeMarker JSON demo!");
            ftlParams.put("hobbies", List.of("reading", "coding", "hiking"));
            ftlParams.put("status", "active");
            ftlParams.put("features", Map.of("freeTrial", true, "premiumAccess", false));
            ftlParams.put("users", List.of(
                    Map.of("id", 1, "name", "Alex", "role", "Admin"),
                    Map.of("id", 2, "name", "Alexandr", "role", "User")
            ));
            ftlParams.put("randomId", UUID.randomUUID());

            Template template = freemarkerConfig.getTemplate("index.ftl");

            String renderedContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, ftlParams);

            return ResponseEntity.ok(renderedContent);

        } catch (Exception e) {
            String errorMessage = "{\"error\": \"Template processing failed: " + e.getMessage() + "\"}";
            return ResponseEntity.status(500).body(errorMessage);
        }
    }

}