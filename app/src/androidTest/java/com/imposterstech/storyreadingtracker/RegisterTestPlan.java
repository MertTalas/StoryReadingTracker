package com.imposterstech.storyreadingtracker;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.imposterstech.storyreadingtracker.view.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;

public class RegisterTestPlan {
    @Rule
    public ActivityTestRule<RegisterActivity> activityActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test()
    public void checkRegisterPageElements(){
        RegisterPage.checkButton(RegisterPage.getImageDashboard());
        RegisterPage.checkButton(RegisterPage.getTextViewDashboardDesc());
        RegisterPage.checkButton(RegisterPage.getEditTextEmail());
        RegisterPage.checkButton(RegisterPage.getEditTextPassword());
        RegisterPage.checkButton(RegisterPage.getEditTextName());
        RegisterPage.checkButton(RegisterPage.getEditTextSurname());
        RegisterPage.checkButton(RegisterPage.getSpinnerAge());
        RegisterPage.checkButton(RegisterPage.getSpinnerGender());
        RegisterPage.checkButton(RegisterPage.getCheckboxPrivacy());
        RegisterPage.checkButton(RegisterPage.getButtonRegister());
        Util.logTest("Register Page is properly checked");
    }

    @Test()
    public void checkRegisterAction(){
        RegisterPage.setEditTextName(RegisterPage.getEditTextName());
        Espresso.closeSoftKeyboard();
        RegisterPage.setEditTextSurname(RegisterPage.getEditTextSurname());
        Espresso.closeSoftKeyboard();
        RegisterPage.setEditTextEmail(RegisterPage.getEditTextEmail());
        Espresso.closeSoftKeyboard();
        RegisterPage.setEditTextPassword(RegisterPage.getEditTextPassword());
        Espresso.closeSoftKeyboard();
        RegisterPage.setSpinnerAge(RegisterPage.getSpinnerAge());
        RegisterPage.setSpinnerGender(RegisterPage.getSpinnerGender());
        RegisterPage.clickCheckbox(RegisterPage.getCheckboxPrivacy());
        RegisterPage.clickButton(RegisterPage.getButtonRegister());
        Util.logTest("Register successfully");
    }
}
