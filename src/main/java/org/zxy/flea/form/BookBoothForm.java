package org.zxy.flea.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BookBoothForm {

    @NotBlank
    private String userId;

    @NotNull
    private Integer addressId;

    @NotNull
    private Integer campusId;

    @NotBlank
    private String enterYear;

    @NotBlank
    private String boothName;

    @NotBlank
    private String synopsis;

    private Date createTime;

    private Date updateTime;
}
