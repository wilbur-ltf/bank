package com.bank.trade.controller;

import com.bank.common.exception.UtilException;
import com.bank.common.vo.R;
import com.bank.trade.application.command.TradeCommandService;
import com.bank.trade.application.query.TradeQueryService;
import com.bank.trade.domain.dto.TradeCreateDTO;
import com.bank.trade.domain.dto.TradeUpdateDTO;
import com.bank.trade.domain.vo.TradeVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TradeControllerTest {

    @Autowired
    private TradeController tradeController;

    @Autowired
    private TradeCommandService tradeCommandService;

    @Autowired
    private TradeQueryService tradeQueryService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private TradeVO mockVO;
    private TradeCreateDTO mockCreateDTO;
    private TradeUpdateDTO mockUpdateDTO;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockVO = new TradeVO().setId(1L)
                .setTradeCode("1234567890")
                .setAmount(BigDecimal.valueOf(100))
                .setType(1)
                .setCreateTime(LocalDateTime.now())
                .setStatus("1");
        mockCreateDTO = new TradeCreateDTO()
                .setTradeCode("1234567890")
                .setAmount(BigDecimal.valueOf(100))
                .setType(1)
                .setStatus("1");
        mockUpdateDTO = new TradeUpdateDTO()
                .setId(1L)
                .setTradeCode("1234567890")
                .setAmount(BigDecimal.valueOf(100))
                .setType(1)
                .setStatus("1");
    }

    // 测试创建成功
    @Test
    public void should_returnTradeVO_when_createTrade_given_validDTO() throws Exception {
        // given

        // when
        MvcResult result = mockMvc.perform(post("/api/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreateDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        R<TradeVO> response = objectMapper.readValue(responseContent, new TypeReference<>() {
        });
        TradeVO responseTrade = response.getData();

        // then
        assertNotNull(responseTrade, "Response trade should not be null");
        assertNotNull(responseTrade.getId(), "Trade ID should not be null");
        assertEquals(mockCreateDTO.getTradeCode(), responseTrade.getTradeCode(), "Trade tradeCode should match");

        List<TradeVO> trades = tradeQueryService.listAllTrades();
        assertEquals(mockCreateDTO.getTradeCode(), trades.get(0).getTradeCode());

        // 删除数据，避免对其他case的影响
        tradeCommandService.removeTrade(responseTrade.getId());
    }

    // 测试创建重复异常
    @Test
    public void should_returnDuplicateException_when_createTrade_given_duplicateTradeCode() throws Exception {
        // given

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreateDTO)))
                .andExpect(status().isOk())
                .andReturn();
        // 删除数据，避免对其他case的影响
        String responseContent = mvcResult.getResponse().getContentAsString();
        R<TradeVO> response = objectMapper.readValue(responseContent, new TypeReference<>() {
        });

        Exception exception = assertThrows(ServletException.class,
                () -> mockMvc.perform(post("/api/trade")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockCreateDTO)))
                        .andExpect(status().isConflict())
                        .andReturn()
        );

        // then
        // 验证异常类型
        assertTrue(exception.getCause() instanceof UtilException);
        UtilException utilException = (UtilException) exception.getCause();
        assertEquals("90001", utilException.getExceptionCode().getCode());

        // 删除数据，避免对其他case的影响
        tradeCommandService.removeTrade(response.getData().getId());
    }

    // 测试删除交易成功
    @Test
    public void should_returnSuccess_when_removeTrade_given_validId() throws Exception {
        // given
        TradeVO mockVO = tradeCommandService.createTrade(mockCreateDTO);// 先创建记录

        // when
        MvcResult result = mockMvc.perform(delete("/api/trade/{id}", mockVO.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        List<TradeVO> trades = tradeQueryService.listAllTrades();
        assertTrue(trades.stream().noneMatch(t -> t.getId().equals(mockVO.getId())));
    }

    // 测试修改交易
    @Test
    public void should_returnUpdatedVO_when_modifyTrade_given_validDTO() throws Exception {
        // given
        TradeVO existing = tradeCommandService.createTrade(mockCreateDTO);// 先创建记录

        TradeUpdateDTO tradeUpdateDTO = new TradeUpdateDTO();
        tradeUpdateDTO.setId(existing.getId());
        tradeUpdateDTO.setTradeCode(existing.getTradeCode());
        tradeUpdateDTO.setAmount(BigDecimal.valueOf(200)); // 修改金额
        tradeUpdateDTO.setType(existing.getType());
        tradeUpdateDTO.setStatus(existing.getStatus());

        // when
        MvcResult result = mockMvc.perform(put("/api/trade/{id}", existing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeUpdateDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        R<TradeVO> response = objectMapper.readValue(responseContent, new TypeReference<>() {
        });
        TradeVO updated = response.getData();

        // then
        assertEquals(BigDecimal.valueOf(200), updated.getAmount());

        // 清理
        tradeCommandService.removeTrade(existing.getId());
    }

    // 测试查询所有交易
    @Test
    public void should_returnTradeList_when_listAllTrades_given_validRequest() throws Exception {
        // given
        tradeCommandService.createTrade(mockCreateDTO); // 先创建记录

        // when
        MvcResult result = mockMvc.perform(get("/api/trade/list"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        R<List<TradeVO>> response = objectMapper.readValue(responseContent, new TypeReference<>() {
        });
        List<TradeVO> trades = response.getData();

        // then
        assertEquals(1, trades.size());
        assertEquals(mockCreateDTO.getTradeCode(), trades.get(0).getTradeCode());

        // 清理
        tradeCommandService.removeTrade(trades.get(0).getId());
    }

}