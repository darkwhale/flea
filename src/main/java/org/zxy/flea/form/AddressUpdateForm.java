package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddressUpdateForm {

    @NotNull
    private Integer addressId;

    @NotNull
    private Integer addressType;

    @NotBlank
    private String addressRegion;

    @NotBlank
    private String addressFloor;

}
