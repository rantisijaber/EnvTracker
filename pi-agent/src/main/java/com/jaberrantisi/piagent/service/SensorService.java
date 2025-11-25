package com.jaberrantisi.piagent.service;

import com.jaberrantisi.piagent.readings.BME280Reading;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
public class SensorService {

    @Value("${BME280_PATH}")
    private String bme280Path;


    public BME280Reading readBME280() throws IOException {
        if (bme280Path == null) {
            throw new RuntimeException("Did not pass bme280 executable path");
        }
        System.out.println(bme280Path);
        ProcessBuilder pb = new ProcessBuilder(bme280Path);
        pb.redirectErrorStream(true);

        Process process = pb.start();
           try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
               String line = reader.readLine();

               String[] parts = line.split("\\s+");

               Float temperature = Float.parseFloat(parts[0]);
               Float pressure = Float.parseFloat(parts[1]);
               Float humidity = Float.parseFloat(parts[2]);

               return new BME280Reading(temperature, pressure, humidity);

           }


    }


}
