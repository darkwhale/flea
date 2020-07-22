package org.zxy.flea.form;

import javax.validation.constraints.NotBlank;

public class AddressForm {

    @NotBlank
    private Integer addressType;

    @NotBlank
    private String addressRegion;

    @NotBlank
    private String addressFloor;
}
