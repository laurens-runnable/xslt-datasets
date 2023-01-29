package nl.runnable.dataset.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * ControllerAdvice that provides common XSLT stylesheet parameters.
 * <p>
 * The attributes are accessed from stylesheets via <code>&lt;xsl:param&gt;</code>.
 */
@ControllerAdvice
public class StylesheetParamsControllerAdvice {

    @ModelAttribute("path")
    String path(HttpServletRequest request) {
        var path = request.getServletPath();
        if (path == null) {
            path = "/";
        }
        return path;
    }

    @ModelAttribute("contextPath")
    String contextPath(HttpServletRequest request) {
        return request.getContextPath();

    }
}
