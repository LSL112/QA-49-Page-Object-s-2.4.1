package ru.netology.test;


import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    int beginBalance1;
    int beginBalance2;
    int endBalance1;
    int endBalance2;
    int sum;
    DashboardPage dashboardPage;

    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataGenerator.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        beginBalance1 = dashboardPage.getBalance(dashboardPage.card1);
        beginBalance2 = dashboardPage.getBalance(dashboardPage.card2);
    }

    @Test
    @DisplayName("Перевод денег сo второй карты на первую")
    void shouldTransferMoneyFromSecondToFirstCard() {
        sum = 100;
        var topUpPage = dashboardPage.clickTopUp(dashboardPage.card1);
        var cardNum = DataGenerator.getSecondCard().getNumber();
        var dashboardPage2 = topUpPage.successfulTopUp(Integer.toString(sum), cardNum);
        endBalance1 = dashboardPage2.getBalance(dashboardPage2.card1);
        endBalance2 = dashboardPage2.getBalance(dashboardPage2.card2);
        assertEquals(beginBalance1 + sum, endBalance1);
        assertEquals(beginBalance2 - sum, endBalance2);
    }

    @Test
    @DisplayName("Перевод денег с первой карты на вторую")
    void shouldTransferMoneyFromFirstToSecondCard() {
        sum = 100;
        var topUpPage = dashboardPage.clickTopUp(dashboardPage.card2);
        var cardNum = DataGenerator.getFirstCard().getNumber();
        var dashboardPage2 = topUpPage.successfulTopUp(Integer.toString(sum), cardNum);
        endBalance1 = dashboardPage2.getBalance(dashboardPage2.card1);
        endBalance2 = dashboardPage2.getBalance(dashboardPage2.card2);
        assertEquals(beginBalance1 - sum, endBalance1);
        assertEquals(beginBalance2 + sum, endBalance2);
    }

    @Test
    @DisplayName("Не должен переводить больше, чем есть на карте")
    void shouldNotTransferMoreThanAvailable() {
        sum = beginBalance1 + 100;
        var topUpPage = dashboardPage.clickTopUp(dashboardPage.card2);
        var cardNum = DataGenerator.getFirstCard().getNumber();
        topUpPage.unsuccessfulTopUp(Integer.toString(sum), cardNum);
    }

}
//  @Test
//void shouldTransferMoneyBetweenOwnCardsV1() {
//    open("http://localhost:9999");
//    var loginPage = new LoginPageV1();
//    var loginPage = open("http://localhost:9999", LoginPageV1.class);
//     var authInfo = DataGenerator.getAuthInfo();
//     var verificationPage = loginPage.validLogin(authInfo);
//     var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
//     verificationPage.validVerify(verificationCode);
// }

//   @Test
//   void shouldTransferMoneyBetweenOwnCards() {
//    open("http://localhost:9999");

//      var loginPage = new LoginPage();
//      var loginPage = open("http://localhost:9999", LoginPageV2.class);
//   var authInfo = DataGenerator.getAuthInfo();
//      var verificationPage = loginPage.validLogin(authInfo);
//      var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
//      verificationPage.validVerify(verificationCode);
//      new LoginPage()
//               .validLogin(authInfo)
//               .validVerify(DataGenerator.getVerificationCodeFor(authInfo));


//  @Test
//  void shouldTransferMoneyBetweenOwnCardsV3() {
//      var loginPage = open("http://localhost:9999", LoginPageV3.class);
//      var authInfo = DataGenerator.getAuthInfo();
//      var verificationPage = loginPage.validLogin(authInfo);
//      var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
//      verificationPage.validVerify(verificationCode);
//  }

