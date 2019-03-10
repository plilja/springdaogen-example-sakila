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
public class Address implements BaseEntity<Integer>, ChangedAtTracked<LocalDateTime> {

    private Integer addressId;
    @NotNull
    @Size(max = 50)
    private String address;
    @Size(max = 50)
    private String address2;
    @NotNull
    private Integer cityId;
    @NotNull
    @Size(max = 20)
    private String district;
    private LocalDateTime lastUpdate;
    @NotNull
    private byte[] location;
    @NotNull
    @Size(max = 20)
    private String phone;
    @Size(max = 10)
    private String postalCode;

    @JsonIgnore
    @Override
    public Integer getId() {
        return addressId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.addressId = id;
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
