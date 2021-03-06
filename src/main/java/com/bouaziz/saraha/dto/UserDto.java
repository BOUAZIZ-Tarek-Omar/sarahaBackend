package com.bouaziz.saraha.dto;


import com.bouaziz.saraha.models.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto implements Serializable {

    private Integer id;

    @NotBlank(message = "You need to provide the firstname")//import .validation
    @NotNull
    private String firstname;

    //  @NotNull(message="You need to provide the username")
    private String username;//auto generated

    @NotBlank(message = "You need to provide the lastname")
    @NotNull
    private String lastname;

    //  @NotNull(message="You need to provide the date in the past")
    //@Past(message="the birthday date should be in the past")
    @DateTimeFormat(pattern = "DD/MM/YYYY")
    private LocalDate birthDate;
    // private String birthDate;
    @NotBlank(message = "You need to provide the email")
    @Email(message = "you need to provide a valid email address")
    @NotNull
    private String email;
    @NotBlank(message = "you need to provide the password")
    @Size(min = 8, max = 16, message = "the password should be between 8 and 16 characters")
    @NotNull
    private String password;

}
