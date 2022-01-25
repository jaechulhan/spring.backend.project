package com.prolancer.core.util;

import com.prolancer.core.common.utils.AES256Utils;

public class TestAES256Utils {
    public static void main(String[] args) {
        String arrStr[] = {"jaechulhan@gmail.com", "+1 (301) 265-6220", "password123!@#"};

        for (String orgStr : arrStr) {
            String encStr = AES256Utils.encrypt(orgStr);
            String decStr = AES256Utils.decrypt(encStr);
            System.out.println("Origin : " + orgStr);
            System.out.println("Encrypted : " + encStr);
            System.out.println("Decrypted : " + decStr);
        }

        String arrEncStr[] = {"_AG-VM6Rgv6cvlgUwJs_UDtC1AHKQFIn90RxgWXzbXk", "Z129JOHhjHPpDlZOPtEeUXnEUKK63PboqL6_JCBfE_k", "5vswIXQm3lr8U6rVdo93dQ"};

        for (String encStr : arrEncStr) {
            String decStr = AES256Utils.decrypt(encStr);
            System.out.println("Encrypted : " + encStr);
            System.out.println("Decrypted : " + decStr);
        }
    }
}
