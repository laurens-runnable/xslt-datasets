package nl.runnable.dataset.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Page {

    private final String siteName;

    private String title;
}
