package se.plilja.example.dbframework;

/**
 * An entity with a column tracking who/what
 * it was created by.
 */
public interface CreatedByTracked {
    void setCreatedBy(String value);

    String getCreatedBy();
}