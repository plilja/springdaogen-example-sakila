package se.plilja.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.plilja.example.dbframework.BaseEntity;
import se.plilja.example.dbframework.ChangedAtTracked;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Store implements BaseEntity<Integer>, ChangedAtTracked<LocalDateTime> {

    private Integer storeId;
    @NotNull
    private Integer addressId;
    private LocalDateTime lastUpdate;
    @NotNull
    private Integer managerStaffId;

    @JsonIgnore
    @Override
    public Integer getId() {
        return storeId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.storeId = id;
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
