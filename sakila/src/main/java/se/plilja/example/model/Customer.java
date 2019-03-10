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
import se.plilja.example.dbframework.CreatedAtTracked;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements BaseEntity<Integer>, CreatedAtTracked<LocalDateTime>, ChangedAtTracked<LocalDateTime> {

    private Integer customerId;
    @NotNull
    private Boolean active;
    @NotNull
    private Integer addressId;
    private LocalDateTime createDate;
    @Size(max = 50)
    private String email;
    @NotNull
    @Size(max = 45)
    private String firstName;
    @NotNull
    @Size(max = 45)
    private String lastName;
    private LocalDateTime lastUpdate;
    @NotNull
    private Integer storeId;

    @JsonIgnore
    @Override
    public Integer getId() {
        return customerId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.customerId = id;
    }

    @JsonIgnore
    @Override
    public LocalDateTime getCreatedAt() {
        return createDate;
    }

    @Override
    public void setCreatedNow() {
        createDate = LocalDateTime.now();
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
