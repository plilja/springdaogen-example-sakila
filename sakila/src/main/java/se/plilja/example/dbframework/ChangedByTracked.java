package se.plilja.example.dbframework;

/**
 * An entity with a column tracking who/what
 * it was last changed by.
 */
public interface ChangedByTracked {
    void setChangedBy(String value);

    String getChangedBy();
}