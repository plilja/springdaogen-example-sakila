package se.plilja.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
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
public class Film implements BaseEntity<Integer>, ChangedAtTracked<LocalDateTime> {

    private Integer filmId;
    @Size(max = 65535)
    private String description;
    @NotNull
    private Integer languageId;
    private LocalDateTime lastUpdate;
    private Integer length;
    private Integer originalLanguageId;
    @Size(max = 5)
    private String rating;
    private Integer releaseYear;
    @NotNull
    private Integer rentalDuration;
    @NotNull
    private BigDecimal rentalRate;
    @NotNull
    private BigDecimal replacementCost;
    @Size(max = 54)
    private String specialFeatures;
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
