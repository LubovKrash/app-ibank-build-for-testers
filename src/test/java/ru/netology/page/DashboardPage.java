package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceFirst = "баланс: ";
    private final String balanceSecond = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item dev");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public int getCardBalance(String maskedCardNumber) {
        var text = cards.findBy(Condition.text(maskedCardNumber)).getText();
        return  extractBalance(text);
    }

    public int getCardBalance(int index) {
        var text = cards.get(index).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferPage();
    }

    public void reloadDashboardPage() {
        reloadButton.click();
        heading.shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceFirst);
        var finish = text.indexOf(balanceSecond);
        var value = text.substring(start + balanceFirst.length(), finish);
        return Integer.parseInt(value);
    }

}
