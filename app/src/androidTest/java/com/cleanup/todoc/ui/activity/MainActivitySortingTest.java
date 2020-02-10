package com.cleanup.todoc.ui.activity;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.cleanup.todoc.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivitySortingTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // TODO : method (new class) for DELETE fonctionnality

    @Test
    public void mainActivitySortingTest() {

        createTask("tache 1","Projet Tartampion");
        createTask("tache 2","Projet Lucidia");
        createTask("tache 3","Projet Circus");

        // FILTER "Z -> A"
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Z -> A"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 3"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("tache 3")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("tache 2")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 1"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("tache 1")));

        // FILTER "Les plus anciens d’abord"
        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Les plus anciens d’abord"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 1"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("tache 1")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("tache 2")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 3"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("tache 3")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 3"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("tache 3")));

        // FILTER "Les plus récents d’abord"
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.title), withText("Les plus récents d’abord"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 3"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("tache 3")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("tache 2")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.lbl_task_name), withText("tache 1"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView10.check(matches(withText("tache 1")));
    }

    private void createTask(String nomDeLaTache, String project) {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.txt_task_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText(nomDeLaTache), closeSoftKeyboard());

        // TODO : error ici

        /*onData(allOf(is(instanceOf(String.class)), is(project)))
                .perform(click());
        onView(withId(R.id.project_spinner))
                .check(matches(withSpinnerText(containsString(project))));
        */
        onView(ViewMatchers.withId(R.id.project_spinner)).perform(click());
        // onData(allOf(is(instanceOf(String.class)), is(project))).perform(click());
        // Espresso.onData(allOf(is(instanceOf(Integer.class)))).atPosition(1).perform(ViewActions.click());

        // onData(allOf(is(instanceOf(Integer.class)))).atPosition(1).perform(click());

        /*onData(allOf(is(instanceOf(Integer.class)),
                is(1))).inRoot(isPlatformPopup()).perform(click());*/

        // onData(allOf(is(instanceOf(Integer.class)), is(1))).perform(click());

        /*DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());*/

        // onData(allOf(is(instanceOf(Integer.class)))).atPosition(project).perform(click());

        /*Espresso.onView(ViewMatchers.withId(R.id.project_spinner)).perform(ViewActions.click());
        Espresso.onData(allOf(is(instanceOf(Integer.class)))).atPosition(project-1).perform(ViewActions.click());
        */

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("Ajouter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
