package com.ggreener.oa.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Data
public class BusinessDataVO implements Serializable {
    private static final long serialVersionUID = 6842873597393690035L;
    private Long id;
    private Long companyId;
    private String year;
    private float totalAsset;
    private float netAsset;
    private float revenue;
    private float profit;
    private String debtRatio;
    private float contractAmount;
    private float investedAmount;
    private Long number;
}
