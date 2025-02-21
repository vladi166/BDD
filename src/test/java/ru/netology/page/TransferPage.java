package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement titleText = $(byText("Пополнение карты"));
    private SelenideElement sum = $("[data-test-id=amount] [class='input__control']");
    private SelenideElement fromCard = $("[data-test-id=from] [class='input__control']");
    private SelenideElement replenishButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel'] [class='button__content']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification'] _notification__content");

    public TransferPage() {
        titleText.shouldBe(visible);
    }

    public void makeTransfer(String amountToTransfer, UserInfo.CardInfo cardInfo){
        sum.setValue(amountToTransfer);
        fromCard.setValue(cardInfo.getCardNumber());
        replenishButton.click();
    }

    public void showError(String expectedText) {
        errorMessage.shouldHave(text("Сумма превышает допустимый лимит!"), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
