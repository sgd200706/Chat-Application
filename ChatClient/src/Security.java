

import java.security.GeneralSecurityException;

/**
 * Created by lightlycat on 10/14/14.
 */
public interface Security {
           public byte[] encrypt(String key, String value) throws GeneralSecurityException;
           public String decrypt(String key, byte[] encrypted) throws GeneralSecurityException ;
}
