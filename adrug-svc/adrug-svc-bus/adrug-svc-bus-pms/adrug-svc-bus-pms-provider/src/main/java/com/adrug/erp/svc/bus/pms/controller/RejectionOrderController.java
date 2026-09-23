package com.adrug.erp.svc.bus.pms.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.RejectionOrderProvider;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拒收单 Controller。
 * <p>
 * URL 路径与 {@code RejectionOrderFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/pms/rejection-order")
public class RejectionOrderController {

    @Autowired
    private RejectionOrderProvider rejectionOrderProvider;

    @PostMapping("/page")
    public Result<PageResult<RejectionOrderVO>> page(@RequestBody RejectionOrderQuery query) {
        return Result.success(rejectionOrderProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<RejectionOrderVO> getById(@PathVariable("id") Long id) {
        return Result.success(rejectionOrderProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody RejectionOrderSaveDTO dto) {
        return Result.success(rejectionOrderProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody RejectionOrderSaveDTO dto) {
        rejectionOrderProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        rejectionOrderProvider.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/post")
    public Result<Void> post(@PathVariable("id") Long id) {
        rejectionOrderProvider.post(id);
        return Result.success();
    }
}
