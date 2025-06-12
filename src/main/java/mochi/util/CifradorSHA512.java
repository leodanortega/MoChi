package mochi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CifradorSHA512 {

    public static String encriptar(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b)); // convierte cada byte a hexadecimal
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SHA-512 no disponible", e);
        }
    }
}
