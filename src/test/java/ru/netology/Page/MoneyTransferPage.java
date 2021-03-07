package ru.netology.Page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {

    private SelenideElement db = $("[data-test-id = dashboard]");
    private SelenideElement moneyAmount = $("[data-test-id = amount] input");
    private SelenideElement fromCard = $("[data-test-id = from] input");
    private SelenideElement button = $("[data-test-id = action-transfer]");


    public MoneyTransferPage() {
        db.shouldBe(Condition.visible);
    }


    public DashBoard getTransfer(String amount, String cardNumber) {
        moneyAmount.setValue(amount);
        fromCard.setValue(cardNumber);
        button.click();
        return new DashBoard();
    }

}
