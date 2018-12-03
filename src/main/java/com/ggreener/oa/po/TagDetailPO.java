package com.ggreener.oa.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/12/3.
 * <p>
 * XXX
 */
@Data
public class TagDetailPO implements Serializable {
    private static final long serialVersionUID = 5008600220042545624L;
    private Long tagId;
    private Long companyId;
    private String name;
    private Long parentId;
}
