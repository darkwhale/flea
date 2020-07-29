package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CampusForm {

    @NotNull
    private Integer campusId;

    @NotBlank
    private String campusName;
}
