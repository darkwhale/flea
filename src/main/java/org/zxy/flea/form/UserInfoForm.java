package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserInfoForm {

    @NotNull
    private Integer userGender;

    @NotBlank
    private String userMobile;

    @NotBlank
    private String userQq;

    @NotBlank
    private String userWx;

    @NotNull
    private Integer userResideAddressId;

    @NotNull
    private Integer userStudyAddressId;

    @NotNull
    private Integer userGrade;

}
