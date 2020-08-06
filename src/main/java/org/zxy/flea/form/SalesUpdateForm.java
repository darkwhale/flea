package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SalesUpdateForm {

    @NotEmpty
    private String salesId;

    @NotEmpty
    private String salesName;

    @NotEmpty
    private String synopsis;

    @NotNull
    private BigDecimal price;

    private String image_info;

    @NotNull
    private Integer newLevel;

    @NotEmpty
    private String items;

}
