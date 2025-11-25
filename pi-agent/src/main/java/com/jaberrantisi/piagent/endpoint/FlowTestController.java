package com.jaberrantisi.piagent.endpoint;

import com.jaberrantisi.piagent.readings.BME280Reading;
import com.jaberrantisi.piagent.service.EnvMessageService;
import com.jaberrantisi.piagent.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FlowTestController {

    private final EnvMessageService envMessageService;
    private final SensorService sensorService;

    @Autowired
    public FlowTestController(EnvMessageService envMessageService, SensorService sensorService) {
        this.envMessageService = envMessageService;
        this.sensorService = sensorService;
    }
    @GetMapping("test")
    public void test() {
        System.out.println("Endpoint");
        envMessageService.sendTestPayload();
    }

    @GetMapping("test-process")
    public ResponseEntity<String> restProcessCall() throws IOException {

        BME280Reading reading = sensorService.readBME280();
        Float temp = reading.temperature();

        return ResponseEntity.ok(temp.toString());
    }



}
