package ru.netology;

import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class CardDeliveryTest {
    String meetingDay(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));}

//        @BeforeAll {
//            WebDriverManager.chromedriver().setup();
//        }
        @BeforeEach
        void setUp () {
            open("http://localhost:9999/");
        }
        @Test
        void testHappyPath() {
            SelenideElement form = $("[action='/']");
            form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
            form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
            form.$("[data-test-id='date'] input").setValue(meetingDay(3));
            form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
            form.$("[data-test-id='phone'] input").setValue("+72745675622");
            form.$("[data-test-id='agreement']").click();
            form.$(".button__content").click();
            $("[data-test-id='notification']").waitUntil(visible, 15_000).shouldHave(text(meetingDay(3)));
        }
    @Test
    void testWithoutCity() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(3));
        form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
        form.$("[data-test-id='phone'] input").setValue("+72745675622");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void testWithoutName() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(3));
        form.$("[data-test-id='phone'] input").setValue("+72745675622");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void testWithoutPhone() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(3));
        form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void testWithoutCheckbox() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(3));
        form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
        form.$("[data-test-id='phone'] input").setValue("+72745675622");
        form.$(".button__content").click();
        form.$("[data-test-id='agreement'].input_invalid").shouldBe(visible).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    void testCityNotAvailable() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Бобруйск");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(3));
        form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
        form.$("[data-test-id='phone'] input").setValue("+72745675622");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='city'] .input__sub").shouldBe(visible).shouldHave(text("Доставка в выбранный город недоступна"));
    }
    @Test
    void testWithoutDate() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
        form.$("[data-test-id='phone'] input").setValue("+72745675622");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='date'] .input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }
    @Test
    void testDateLess3Days() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(2));
        form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
        form.$("[data-test-id='phone'] input").setValue("+72745675622");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='date'] .input__sub").shouldBe(visible).shouldHave(text("Заказ на выбранную дату невозможен"));
    }
    @Test
    void testInvalidName() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(3));
        form.$("[data-test-id='name'] input").setValue("Invalid name");
        form.$("[data-test-id='phone'] input").setValue("+72745675622");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
}