package nl.runnable.dataset;

import lombok.NonNull;

import java.util.Optional;
import java.util.stream.Stream;

public interface DatasetRepository {

    Optional<Dataset> find(@NonNull String name);

    Stream<Dataset> findAll();

}
