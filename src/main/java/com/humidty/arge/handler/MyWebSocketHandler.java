package com.humidty.arge.handler;



import com.humidty.arge.service.DeviceService;
import com.humidty.arge.service.SessionManagementService;
import com.humidty.arge.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Arrays;
import java.util.List;

//@Service
@RequiredArgsConstructor
public class MyWebSocketHandler extends TextWebSocketHandler  {
    // Kabul edilecek deviceID'lerin listesi
    private final List<String> acceptedDeviceIds = Arrays.asList("ARDUINO001", "ARDUINO002", "ARDUINO003");
    private final SessionManagementService sessionManagementService;

    private WebSocketService webSocketService;

    private DeviceService deviceService;


//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException, IOException {
//        // get payload from message
//        String payload = message.getPayload();
//
//        // Gelen payload mesajını bir JSON objesine dönüştürün
//        JSONObject jsonObj = new JSONObject(payload);
//
//        // JSON objesinden deviceID ve humidity değerlerini alın
//        String deviceId = jsonObj.getString("deviceID");
//
//        // Eğer deviceID, kabul edilenler listesinde değilse, bağlantıyı kapat
//        if (!acceptedDeviceIds.contains(deviceId)) {
//            return;
//        }
//
//        double humidity = jsonObj.getDouble("humidity");
//
////        String responseMessage = webSocketService.handleHumidity(deviceId,humidity);
//
////        session.sendMessage(responseMessage);
//
//    }

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
//        // Cihazın deviceID'sini alın (bu kodun düzgün çalışabilmesi için,
//        // cihazın ilk mesajının deviceID'sini içermesi gerekmektedir)
//        Map<String, String> pathParameters = UriComponentsBuilder.fromUri(Objects.requireNonNull(session.getUri())).build().getQueryParams()
//                .toSingleValueMap();
//
//        String deviceID = pathParameters.get("deviceID");
//
//        webSocketService.onOffInfoUpdateDevice(true,deviceID);
//        // devicein last state check edilip ona gore mesaj gonderiliyor
////        sessionManagementService.registerSession(deviceID,session,deviceService.prepareStatusDeviceJson(deviceID,"Device Connect successfully"));
//
//
//    }


//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        Map<String, String> pathParameters = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams()
//                .toSingleValueMap();
//
//        String deviceID = pathParameters.get("deviceID");
//
//        webSocketService.onOffInfoUpdateDevice(false,deviceID);
//
//    }
}