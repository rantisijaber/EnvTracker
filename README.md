# EnvTracker

**EnvTracker** is a smart environmental monitoring platform built with **Spring Boot**, **AWS Lambda**, and a **Raspberry Pi**.  
It collects real-time environmental data using **BME280** (temperature, humidity, pressure) and **MH-Z19** (CO₂) sensors and securely sends these readings to the cloud through **AWS IoT Core**.

A hybrid backend consisting of **Spring Cloud Function**, **three AWS Lambda functions**, and Raspberry Pi–side native **C sensor code** powers data ingestion, storage, analytics, and alerting.

![EnvTracker Architecture](EnvTrackerImg.png)

---

## Features
- Real-time monitoring of temperature, humidity, pressure, and CO₂ levels  
- Secure IoT MQTT communication via **AWS IoT Core**  
- Native **C code** on Raspberry Pi to communicate directly with hardware  
- Spring Boot container invokes C code to collect sensor data  
- Three AWS Lambda functions for data storage, analytics prep, and alerting  
- Automatic email notifications when environmental conditions exceed safe ranges  
- Fast historical lookups with **AWS DynamoDB**  
- Long-term analytics through **S3 + AWS Glue + Amazon Athena**  
- Modular backend built with **Spring Cloud Function**  

---

## System Architecture

### 1. **Raspberry Pi**
The Raspberry Pi runs two components:

#### **A. Native C Sensor Module**
A lightweight C program directly communicates with the hardware sensors:
- Reads temperature, humidity, and pressure from the **BME280**
- Reads CO₂ data from the **MH-Z19**
- Interacts with I²C and UART interfaces efficiently  
- Outputs sensor data in JSON or simple key-value format

This C program provides highly reliable, low-latency access to the hardware.

#### **B. Spring Boot Container on the Pi**
A Spring Boot application (running in a lightweight container or directly on the OS):
- Invokes the native C executable using `ProcessBuilder`
- Parses the output from the C program
- Converts the sensor reading into a structured model
- Publishes the data to **AWS IoT Core** over MQTT

This hybrid approach keeps hardware-level code minimal and performant (in C) while higher-level logic runs in Java.

---

### 2. **AWS IoT Core**
- Provides secure communication between the Raspberry Pi and the backend  
- Routes incoming IoT messages to:
  - **Spring Cloud Function**
  - **Three AWS Lambda functions**

---

## Backend Processing

### **A. Spring Cloud Function**
Acts as the main application logic layer:
- Receives IoT messages  
- Performs validation, normalization, and enrichment of sensor data  
- Coordinates downstream processing  

---

### **B. AWS Lambda Functions**

#### **1. DynamoDB Writer Lambda**
- Saves each incoming sensor reading into a DynamoDB table  
- Enables fast lookups and real-time dashboards  

#### **2. S3 Storage Lambda (Analytics Pipeline)**
- Stores each reading into an S3 bucket in partitioned folders  
- AWS Glue Crawler updates schema  
- Athena provides SQL-based analytics  

#### **3. Alerting Lambda (SNS Email Notifications)**
- Evaluates readings for unsafe environmental conditions  
- Sends alert emails via AWS SNS  

---

## Pipeline Summary

1. Raspberry Pi reads sensor data using native C code  
2. Spring Boot container executes the C program, parses output  
3. Spring Boot publishes the reading to AWS IoT Core  
4. IoT Core triggers Lambdas + Spring Cloud Function  
5. Data gets written to DynamoDB + S3  
6. Alerts sent via SNS when needed  
7. Long-term analytics available via Athena  

---

## Technologies Used
- **C (for Raspberry Pi sensor hardware drivers)**  
- **Java 17**  
- **Spring Boot**  
- **Spring Cloud Function**  
- **AWS Lambda**  
- **AWS IoT Core (MQTT)**  
- **AWS SNS**  
- **AWS DynamoDB**  
- **AWS S3 + AWS Glue + Amazon Athena**  
- **Docker**
- **Raspberry Pi**  
- **BME280 & MH-Z19 sensors**  
- **Maven**

---


