package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class WaresBoothForm {

    @NotNull
    private Integer addressId;

    @NotEmpty
    private String boothName;

    @NotEmpty
    private String synopsis;

    private String image_info;
}
