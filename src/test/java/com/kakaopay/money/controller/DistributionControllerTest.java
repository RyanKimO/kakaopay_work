package com.kakaopay.money.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import com.kakaopay.money.dto.DistributionDTO;
import com.kakaopay.money.dto.DividendDTO;
import com.kakaopay.money.service.DistributionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DistributionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistributionService distributionService;

    private DistributionDTO sample;


    @BeforeEach
    public void setUp() {
        DividendDTO dividend = new DividendDTO();
        dividend.setReceiverId(2L);
        dividend.setAmount(500L);

        sample = new DistributionDTO();
        sample.setInitialAmount(1000L);
        sample.setDistributedAmount(500L);
        sample.setDistributedAt(LocalDateTime.now());
        sample.setDistributedHistory(Collections.singletonList(dividend));
    }


    @Test
    public void getDistribution() throws Exception {
        Mockito.when(distributionService
                .getDistributionDTO(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(sample);

        mockMvc.perform(MockMvcRequestBuilders.get("/distribution/111")
                .header(DistributionController.USER_ID_ATTR, 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.initialAmount").value(1000))
                .andExpect(jsonPath("$.data.distributedAmount").value(500))
                .andExpect(jsonPath("$.data.distributedHistory[0].amount").value(500))
                .andExpect(jsonPath("$.data.distributedHistory[0].receiverId").value(2));
    }

}