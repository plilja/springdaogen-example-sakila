package se.plilja.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorInfo {

    @NotNull
    private Integer actorId;
    @Size(max = 65535)
    private String filmInfo;
    @NotNull
    @Size(max = 45)
    private String firstName;
    @NotNull
    @Size(max = 45)
    private String lastName;

}
