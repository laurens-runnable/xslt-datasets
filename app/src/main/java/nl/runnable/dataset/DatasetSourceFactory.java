package nl.runnable.dataset;

import lombok.NonNull;

import javax.xml.transform.Source;
import java.util.stream.Stream;

public interface DatasetSourceFactory {

    Source createIndexPageSource(Stream<Dataset> datasets);

    Source createDatasetPageSource(@NonNull Dataset dataset);

    Source createDatasetSource(@NonNull Dataset dataset);


}
