package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.UserInfo.*;
import static ru.netology.page.DashboardPage.firstCardButton;
import static ru.netology.page.DashboardPage.secondCardButton;

public class CardBalanceTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Replenishment of the first card account")
    public void shouldReplenishedFirstCard() {
        var dashboardPage = new DashboardPage();
        var firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        var secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        int amount = 3_000;

        var transferPage = firstCardButton();
        transferPage.makeTransfer(String.valueOf(amount), getSecondCardNumber());
        var firstCardBalanceResult = firstCardBalanceStart + amount;
        var secondCardBalanceResult = secondCardBalanceStart - amount;

        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    @DisplayName("Replenishment of the second card account")
    public void shouldReplenishedSecondCard() {
        var dashboardPage = new DashboardPage();
        var firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        var secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        int amount = 7_000;

        var transferPage = secondCardButton();
        transferPage.makeTransfer(String.valueOf(amount), getFirstCardNumber());
        var firstCardBalanceResult = firstCardBalanceStart - amount;
        var secondCardBalanceResult = secondCardBalanceStart + amount;

        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    @DisplayName("Should not transfer money if the amount is more on the balance")
    public void shouldNotTransferMoneyIfAmountMoreBalance() {
        var dashboardPage = new DashboardPage();
        int amount = 20_000;

        var transferPage = firstCardButton();
        transferPage.makeTransfer(String.valueOf(amount), getSecondCardNumber());
        transferPage.showError("Сумма превышает допустимый лимит!");
    }
}
