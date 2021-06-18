package algonquin.cst2335.isla0059;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)

public class MainActivityTest {
@Rule
public ActivityTestRule<MainActivity>mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    /**
     * This test case find missing uppercase,lowercase and special character
     */

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));

        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());
        ViewInteraction materialButton = onView((withId(R.id.button)));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));


    }

    /**
     * This test case find missing uppercase
     */

    @Test
    public void testFindMissingUpperCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));

        appCompatEditText.perform(replaceText("password123#$*"));
        ViewInteraction materialButton = onView((withId(R.id.button)));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * This test case find missing lowercase
     */
    @Test
    public void testFindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));

        appCompatEditText.perform(replaceText("PASSWORD123#$*"));
        ViewInteraction materialButton = onView((withId(R.id.button)));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * This test case find missing digit
     */
    @Test
    public void testFindMissingDigitCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));

        appCompatEditText.perform(replaceText("passwordPASSWORD#$*"));
        ViewInteraction materialButton = onView((withId(R.id.button)));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * This test case find missing special character
     */
    @Test
    public void testFindMissingSpecialCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));

        appCompatEditText.perform(replaceText("passwordPASSWORD123"));
        ViewInteraction materialButton = onView((withId(R.id.button)));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * This test case checks all the requirements
     */
    @Test
    public void testFindAllRequirementCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));

        appCompatEditText.perform(replaceText("passwordPASSWORD123#$*"));
        ViewInteraction materialButton = onView((withId(R.id.button)));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("Your password meets the requirements")));
    }

}