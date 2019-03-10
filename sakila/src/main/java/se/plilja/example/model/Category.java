package se.plilja.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.plilja.example.dbframework.BaseEntity;
import se.plilja.example.dbframework.ChangedAtTracked;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category implements BaseEntity<Integer>, ChangedAtTracked<LocalDateTime> {

    private Integer categoryId;
    private LocalDateTime lastUpdate;
    @NotNull
    @Size(max = 25)
    private String name;

    @JsonIgnore
    @Override
    public Integer getId() {
        return categoryId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.categoryId = id;
    }

    @JsonIgnore
    @Override
    public LocalDateTime getChangedAt() {
        return lastUpdate;
    }

    @Override
    public void setChangedNow() {
        lastUpdate = LocalDateTime.now();
    }

}
