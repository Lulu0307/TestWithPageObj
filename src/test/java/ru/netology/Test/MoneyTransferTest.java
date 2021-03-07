package ru.netology.Test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.Data.CardData;
import ru.netology.Page.DashBoard;
import ru.netology.Page.MoneyTransferPage;
import ru.netology.Page.VerificationPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {


    @Test
    void shouldPassVerification() {
        VerificationPage.openPersonalAccount();
    }

    //перевод со второй карты на первую
    @Test
    void shouldTransferMoneyV1() {
        VerificationPage.openPersonalAccount();
        val dashboard = new DashBoard();
        int balance1 = dashboard.getFirstCardBalance();
        int balance2 = dashboard.getSecondCardBalance();
        int transferAmount = Math.abs(balance1 / 2);
        $("[data-test-id = action-deposit]").click();
        val transferPage = new MoneyTransferPage();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.secondCard.getCardId());
        $(withText("Ваши карты")).shouldBe(Condition.visible);
        int newBalance1 = dashboard.getFirstCardBalance();
        int newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 + transferAmount, newBalance1);
        assertEquals(balance2 - transferAmount, newBalance2);
    }

    //перевод с первой карты
    @Test
    void shouldTransferMoneyV2() {
        VerificationPage.openPersonalAccount();
        val dashboard = new DashBoard();
        int balance1 = dashboard.getFirstCardBalance();
        int balance2 = dashboard.getSecondCardBalance();
        int transferAmount = Math.abs(balance2 / 2);
        $$("[data-test-id = action-deposit]").get(1).click();
        val transferPage = new MoneyTransferPage();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.firstCard.getCardId());
        $(withText("Ваши карты")).shouldBe(Condition.visible);
        int newBalance1 = dashboard.getFirstCardBalance();
        int newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 - transferAmount, newBalance1);
        assertEquals(balance2 + transferAmount, newBalance2);
    }

    //проверка возможности уйти в минус
    @Test
    void transferWhenInsufficientFunds() {
        VerificationPage.openPersonalAccount();
        val dashboard = new DashBoard();
        int balance1 = dashboard.getFirstCardBalance();
        int balance2 = dashboard.getSecondCardBalance();
        int transferAmount = Math.abs(balance1 * 2);
        $$("[data-test-id = action-deposit]").get(1).click();
        val transferPage = new MoneyTransferPage();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.firstCard.getCardId());
        $(withText("Ваши карты")).shouldBe(Condition.visible);
        int newBalance1 = dashboard.getFirstCardBalance();
        int newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 - transferAmount, newBalance1);
        assertEquals(balance2 + transferAmount, newBalance2);
    }

    //проверка возможности перевода суммы равной 0
    @Test
    void transferWithZeroTransferAmount() {
        VerificationPage.openPersonalAccount();
        val dashboard = new DashBoard();
        int balance1 = dashboard.getFirstCardBalance();
        int balance2 = dashboard.getSecondCardBalance();
        int transferAmount = 0;
        $$("[data-test-id = action-deposit]").get(1).click();
        val transferPage = new MoneyTransferPage();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.firstCard.getCardId());
        $(withText("Ваши карты")).shouldBe(Condition.visible);
        int newBalance1 = dashboard.getFirstCardBalance();
        int newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 - transferAmount, newBalance1);
        assertEquals(balance2 + transferAmount, newBalance2);
    }

    //проверка возможности перевода без ввода суммы
    @Test
    void transferWithEmptyTransferAmountField() {
        VerificationPage.openPersonalAccount();
        val dashboard = new DashBoard();
        int balance1 = dashboard.getFirstCardBalance();
        int balance2 = dashboard.getSecondCardBalance();
        $$("[data-test-id = action-deposit]").get(1).click();
        val transferPage = new MoneyTransferPage();
        transferPage.getTransfer("", CardData.firstCard.getCardId());
        $(withText("Ваши карты")).shouldBe(Condition.visible);
        int newBalance1 = dashboard.getFirstCardBalance();
        int newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1, newBalance1);
        assertEquals(balance2, newBalance2);
    }


    @Test
    void transferWithIncorrectCardId() {
        VerificationPage.openPersonalAccount();
        val dashboard = new DashBoard();
        int balance1 = dashboard.getFirstCardBalance();
        $$("[data-test-id = action-deposit]").get(1).click();
        val transferPage = new MoneyTransferPage();
        transferPage.getTransfer(String.valueOf(balance1), CardData.firstCard.getCardId().substring(2));
        $(withText("Ошибка")).shouldBe(Condition.visible);
    }

}
