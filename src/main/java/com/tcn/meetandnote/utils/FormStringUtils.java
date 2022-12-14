package com.tcn.meetandnote.utils;

public class FormStringUtils {

    public static String getMailVerifyForm(String token) {
        return """
                <h2>Vui lòng ấn xác nhận để xác nhận tài khoản của bạn tại đây</h2>
                <br/>
                <a href="http://localhost:8080/verify?verifyToken=""" + token +
                """
                        ">Xác nhận</a>
                        """;
    }

}
