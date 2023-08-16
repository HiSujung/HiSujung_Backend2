package com.hisujung.web.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisujung.web.dto.PortfolioListResponseDto;

import java.util.List;

public class JsonConverter {
    public static String convertPListToJson(List<PortfolioListResponseDto> portfolios) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(portfolios);
    }
}
