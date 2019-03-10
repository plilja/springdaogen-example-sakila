package se.plilja.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmList {

    @Size(max = 65535)
    private String actors;
    @NotNull
    @Size(max = 25)
    private String category;
    @Size(max = 65535)
    private String description;
    private Integer fid;
    private Integer length;
    private BigDecimal price;
    @Size(max = 5)
    private String rating;
    @Size(max = 255)
    private String title;

}
