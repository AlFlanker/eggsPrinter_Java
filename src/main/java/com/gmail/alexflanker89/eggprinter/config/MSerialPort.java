package com.gmail.alexflanker89.eggprinter.config;


import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MSerialPort {
    private final SerialPort serialPort;

    public MSerialPort() {
        String[] portNames = SerialPortList.getPortNames();
        this.serialPort = new SerialPort(portNames[1]);
        try {
            open();
        } catch (SerialPortException ex) {
            log.error(ex.getMessage());
        }
    }

    public boolean isOpen() {
        return serialPort.isOpened();
    }

    public boolean closePort() throws SerialPortException {
        return serialPort.closePort();
    }

    public boolean openPort() throws SerialPortException {
        if(isOpen()) return true;
        return open();
    }

    public void write(byte[] data) throws SerialPortException {
        if (!isOpen()) {
            openPort();
        }
        for(byte b: data) {
            serialPort.writeByte(b);
        }

    }

    private boolean open() throws SerialPortException {
        // Открываем порт
        boolean isOpen = serialPort.openPort();
        // Выставляем параметры
        serialPort.setParams(SerialPort.BAUDRATE_115200,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        return isOpen;
    }
}
