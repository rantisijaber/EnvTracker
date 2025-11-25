#include "BME280_driver/bme280.h"
#include <stdio.h>
#include <stdint.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <linux/i2c-dev.h>

void user_delay_us(uint32_t period, void *intf_ptr) {
    (void)intf_ptr;
    usleep(period);
}

int8_t user_i2c_read(uint8_t reg_addr, uint8_t *data, uint32_t len, void *intf_ptr) {
    uint8_t dev_id = *(uint8_t*)intf_ptr;
    int fd = open("/dev/i2c-1",  O_RDWR);
    if (fd < 0) return -1;

    if (ioctl(fd, I2C_SLAVE, dev_id) < 0) {
        close(fd);
        return -1;
    }

    if (write(fd, &reg_addr, 1) != 1) {
        close(fd);
        return -1;
    }

    if (read(fd, data, len) != (ssize_t)len) {
        close(fd);
        return -1;
    }

    close(fd);
    return 0;
}

int8_t user_i2c_write(uint8_t reg_addr,const uint8_t *data, uint32_t len, void *intf_ptr) {
     uint8_t dev_id = *(uint8_t*)intf_ptr;
     int fd = open("/dev/i2c-1", O_RDWR);
     if (fd < 0) return -1;

    if (ioctl(fd, I2C_SLAVE, dev_id) < 0) {
        close(fd);
        return -1;
    }

    uint8_t buffer[len + 1];
    buffer[0] = reg_addr;
    for (uint32_t i = 0; i < len; i++){
        buffer[i + 1] = data[i];
    }

    if (write(fd, buffer, len + 1) != (ssize_t)(len + 1)) {
        close(fd);
        return -1;
    }

    close(fd);
    return 0;
}

int main() {
    struct bme280_dev dev;
    struct bme280_data data;
    struct bme280_settings settings;

    int8_t rs;

    uint8_t i2c_address = BME280_I2C_ADDR_PRIM;

    dev.intf = BME280_I2C_INTF;
    dev.read = user_i2c_read;
    dev.write = user_i2c_write;
    dev.intf_ptr = &i2c_address;
    dev.delay_us = user_delay_us;

    if (bme280_init(&dev) != BME280_OK) {
        printf("BME280 Initialization failed!");
        return -1;
    }

    settings.osr_h = BME280_OVERSAMPLING_1X;
    settings.osr_p = BME280_OVERSAMPLING_1X;
    settings.osr_t = BME280_OVERSAMPLING_1X;
    settings.filter = BME280_FILTER_COEFF_OFF;
    settings.standby_time = BME280_STANDBY_TIME_125_MS;

    uint8_t settings_sel = BME280_SEL_ALL_SETTINGS;
    if (bme280_set_sensor_settings(settings_sel, &settings, &dev)) {
        printf("Failed to set settings");
        return -1;
    }

    if (bme280_set_sensor_mode(BME280_POWERMODE_NORMAL, &dev) != BME280_OK) {
        printf("Failed to set sensor mode");
        return -1;
    }

    dev.delay_us(100000, dev.intf_ptr);

    if (bme280_get_sensor_data(BME280_ALL, &data, &dev) == BME280_OK) {
        printf("%.2f %.2f  %.2f\n", data.temperature, data.pressure / 100.0, data.humidity);
    } else {
        printf("Failed to read\n");
    }
}

