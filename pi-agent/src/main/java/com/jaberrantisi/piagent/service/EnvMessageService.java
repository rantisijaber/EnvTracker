package com.jaberrantisi.piagent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaberrantisi.piagent.dto.EnvMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class EnvMessageService {

    private final ObjectMapper mapper;
    private final IoTCoreService iotCoreService;

    @Autowired
    public EnvMessageService(ObjectMapper mapper, IoTCoreService iotCoreService) {
        this.mapper = mapper;
        this.iotCoreService = iotCoreService;
    }

    public String payloadCreator(EnvMessageDTO envMessageDTO) {
        try {
            return mapper.writeValueAsString(envMessageDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not transform message into valid payload form: " + e);
        }
    }


    public void SendPayload(EnvMessageDTO envMessageDTO) {
        iotCoreService.sendMessage(payloadCreator(envMessageDTO));
    }

    public void sendTestPayload() {
        System.out.println("Creating dto");
        EnvMessageDTO envTest = EnvMessageDTO.builder()
                .date(LocalDate.now().toString())
                .co2Ppm(700)
                .tempC(15.0)
                .humidityPercentage(10.0)
                .build();
        System.out.println("Dto created");
        iotCoreService.sendMessage(payloadCreator(envTest));
    }

}
