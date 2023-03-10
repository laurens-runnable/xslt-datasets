package nl.runnable.dataset.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.View;

import java.util.Map;

@RequiredArgsConstructor
class ResourceView implements View {

    @NonNull
    private final Resource resource;

    @NonNull
    private final String contentType;

    @Override
    public void render(@NonNull Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(contentType);
        StreamUtils.copy(resource.getInputStream(), response.getOutputStream());
    }
}
