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

public class LogoutTestPlan {

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
    public void checkLogoutAction(){
        MainPage.getRecyclerSettingsItem(MainPage.getMainPageItems());
        MainPage.getRecyclerLogoutItem(MainPage.getMainPageItems());
        Util.logTest("Logout successfully");
    }
}
