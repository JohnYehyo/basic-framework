package com.rongji.rjsoft.web.config;

import com.rongji.rjsoft.common.security.util.RSAUtils;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

/**
 * @description: Jasypt EncryptablePropertyResolver 自定义实现
 * @author: JohnYehyo
 * @create: 2021-10-09 10:46:56
 */
public class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {

    private EncryptablePropertyDetector detector = null;

    public EncryptablePropertyDetector getDetector(){
        if(null == detector){
            detector = new MyEncryptablePropertyDetector();
        }
        return detector;
    }

    static String pk = "";

    static {
        pk = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCN8iNPjIWztrpzh7I/Ud3d7goW/MfIVsTrd9VMyEhU/SsNw6PPgQiEffpoKTNh0BQ/aYnJYuyfs5cRR8R38I3NVyMbzWB5+Z2wZyldFsnbBwvYdxqWexdJ5a2ZaTJeOnMvIAVuuvOH4pfpskOQjma2tySOZYwyMNBuZa15/pcnDxf429feg8dQsmzBn63ds3DEOmObym+ipkvOhTn2LPi1Vdc05T57bk9H9ZlCuToXAqRLWS/J8ecz4QtHc9NM2Ze9rzXuxj/Wif6cKAHeQ8DJdVJwUlWbT6RttBZ0BeoN+q0yy0haB9kV/QhQFfdgNzb6GbuJmqj7GIPXpjwj2XkVAgMBAAECggEAF1Flt5WwCRvFeWEbSQeb2O9XvPr9ns2SP9RJt/lW2I9WWUaATIA38/v1/NJ33GOgT+J4fhrAIGQSNwaBIerkgI0F+XU9mgwFeYsY4m/x7Qc1TPQT7BCAfOU8JW2AlrYnYeByxAP2KaZxqjRcoQyjlvRzDuljpIMcWmZdnyLRldrH3nkZjC1aQNeDIitm/AJDUh+j3ARA6QC/xVHK5aogA7hHH9Y5B1ZOUslu+yZ34tfShMRRGyIbpolE1pLbHdZ62E5qbhJUnhmQINgx0Vml9/yTHY2E3mCjwcGvnZ9lXVbiBUgzksmsi+hzeUZPJ9aX10KiSWydeqdLbByZXBYoAQKBgQDVjHegjQu/GbPpBbucjzWQhHVFfeyTfL/16AH09NM8xyc84ZlkToFiRxYXOOX7WDxC6CGjQC0R2fS1IViS12gdNEPnMDlnfvitrr01iGSSl/vrGKIRikczVi2qHIYkP6a/uzCY+xUiI2vVEGjcOjt90ezsJDq5edVcIQAgV1bWFQKBgQCqKcnYXSWCn0rkVH0skiLFwCbAuP7LhU60fWwCS6hy4udv8wI2luYJv95G6TbOGUh23o9wBFefpNnyYLRAG/93zq4PbyjEvQCwge4uyh02DIJpz/4ztZeiEPm3c3BLCyXDzaE6QwCd/W8dzpcsBxO/S5gDwUf+K3+ZMupFQJvXAQKBgQCOYLQVsFSHmixwjajzjivpadcCeR21i/Q0kPZVCrKYEDQiDEwIK6tqCfk5jG8RsFelSD29KXmjME1OQqhaZxH8fq9TQbzUOzy0GBAA1Cox7vFAYtETnr0wBiI2DASHwIR9yuFw7d6+Px2TFfjD9HNbxQ4Qt2sL5KYTYdFCvtifrQKBgQCDmdAGKnvBGurzzbgFSXvlDmeqMyizRAULp9hpqhO7zmqJdRpoF6oPLjl9BA6jtIALlrK+Qk12JSt9vXaKAl1ATjLlsuWHyliHKeWIgD25OFF//iEt4qpD5/cI/xmAoD1zwkuH4JvQlFlkZwsZPNvz3UXS2Z+RHhkP5YWzS3V7AQKBgACshWCowW1XqT4OP0JV819ROL8Csp/otMujO7CUvwgSzz3GyLgitwtoblTmKTjCiluJSXFgCR3/iPGF240zgFWoD4BxfNm0Smn3rQaMjWknqihyGqNJmYF0UGqSPTmzYRHvs3Wg0Nk5IBmn7uc9IXvtVqKO0h7d0q/YBZ4Mowuw";
    }

    @Override
    public String resolvePropertyValue(String s) {
        EncryptablePropertyDetector encryptablePropertyDetector = getDetector();
        try {
            if (encryptablePropertyDetector.isEncrypted(s)) {
                String s1 = encryptablePropertyDetector.unwrapEncryptedValue(s);
                String str = RSAUtils.decrypt(s1, pk);
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
