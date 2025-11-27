package com.jaberrantisi.piagent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaberrantisi.piagent.dto.EnvMessageDTO;
import com.jaberrantisi.piagent.readings.BME280Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class EnvMessageService {

    private final ObjectMapper mapper;
    private final IoTCoreService iotCoreService;
    private final SensorService sensorService;

    @Autowired
    public EnvMessageService(ObjectMapper mapper, IoTCoreService iotCoreService, SensorService sensorService) {
        this.mapper = mapper;
        this.iotCoreService = iotCoreService;
        this.sensorService = sensorService;
    }

    public String payloadCreator(EnvMessageDTO envMessageDTO) {
        try {
            return mapper.writeValueAsString(envMessageDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not transform message into valid payload form: " + e);
        }
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000 / 10)
    public void SendPayload() throws Exception {
        BME280Reading bme280Reading = sensorService.readBME280();
        EnvMessageDTO envMessageDTO = EnvMessageDTO.builder()
                        .date(LocalDate.now().toString())
                        .pressure(Double.valueOf(bme280Reading.pressure()))
                        .tempC(Double.valueOf(bme280Reading.temperature()))
                        .humidityPercentage(Double.valueOf(bme280Reading.humidity()))
                        .build();
        iotCoreService.sendMessage(payloadCreator(envMessageDTO));
    }

}
