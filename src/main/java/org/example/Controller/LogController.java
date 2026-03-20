package org.example.Controller;

import org.example.Service.LogChunk;
import org.example.Service.LogService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/view")
    public LogChunk viewLogs(
            @RequestParam(defaultValue = "0") long offset,
            @RequestParam(defaultValue = "50") int limit) throws IOException {
        return logService.getLogChunk(offset, limit);
    }
}