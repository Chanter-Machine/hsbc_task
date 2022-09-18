package common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionTest {
    @Test
    @DisplayName("Test md5 generation")
    void assertGetMd5() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String res = Encryption.GetMd5("");
        Assertions.assertEquals(res, "d41d8cd98f00b204e9800998ecf8427e");

        res = Encryption.GetMd5("123");
        Assertions.assertEquals(res, "202cb962ac59075b964b07152d234b70");

    }
}