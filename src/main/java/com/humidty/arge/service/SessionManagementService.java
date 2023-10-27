package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

import org.json.*;

@Service
@RequiredArgsConstructor
public class SessionManagementService {
    private  Map<String, WebSocketSession> deviceSessions;


    public WebSocketSession getSessionById(int id) {
        return deviceSessions.get(id);
    }

    public void registerSession(String deviceID, WebSocketSession session, TextMessage lastStatus) throws IOException {
        // Get the deviceID of the device (for this code to work properly,
        // the first message from the device must contain its deviceID)

        deviceSessions.put(deviceID, session);

        System.out.println("Device Connection established " + deviceID);

        session.sendMessage(lastStatus);

    }

    // Diğer oturum yönetimi metodları buraya gelebilir
}