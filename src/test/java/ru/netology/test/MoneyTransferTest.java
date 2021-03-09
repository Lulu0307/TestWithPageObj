package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardData;
import ru.netology.data.UserData;
import ru.netology.page.DashBoard;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    public DashBoard openPersonalAccount() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = UserData.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = UserData.getCode(authInfo);
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldPassVerification() {
        openPersonalAccount();
    }


    @Test
    void shouldSuccessTransferToFirstCard() {
        val dashboard = openPersonalAccount();
        val balance1 = dashboard.getFirstCardBalance();
        val balance2 = dashboard.getSecondCardBalance();
        val transferAmount = Math.abs(balance1 / 2);
        val transferPage = dashboard.getTransferToFirstCard();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.secondCard.getCardId());
        val newBalance1 = dashboard.getFirstCardBalance();
        val newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 + transferAmount, newBalance1);
        assertEquals(balance2 - transferAmount, newBalance2);
    }


    @Test
    void shouldSuccessTransferToSecondCard() {
        val dashboard = openPersonalAccount();
        val balance1 = dashboard.getFirstCardBalance();
        val balance2 = dashboard.getSecondCardBalance();
        val transferAmount = Math.abs(balance2 / 2);
        val transferPage = dashboard.getTransferToSecondCard();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.firstCard.getCardId());
        val newBalance1 = dashboard.getFirstCardBalance();
        val newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 - transferAmount, newBalance1);
        assertEquals(balance2 + transferAmount, newBalance2);
    }

    //проверка возможности уйти в минус
    @Test
    void shouldSuccessTransferWhenInsufficientFunds() {
        val dashboard = openPersonalAccount();
        val balance1 = dashboard.getFirstCardBalance();
        val balance2 = dashboard.getSecondCardBalance();
        val transferAmount = Math.abs(balance1 * 2);
        val transferPage = dashboard.getTransferToSecondCard();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.firstCard.getCardId());
        val newBalance1 = dashboard.getFirstCardBalance();
        val newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 - transferAmount, newBalance1);
        assertEquals(balance2 + transferAmount, newBalance2);
    }

    //проверка возможности перевода суммы равной 0
    @Test
    void shouldSuccessTransferWithZeroTransferAmount() {
        val dashboard = openPersonalAccount();
        val balance1 = dashboard.getFirstCardBalance();
        val balance2 = dashboard.getSecondCardBalance();
        val transferAmount = 0;
        val transferPage = dashboard.getTransferToSecondCard();
        transferPage.getTransfer(String.valueOf(transferAmount), CardData.firstCard.getCardId());
        val newBalance1 = dashboard.getFirstCardBalance();
        val newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1 - transferAmount, newBalance1);
        assertEquals(balance2 + transferAmount, newBalance2);
    }

    //проверка возможности перевода без ввода суммы
    @Test
    void shouldSuccessTransferWithEmptyTransferAmountField() {
        val dashboard = openPersonalAccount();
        val balance1 = dashboard.getFirstCardBalance();
        val balance2 = dashboard.getSecondCardBalance();
        val transferPage = dashboard.getTransferToSecondCard();
        transferPage.getTransfer("", CardData.firstCard.getCardId());
        val newBalance1 = dashboard.getFirstCardBalance();
        val newBalance2 = dashboard.getSecondCardBalance();
        assertEquals(balance1, newBalance1);
        assertEquals(balance2, newBalance2);
    }


    @Test
    void shouldNotSuccessTransferWithIncorrectCardId() {
        val dashboard = openPersonalAccount();
        val balance1 = dashboard.getFirstCardBalance();
        val transferPage = dashboard.getTransferToSecondCard();
        transferPage.getTransfer(String.valueOf(balance1), CardData.firstCard.getCardId().substring(2));
        transferPage.showErrorMessage();
    }

}
