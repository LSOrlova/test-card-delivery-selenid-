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
            form.$("[data-test-id='date'] input").setValue(meetingDay(5));
            form.$("[data-test-id='name'] input").setValue("Каша Смаслом");
            form.$("[data-test-id='phone'] input").setValue("+72745675622");
            form.$("[data-test-id='agreement']").click();
            form.$(".button__content").click();
            $("[data-test-id='notification']").waitUntil(visible, 15_000).shouldHave(text(meetingDay(5)));
        }
    }