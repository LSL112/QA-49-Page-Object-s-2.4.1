package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class TopUpPage {
    private SelenideElement sumField = $("div[data-test-id = amount] input");
    private SelenideElement accountField = $("span[data-test-id = from] input");
    private SelenideElement topUpButton = $("button[data-test-id = action-transfer]");
    private SelenideElement errorNotification = $("div[data-test-id = error-notification]");

    public DashboardPage successfulTopUp(String sum, String cardNum) {
        sumField.sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        sumField.setValue(sum);
        accountField.sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        accountField.setValue(cardNum);
        sleep(5000);
        topUpButton.click();
        return new DashboardPage();
    }

    public void unsuccessfulTopUp(String sum, String cardNum) {
        sumField.sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        sumField.setValue(sum);
        errorNotification.shouldBe(visible);
    }
}
