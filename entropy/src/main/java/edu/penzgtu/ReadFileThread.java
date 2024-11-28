package edu.penzgtu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ReadFileThread implements Callable<Map<Byte, Integer>> {
    private final FileInputStream fis;
    private final Map<Byte, Integer> byteFrequencies;

    public ReadFileThread(FileInputStream fis, Map<Byte, Integer> byteFrequencies) {
        this.fis = fis;
        this.byteFrequencies = byteFrequencies;
    }

    @Override
    public Map<Byte, Integer> call() {

        // Подсчет частоты символов отдельного куска файла
        Map<Byte, Integer> localFrequencies = new HashMap<>();
        byte[] buffer = new byte[8192];
        try {
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    byte b = buffer[i];
                    localFrequencies.merge(b, 1, Integer::sum);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Запись значений в единый словать
        for (Map.Entry<Byte, Integer> entry : localFrequencies.entrySet()) {
            byteFrequencies.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }

        return localFrequencies;
    }
}
