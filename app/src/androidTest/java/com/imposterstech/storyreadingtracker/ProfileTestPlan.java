package com.imposterstech.storyreadingtracker;

import static androidx.test.espresso.action.ViewActions.clearText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.imposterstech.storyreadingtracker.view.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfileTestPlan {

    @Rule
    public ActivityTestRule<LoginActivity> activityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void login() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.editText_loginpage_email))
                    .perform(clearText(), ViewActions.typeText("test@mail.com"), ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.editText_loginpage_password))
                    .perform(clearText(), ViewActions.typeText("test123"), ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.button_loginpage_login)).perform(ViewActions.click());
        } catch (Exception NoMatchingViewException) {

        }
    }

    @Test()
    public void checkProfilePageElements(){
        MainPage.getRecyclerProfileItem(MainPage.getMainPageItems());
        ProfilePage.checkButton(ProfilePage.getTextViewDashboard());
        ProfilePage.checkButton(ProfilePage.getImageAvatar());
        ProfilePage.checkButton(ProfilePage.getTextViewAvatar());
        ProfilePage.checkButton(ProfilePage.getTextViewEmail());
        ProfilePage.checkButton(ProfilePage.getTextViewName());
        ProfilePage.checkButton(ProfilePage.getEditTextName());
        ProfilePage.checkButton(ProfilePage.getTextViewSurname());
        ProfilePage.checkButton(ProfilePage.getEditTextSurname());
        ProfilePage.checkButton(ProfilePage.getTextViewAge());
        ProfilePage.checkButton(ProfilePage.getEditTextAge());
        ProfilePage.checkButton(ProfilePage.getSpinnerAge());
        ProfilePage.checkButton(ProfilePage.getTextViewGender());
        ProfilePage.checkButton(ProfilePage.getEditTextGender());
        ProfilePage.checkButton(ProfilePage.getSpinnerGender());
        ProfilePage.checkButton(ProfilePage.getUpdateButton());
        Util.logTest("Profile Page is properly checked");
    }

    @Test()
    public void checkProfileUpdateAction(){
        MainPage.getRecyclerProfileItem(MainPage.getMainPageItems());
        ProfilePage.setEditTextName(ProfilePage.getEditTextName());
        Espresso.closeSoftKeyboard();
        ProfilePage.clickButton(ProfilePage.getUpdateButton());
        Util.logTest("Profile updated successfully");
    }
}
