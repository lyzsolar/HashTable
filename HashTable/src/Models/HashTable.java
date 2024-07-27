package Models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class HashTable {

    private static final int TABLE_SIZE = 100;
    private LinkedList<Entry>[] table;


    public HashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }


    private int hashFunctionSHA256(String key) {
        String sha256Hash = hashStringSHA256(key);
        return Math.abs(sha256Hash.hashCode() % TABLE_SIZE);
    }


    private int hashFunctionMD5(String key) {
        String md5Hash = hashStringMD5(key);
        return Math.abs(md5Hash.hashCode() % TABLE_SIZE);
    }

    public void insert(String key, String value, boolean useSHA256) {
        int index = useSHA256 ? hashFunctionSHA256(key) : hashFunctionMD5(key);


        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        table[index].add(new Entry(key, value));
    }

    public String get(String key, boolean useSHA256) {
        int index = useSHA256 ? hashFunctionSHA256(key) : hashFunctionMD5(key);
        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    private static class Entry {
        String key;
        String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


    public static String hashStringSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hashStringMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void printTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (!table[i].isEmpty()) {
                System.out.print("[" + i + "] -> ");
                for (Entry entry : table[i]) {
                    System.out.print("(" + entry.key + ", " + entry.value + ") ");
                }
                System.out.println();
            }
        }
    }
}
