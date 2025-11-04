package com.jaberrantisi.piagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class EnvMessageDTO {

    private String date;
    private Integer co2Ppm;
    private Double tempC;
    private Double humidityPercentage;

}
