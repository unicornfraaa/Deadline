package ru.netology.banklogin.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQLHelper;
import ru.netology.banklogin.page.LoginPage;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.banklogin.data.SQLHelper.cleanDatabase;

public class BankLoginTest {


    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfullLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var AuthInfo = DataHelper.getAuthInfoWithTestData();
        var VerificationPage = loginPage.validLogin(AuthInfo);
        VerificationPage.verifyVerificationPageVisiblity();
        var VerificationCode = SQLHelper.getVerificationCode();
        VerificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationLoginWithRandomUserWithoutAddingToBase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var AuthInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(AuthInfo);
        loginPage.verifyErrorNotificationVisiblity();
    }

    @Test
    @DisplayName("Should get error notification if login with random exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithRandomActiveUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var AuthInfo = DataHelper.getAuthInfoWithTestData();
        var VerificationPage = loginPage.validLogin(AuthInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var VerificationCode = DataHelper.generationRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotoficationVisiblity();
    }
}


