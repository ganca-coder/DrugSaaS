package com.adrug.erp.app.term.pos.controller;

import com.adrug.erp.app.term.pos.service.PosService;
import com.adrug.erp.app.term.pos.service.PosQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * POS收银台 Controller（应用层，读写分离）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/pos")
public class PosAppController {

    @Autowired
    private PosQueryService posQueryService;
    @Autowired
    private PosService posOrderService;

    @PostMapping("/drug/search")
    public Result<PageResult<DrugVO>> searchDrug(@RequestBody DrugQuery query) {
        return Result.success(posQueryService.searchDrug(query));
    }

    @PostMapping("/member/search")
    public Result<PageResult<MemberVO>> searchMember(@RequestBody MemberQuery query) {
        return Result.success(posQueryService.searchMember(query));
    }

    @PostMapping("/retail-order")
    public Result<Long> createRetailOrder(@RequestBody PosRetailOrderSaveDTO dto) {
        return Result.success(posOrderService.createRetailOrder(dto));
    }
}
