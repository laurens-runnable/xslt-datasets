package nl.runnable.dataset;

import org.springframework.core.io.Resource;

public record Dataset(String name, Resource resource) {
}
