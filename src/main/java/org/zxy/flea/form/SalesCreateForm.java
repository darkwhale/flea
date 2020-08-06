package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SalesCreateForm {

    @NotEmpty
    private String salesName;

    @NotEmpty
    private String synopsis;

    private String image_info;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer salesType;

    @NotNull
    private Integer newLevel;

    @NotEmpty
    private String items;

}
