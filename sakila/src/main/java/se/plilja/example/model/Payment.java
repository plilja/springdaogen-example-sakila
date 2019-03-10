package se.plilja.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
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
public class Payment implements BaseEntity<Integer>, ChangedAtTracked<LocalDateTime> {

    private Integer paymentId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Integer customerId;
    private LocalDateTime lastUpdate;
    @NotNull
    private LocalDateTime paymentDate;
    private Integer rentalId;
    @NotNull
    private Integer staffId;

    @JsonIgnore
    @Override
    public Integer getId() {
        return paymentId;
    }

    @JsonIgnore
    @Override
    public void setId(Integer id) {
        this.paymentId = id;
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
