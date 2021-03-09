package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DashBoard {
    private SelenideElement db = $("[data-test-id = dashboard]");
    private SelenideElement balance1 = $$(withText("баланс")).get(0);
    private SelenideElement balance2 = $$(withText("баланс")).get(1);
    private SelenideElement buttonTransferToFirstCard = $("[data-test-id = action-deposit]");
    private SelenideElement buttonTransferToSecondCard = $$("[data-test-id = action-deposit]").get(1);

    public DashBoard() {
        db.shouldBe(Condition.visible);
    }

    public int findBalance(String str) {
        String[] arr = str.split(",");
        String balance = arr[1].replaceAll("[^\\-?\\d]", "");
        return Integer.parseInt(balance);
    }

    public int getFirstCardBalance() {
        String text = balance1.text();
        return findBalance(text);
    }

    public int getSecondCardBalance() {
        String text = balance2.text();
        return findBalance(text);
    }

    public MoneyTransferPage getTransferToFirstCard() {
        buttonTransferToFirstCard.click();
        return new MoneyTransferPage();
    }

    public MoneyTransferPage getTransferToSecondCard() {
        buttonTransferToSecondCard.click();
        return new MoneyTransferPage();
    }
}
