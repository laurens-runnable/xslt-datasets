package nl.runnable.dataset.fop;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.web.servlet.view.xslt.XsltView;

import javax.xml.transform.Result;
import javax.xml.transform.sax.SAXResult;

public class FopXsltView extends XsltView {

    @Setter
    private FopFactory fopFactory;

    public FopXsltView() {
        setContentType(MimeConstants.MIME_PDF);
    }

    @Override
    protected Result createResult(HttpServletResponse response) throws Exception {
        final var fop = fopFactory.newFop(MimeConstants.MIME_PDF, response.getOutputStream());
        return new SAXResult(fop.getDefaultHandler());
    }

}
