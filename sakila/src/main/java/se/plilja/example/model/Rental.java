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
public class Rental implements BaseEntity<Integer>, ChangedAtTracked<LocalDateTime> {

    private Integer rentalId;
    @NotNull
    private Integer customerId;
    @NotNull
    private Integer inventoryId;
    private LocalDateTime lastUpdate;
    @NotNull
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    @NotNull
    private Integer staffId;

    @JsonIgnore
    @Override
    public Integer getId() {
        return rentalId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.rentalId = id;
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
