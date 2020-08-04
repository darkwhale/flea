package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SalesCreateForm {

    @NotEmpty
    private String salesName;

    @NotEmpty
    private String synopsis;

    private String image_info;

    @NotNull
    private Integer salesType;

    @NotNull
    private Integer newLevel;

    @NotEmpty
    private String items;

}
