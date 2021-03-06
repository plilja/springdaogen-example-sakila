package se.plilja.example.dbframework;

/**
 * An entity with a column tracking when
 * it was last created.
 */
public interface CreatedAtTracked<T> {
    /**
     * Mark the entity as created right now.
     */
    void setCreatedNow();

    T getCreatedAt();
}