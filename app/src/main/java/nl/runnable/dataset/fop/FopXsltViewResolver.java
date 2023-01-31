package nl.runnable.dataset.fop;

import lombok.NonNull;
import org.apache.fop.apps.FopFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import java.util.Locale;

public class FopXsltViewResolver extends XsltViewResolver {

    private final FopFactory fopFactory;

    public FopXsltViewResolver(@NonNull FopFactory fopFactory) {
        this.fopFactory = fopFactory;
        setContentType("application/pdf");
        setViewClass(FopXsltView.class);
    }

    @Override
    protected boolean canHandle(String viewName, Locale locale) {
        return viewName.matches("^.+\\.fo$");
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        final var view = (FopXsltView) super.buildView(viewName);
        view.setFopFactory(fopFactory);
        return view;
    }
}
