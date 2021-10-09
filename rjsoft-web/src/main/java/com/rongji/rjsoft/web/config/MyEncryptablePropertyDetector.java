package com.rongji.rjsoft.web.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2021-10-09 10:30:27
 */
public class MyEncryptablePropertyDetector implements EncryptablePropertyDetector {

    private String prefix = "HOLIDAY{";
    private String suffix = "}";

    @Override
    public boolean isEncrypted(String property) {
        if (property == null) {
            return false;
        } else {
            String trimmedValue = property.trim();
            return trimmedValue.startsWith(this.prefix) && trimmedValue.endsWith(this.suffix);
        }
    }

    @Override
    public String unwrapEncryptedValue(String property) {
        return property.substring(this.prefix.length(), property.length() - this.suffix.length());
    }
}
