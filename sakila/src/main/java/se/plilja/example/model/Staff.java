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
public class Staff implements BaseEntity<Integer>, ChangedAtTracked<LocalDateTime> {

    private Integer staffId;
    @NotNull
    private Boolean active;
    @NotNull
    private Integer addressId;
    @Size(max = 50)
    private String email;
    @NotNull
    @Size(max = 45)
    private String firstName;
    @NotNull
    @Size(max = 45)
    private String lastName;
    private LocalDateTime lastUpdate;
    @Size(max = 40)
    private String password;
    private byte[] picture;
    @NotNull
    private Integer storeId;
    @NotNull
    @Size(max = 16)
    private String username;

    @JsonIgnore
    @Override
    public Integer getId() {
        return staffId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.staffId = id;
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
