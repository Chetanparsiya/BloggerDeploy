package com.unborn.blogger.datatransferobject;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.*;

@Data
public class UserDto {

    private Long id;

    @NotEmpty
    @Size(min=3, message = "FirstName min length should be 3 Character!!!")
    private String firstName;

    @NotEmpty
    @Size(min=3, message = "LastName min length should be 3 Character!!!")
    private String lastName;

    @NotEmpty
    @Size(min = 8, message = "Password min length should be 8 Character!!!")
    //@Pattern(regexp = "/(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/", message = "Password minimum length should be 8 character with small case, uppercase, numbers and Special Symbol !!")
    private String password;

    @Email(message = "Email address is not valid!!!")
    @NotEmpty(message = "Email address can't be blank!!!")
    private String email;

    @NotEmpty
    private String about;
}
