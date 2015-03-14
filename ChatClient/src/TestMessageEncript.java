import java.security.GeneralSecurityException;

/**
 * Created by lightlycat on 10/13/14.
 */
public class TestMessageEncript {

    public static void main(String[] args) {

        try {

            EncodeMsg em = new EncodeMsg();
            String key = "ThisIsASecretKey";
            byte[] ciphertext = em.encrypt(key, "I am Olivia.");
            System.out.println("decrypted value:" + (em.decrypt(key, ciphertext)));

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
