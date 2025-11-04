package com.jaberrantisi.piagent.endpoint;

import com.jaberrantisi.piagent.service.EnvMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowTestController {

    private final EnvMessageService envMessageService;

    @Autowired
    public FlowTestController(EnvMessageService envMessageService) {
        this.envMessageService = envMessageService;
    }
    @GetMapping("test")
    public void test() {
        System.out.println("Endpoint");
        envMessageService.sendTestPayload();
    }

}
