package com.bank.trade.controller;

import com.bank.common.vo.R;
import com.bank.trade.application.command.TradeCommandService;
import com.bank.trade.application.query.TradeQueryService;
import com.bank.trade.domain.dto.TradeCreateDTO;
import com.bank.trade.domain.dto.TradeUpdateDTO;
import com.bank.trade.domain.vo.TradeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/trade")
@Tag(name = "Trade Management", description = "Trade management APIs")
public class TradeController {

    @Resource
    private TradeCommandService tradeCommandService;

    @Resource
    private TradeQueryService tradeQueryService;


    @Operation(summary = "Create a new trade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "操作成功"),
            @ApiResponse(responseCode = "7777", description = "未知业务异常"),
            @ApiResponse(responseCode = "7778", description = "参数校验失败"),
            @ApiResponse(responseCode = "90001", description = "交易单已存在")
    })
    @PostMapping
    public R<TradeVO> createTrade(@RequestBody TradeCreateDTO dto) {
        TradeVO vo = tradeCommandService.createTrade(dto);
        return R.ok(vo);
    }

    @Operation(summary = "Delete a trade by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "操作成功"),
            @ApiResponse(responseCode = "7777", description = "未知业务异常"),
            @ApiResponse(responseCode = "7778", description = "参数校验失败"),
            @ApiResponse(responseCode = "90002", description = "交易单不存在")
    })
    @DeleteMapping("/{id}")
    public R<Void> removeTrade(@PathVariable Long id) {
        tradeCommandService.removeTrade(id);
        return R.ok();
    }

    @Operation(summary = "Modify an existing trade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "操作成功"),
            @ApiResponse(responseCode = "7777", description = "未知业务异常"),
            @ApiResponse(responseCode = "7778", description = "参数校验失败"),
            @ApiResponse(responseCode = "90002", description = "交易单不存在")
    })
    @PutMapping("/{id}")
    public R<TradeVO> modifyTrade(@PathVariable Long id, @RequestBody TradeUpdateDTO dto) {
        TradeVO vo = tradeCommandService.modifyTrade(dto);
        return R.ok(vo);
    }

    @Operation(summary = "Get all trades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "操作成功"),
            @ApiResponse(responseCode = "7777", description = "未知业务异常")
    })
    @GetMapping("/list")
    public R<List<TradeVO>> listAllTrades() {
        List<TradeVO> list = tradeQueryService.listAllTrades();
        return R.ok(list);
    }


}
