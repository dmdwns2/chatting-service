package com.example.adapter;

import com.example.port.CurrentDataTimePort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeAdapter implements CurrentDataTimePort {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}