# EnvTracker

**EnvTracker** is a smart environmental monitoring system built with **Spring Boot** and **Raspberry Pi**.  
It collects real-time environmental data using **BME280** (temperature, humidity, pressure) and **MH-Z19** (CO₂) sensors, sending the readings securely to the cloud through **AWS IoT Core**.  
A **Spring Cloud Function** serves as the backend, processing incoming data, storing it in **DynamoDB**, and sending environment updates and alerts via **AWS SNS**.

![EnvTracker Architecture](EnvTrackerImg.png)

---

## Features
- Real-time monitoring of temperature, humidity, pressure, and CO₂ levels  
- Secure IoT data transmission using **AWS IoT Core**  
- Automatic email alerts via **AWS SNS**  
- Historical data storage in **AWS DynamoDB**  
- Scalable backend powered by **Spring Cloud Function**  
- Modular design for future integration with dashboards or analytics

---

## System Architecture
1. **Raspberry Pi**
   - Collects sensor data using:
     - **BME280** – Measures temperature, humidity, and pressure  
     - **MH-Z19** – Measures CO₂ concentration  
   - Publishes readings to **AWS IoT Core** using MQTT.

2. **AWS IoT Core**
   - Acts as a communication bridge between the Raspberry Pi and the backend.  
   - Forwards data to the **Spring Cloud Function**.

3. **Spring Cloud Function**
   - Serves as the backend that receives and processes IoT data.  
   - Stores sensor readings in **AWS DynamoDB** for historical analysis.  
   - Sends environment updates and alerts via **AWS SNS** email notifications.

---

## Technologies Used
- Java 17  
- Spring Boot  
- Spring Cloud Function  
- AWS IoT Core  
- AWS SNS  
- AWS DynamoDB  
- Raspberry Pi  
- BME280 & MH-Z19 sensors  
- Maven  

---
