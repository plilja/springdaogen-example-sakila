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
public class CustomerList {

    @NotNull
    @Size(max = 50)
    private String address;
    @NotNull
    @Size(max = 50)
    private String city;
    @NotNull
    @Size(max = 50)
    private String country;
    @NotNull
    private Integer id;
    @Size(max = 91)
    private String name;
    @NotNull
    @Size(max = 6)
    private String notes;
    @NotNull
    @Size(max = 20)
    private String phone;
    @NotNull
    private Integer sid;
    @Size(max = 10)
    private String zipCode;

}
