# SpringBoot-Humidity-Tracking-MQTT-Api
# Remote Management and Data Observation Project

This project aims to observe various data and remotely manage devices by communicating with Arduino devices. The project communicates with devices using the MQTT protocol and is managed through an API developed with the Spring Framework. Additionally, the project includes humidity control and efficiency monitoring features.

## Features

- **Remote Management**: Ability to remotely manage Arduino devices through communication over MQTT.
- **Data Observation**: Observe and analyze data received from devices.
- **Humidity Control**: Control the humidity level of the environment and perform humidification if necessary.
- **Efficiency Monitoring**: Monitor and report efficiency through the system.

## Technologies

- **Spring Boot**: For API development.
- **MQTT**: Communication protocol with devices.
- **Amazon Web Services (AWS)**: Server where the application is deployed.

## Installation
Install the required dependencies:
mvn install
Run the application:
mvn spring-boot:run

API Usage
You can perform various operations through the API. Below are some examples:

Get Device Status
GET /api/devices/{deviceId}

This endpoint retrieves the status of the device with the specified ID.

Update Device
PUT /api/devices/{deviceId}

This endpoint updates the status of the device with the specified ID.

License
This project is licensed under the MIT License. For more information, please see the LICENSE file.

Contact
If you have any questions or suggestions, please contact us through the issues page.



1. Clone the project:
   ```bash
   git clone https://github.com/farukaydogan/SPRING-BOOT-WATERING-AND-MANAGMENT-API.git

<img width="1118" alt="Screenshot 2023-10-28 at 02 00 13" src="https://github.com/farukaydogan/ARGE-WATERING/assets/57232389/f5a1ca67-a4af-4ff2-b5b8-f4681f595d26">
<img width="1135" alt="Screenshot 2023-10-28 at 02 00 27" src="https://github.com/farukaydogan/ARGE-WATERING/assets/57232389/6c315655-e005-4356-8933-28e615532130">


https://github.com/farukaydogan/SpringBoot-Humidity-Tracking-Web-Socket-Api/assets/57232389/40a8a0e5-97b2-49d3-a088-2c0ee940ef55
<img width="1182" alt="Screenshot 2023-10-28 at 02 00 45" src="https://github.com/farukaydogan/ARGE-WATERING/assets/57232389/546b4543-d1cb-4c62-8a55-4b23ce4f7c15">
