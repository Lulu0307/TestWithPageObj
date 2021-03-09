package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserData;

import static com.codeborne.selenide.Selenide.$;


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

}
