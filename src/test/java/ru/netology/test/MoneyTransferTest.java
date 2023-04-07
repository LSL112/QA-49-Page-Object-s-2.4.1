package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;

public class MoneyTransferTest {
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataGenerator.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataGenerator.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

     @Test
    @DisplayName("Перевод денег сo второй карты на первую")
    void shouldTransferMoneyFromSecondToFirstCard() {
        var firstCardNumber = getFirstCardNumber();
        var secondCardNumber = getSecondCardNumber();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardNumber);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardNumber);
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var transferPage = dashboardPage.selectCardNumberTransfer(firstCardNumber);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), secondCardNumber);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardNumber);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardNumber);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    }


    @Test
    @DisplayName("Перевод суммы превышающий остаток на карте списания")
    void shouldGetErrorMessageIfAmountMoreBalance() {
        var firstCardNumber = getFirstCardNumber();
        var secondCardNumber = getSecondCardNumber();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardNumber);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardNumber);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCardNumberTransfer(firstCardNumber);
        transferPage.makeTransfer(String.valueOf(amount), secondCardNumber);
        transferPage.findErrorMessage("Выполнена попытка перевода суммы, превышающий остаток на карте списания");
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardNumber);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardNumber);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
    }


}
