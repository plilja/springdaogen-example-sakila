package se.plilja.example.dbframework;

public interface BaseEntity<ID> {
    void setId(ID id);

    ID getId();

}