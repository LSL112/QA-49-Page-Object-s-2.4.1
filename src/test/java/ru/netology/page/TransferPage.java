package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TransferPage {
    private final SelenideElement transferButton = $("button[data-test-id = action-transfer]");
    private final SelenideElement amountInput = $("div[data-test-id = amount] input");
    private final SelenideElement fromInput = $("span[data-test-id = from] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("div[data-test-id = error-message]");

    public TransferPage () {
        transferHead.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataGenerator.CardNumber cardNumber) {
        makeTransfer(amountToTransfer, cardNumber);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataGenerator.CardNumber cardNumber ) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardNumber.getCardNumber());
        transferButton.click();
    }

    public void findErrorMessage (String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
