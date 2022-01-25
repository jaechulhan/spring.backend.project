package com.prolancer.core.util;

import com.prolancer.core.common.utils.AES256Utils;

public class TestAES256Utils {
    public static void main(String[] args) {
        String arrStr[] = {"test@test.com", "+1 (123) 456-7890", "password123!@#"};

        for (String orgStr : arrStr) {
            String encStr = AES256Utils.encrypt(orgStr);
            String decStr = AES256Utils.decrypt(encStr);
            System.out.println("Origin : " + orgStr);
            System.out.println("Encrypted : " + encStr);
            System.out.println("Decrypted : " + decStr);
        }
    }
}
