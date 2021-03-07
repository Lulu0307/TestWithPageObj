package ru.netology.Page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.Data.UserData;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class VerificationPage {

    private SelenideElement codeField = $("[data-test-id = code] input");
    private SelenideElement button = $("[data-test-id = action-verify]");

    public DashBoard validVerify(UserData.VerificationCode code) {
        codeField.setValue(code.getCode());
        button.click();
        return new DashBoard();
    }

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
    }

    public static void openPersonalAccount() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = UserData.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = UserData.getCode(authInfo);
        verificationPage.validVerify(verificationCode);
    }
}
