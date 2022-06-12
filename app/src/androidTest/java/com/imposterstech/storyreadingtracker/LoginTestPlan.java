package com.imposterstech.storyreadingtracker;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.imposterstech.storyreadingtracker.view.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

public class LoginTestPlan {
    @Rule
    public ActivityTestRule<LoginActivity> activityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test()
    public void checkLoginPageElements(){
        LoginPage.checkButton(LoginPage.getImageDashboard());
        LoginPage.checkButton(LoginPage.getTextViewDashboardDesc());
        LoginPage.checkButton(LoginPage.getEditTextEmail());
        LoginPage.checkButton(LoginPage.getEditTextPassword());
        LoginPage.checkButton(LoginPage.getImageShowPassword());
        LoginPage.checkButton(LoginPage.getTextViewForgotPassword());
        LoginPage.checkButton(LoginPage.getButtonLogin());
        LoginPage.checkButton(LoginPage.getButtonRegister());
        Util.logTest("Login Page is properly checked");
    }

    @Test()
    public void checkLoginAction(){
        LoginPage.setEditTextEmail(LoginPage.getEditTextEmail());
        Espresso.closeSoftKeyboard();
        LoginPage.setEditTextPassword(LoginPage.getEditTextPassword());
        Espresso.closeSoftKeyboard();
        LoginPage.clickButton(LoginPage.getButtonLogin());
        Util.logTest("Login successfully");
    }
}
