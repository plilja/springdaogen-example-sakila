package se.plilja.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.plilja.example.dbframework.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmText implements BaseEntity<Integer> {

    private Integer filmId;
    @Size(max = 65535)
    private String description;
    @NotNull
    @Size(max = 255)
    private String title;

    @JsonIgnore
    @Override
    public Integer getId() {
        return filmId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.filmId = id;
    }

}
