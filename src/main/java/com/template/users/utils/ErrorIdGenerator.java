package com.template.users.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

/**
 * Generador de IDs únicos para errores del servidor.
 * Utiliza una combinación de timestamp, contador atómico y números aleatorios
 * para garantizar la unicidad de los IDs generados.
 */
@Component
public class ErrorIdGenerator {
    
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final AtomicLong COUNTER = new AtomicLong(0);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    
    /**
     * Genera un ID único para un error del servidor.
     * 
     * Formato: ERR-{timestamp}-{contador}-{aleatorio}
     * Ejemplo: ERR-20241116143022-000001-A7B9C2
     * 
     * @return String ID único del error
     */
    public String generateErrorId() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        long counter = COUNTER.incrementAndGet();
        String randomPart = generateRandomHex(6);
        
        return String.format("ERR-%s-%06d-%s", timestamp, counter, randomPart);
    }
    
    /**
     * Genera una cadena hexadecimal aleatoria de longitud específica.
     * 
     * @param length Longitud deseada de la cadena hexadecimal
     * @return String cadena hexadecimal aleatoria en mayúsculas
     */
    private String generateRandomHex(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%X", SECURE_RANDOM.nextInt(16)));
        }
        return sb.toString();
    }
    
    /**
     * Resetea el contador interno (útil para testing).
     * No se recomienda usar en producción.
     */
    public void resetCounter() {
        COUNTER.set(0);
    }
}