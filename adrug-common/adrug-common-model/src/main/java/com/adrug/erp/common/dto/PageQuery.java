package com.adrug.erp.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基类。
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 页码，从 1 开始 */
    private long pageNum = 1;

    /** 每页条数 */
    private long pageSize = 20;
}
