package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.UserInfo.getAuthInfo;
import static ru.netology.data.UserInfo.getVerificationCodeFor;

public class CardBalanceTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }
}
