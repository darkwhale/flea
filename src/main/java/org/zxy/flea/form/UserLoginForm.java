package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginForm {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
