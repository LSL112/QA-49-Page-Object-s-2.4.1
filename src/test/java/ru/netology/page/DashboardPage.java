package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = "р.";
    private final SelenideElement heading = $("[data-test-id = dashboard]");
    private final ElementsCollection cards = $$(".list__item div");


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataGenerator.CardNumber cardNumber) {
        var text= cards.findBy(text(cardNumber.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardNumberTransfer(DataGenerator.CardNumber cardNumber) {
        cards.findBy(text(cardNumber.getCardNumber().substring(15))).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}
