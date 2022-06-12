package com.imposterstech.storyreadingtracker;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.CustomMatcher;
import org.hamcrest.Matcher;

public class EspressoBaseTest {

    public static void clickButton(Integer resourceId){
        onView(withId(resourceId))
                .perform(click());
    }

    public static void checkButton(Integer resourceId){
        onView(withId(resourceId))
                .check(matches(isDisplayed()));
    }

    public static void setEditTextEmail(Integer resourceId){
        onView(withId(resourceId))
                .perform(clearText(), typeText("test@mail.com"));;
    }

    public static void setEditTextPassword(Integer resourceId){
        onView(withId(resourceId))
                .perform(clearText(), typeText("test123"));;
    }

    public static void setEditTextName(Integer resourceId){
        onView(withId(resourceId))
                .perform(clearText(), typeText("TestName"));;
    }

    public static void setEditTextSurname(Integer resourceId){
        onView(withId(resourceId))
                .perform(clearText(), typeText("TestSurname"));;
    }

    public static void setSpinnerAge(Integer resourceId){
        onView(withId(resourceId))
                .perform(click());
        onData(anything())
                .atPosition(1).perform(click());
    }

    public static void setSpinnerGender(Integer resourceId){
        onView(withId(resourceId))
                .perform(click());
        onData(anything())
                .atPosition(1).perform(click());
    }

    public static void clickCheckbox(Integer resourceId){
        onView(withId(resourceId))
                .perform(click());
    }

    public static void getRecyclerProfileItem(Integer resourceId){
        onView(withId(R.id.recyclerview_main_page)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Profile")), click()));
    }

    public static void getRecyclerSettingsItem(Integer resourceId){
        onView(withId(R.id.recyclerview_main_page)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Settings")), click()));
    }

    public static void getRecyclerLogoutItem(Integer resourceId){
        onView(withId(R.id.recyclerview_settings_page)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Logout")), click()));
    }

}
