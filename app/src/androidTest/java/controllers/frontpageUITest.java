package controllers;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.besammen.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class frontpageUITest {

    /*@Rule
    public ActivityScenarioRule<frontPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(frontPage.class);*/
    @Rule
    public IntentsTestRule<frontPage> activityRule = new IntentsTestRule<>(frontPage.class);



    @Test
    public void frontPageRegisterTest() {
        onView(withId(R.id.registerBtn)).perform(click());
        intended(hasComponent(signUp.class.getName()));

    }
    @Test
    public void frontPageLoginTest(){
        onView(withId(R.id.logInBtn)).perform(click());
        intended(hasComponent(logIn.class.getName()));
    }



}
