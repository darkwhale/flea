package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookBoothForm {

    @NotNull
    private Integer addressId;

    @NotNull
    private Integer campusId;

    @NotBlank
    private String boothName;

    @NotBlank
    private String synopsis;

    private String icon;

}
