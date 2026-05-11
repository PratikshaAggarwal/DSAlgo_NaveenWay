package stepDefinitions;

import org.junit.Assert;

import com.factory.DriverFactory;
import com.pages.RegisterPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterPageSteps {

    private RegisterPage rp = new RegisterPage(DriverFactory.getDriver());

    // ─── Navigation ───────────────────────────────────────────────

    @Given("the user navigates to the register page {string}")
    public void the_user_navigates_to_the_register_page(String url) {
        DriverFactory.getDriver().get(url);
    }

    // ─── URL & Redirect ───────────────────────────────────────────

    @Then("the register page URL should contain {string}")
    public void the_register_page_url_should_contain(String expectedURL) {
        String actualURL = rp.getCurrentUrl();
        System.out.println("The current register page URL is: " + actualURL);
        Assert.assertTrue(actualURL.contains(expectedURL));
    }

    @Then("the register page user should be redirected to {string}")
    public void the_register_page_user_should_be_redirected_to(String expectedURL) {
        String actualURL = rp.getCurrentUrl();
        System.out.println("The redirected URL is: " + actualURL);
        Assert.assertTrue(actualURL.contains(expectedURL));
    }

    // ─── Navbar ───────────────────────────────────────────────────

    @Then("the register page brand logo {string} should be displayed")
    public void the_register_page_brand_logo_should_be_displayed(String expectedText) {
        boolean flag = rp.isBrandLogoVisible();
        System.out.println("Brand logo visibility: " + flag);
        Assert.assertTrue(flag);
        String actualText = rp.getBrandLogoText();
        System.out.println("Brand logo text: " + actualText);
        Assert.assertTrue(actualText.contains(expectedText));
    }

    @Then("the register page navbar {string} link should be visible")
    public void the_register_page_navbar_link_should_be_visible(String linkName) {
        boolean flag;
        if (linkName.equalsIgnoreCase("Register")) {
            flag = rp.isRegisterLinkVisible();
        } else if (linkName.equalsIgnoreCase("Sign in")) {
            flag = rp.isSignInLinkVisible();
        } else {
            throw new IllegalArgumentException("Navbar link not recognised: " + linkName);
        }
        System.out.println(linkName + " navbar link visibility: " + flag);
        Assert.assertTrue(flag);
    }

    @When("the user clicks on the register page navbar {string} link")
    public void the_user_clicks_on_the_register_page_navbar_link(String linkName) {
        if (linkName.equalsIgnoreCase("Sign in")) {
            rp.clickSignInLink();
        } else {
            System.out.println("Navbar link not recognized: " + linkName);
        }
    }

    // ─── Form Fields — Visibility ─────────────────────────────────

    @Then("the register page {string} field should be visible")
    public void the_register_page_field_should_be_visible(String fieldName) {
        boolean flag;
        switch (fieldName) {
            case "Username":              flag = rp.isUsernameFieldVisible();        break;
            case "Password":              flag = rp.isPasswordFieldVisible();        break;
            case "Password confirmation": flag = rp.isPasswordConfirmFieldVisible(); break;
            default: throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
        System.out.println(fieldName + " field visibility: " + flag);
        Assert.assertTrue(flag);
    }

    // ─── Form Fields — Type ───────────────────────────────────────

    @Then("the register page {string} field type should be {string}")
    public void the_register_page_field_type_should_be(String fieldName, String expectedType) {
        String actualType;
        switch (fieldName) {
            case "Password":              actualType = rp.getPasswordFieldType();        break;
            case "Password confirmation": actualType = rp.getPasswordConfirmFieldType(); break;
            default: throw new IllegalArgumentException("Type check not defined for: " + fieldName);
        }
        System.out.println(fieldName + " field type: " + actualType);
        Assert.assertEquals(expectedType, actualType);
    }

    // ─── Form Fields — Input ──────────────────────────────────────

    @When("the user enters {string} in the register page {string} field")
    public void the_user_enters_in_the_register_page_field(String value, String fieldName) {
        switch (fieldName) {
            case "Username":              rp.enterUsername(value);             break;
            case "Password":              rp.enterPassword(value);             break;
            case "Password confirmation": rp.enterPasswordConfirmation(value); break;
            default: throw new IllegalArgumentException("Input not defined for: " + fieldName);
        }
        System.out.println("Entered '" + value + "' in the " + fieldName + " field");
    }

    // ─── Form Fields — Value ──────────────────────────────────────

    @Then("the register page {string} field value should be {string}")
    public void the_register_page_field_value_should_be(String fieldName, String expectedValue) {
        String actualValue;
        switch (fieldName) {
            case "Username": actualValue = rp.getUsernameFieldValue(); break;
            case "Password": actualValue = rp.getPasswordFieldValue(); break;
            default: throw new IllegalArgumentException("getValue not defined for: " + fieldName);
        }
        System.out.println(fieldName + " field value: " + actualValue);
        Assert.assertEquals(expectedValue, actualValue);
    }

    // ─── Submit Button ────────────────────────────────────────────

    @Then("the register submit button should be visible")
    public void the_register_submit_button_should_be_visible() {
        boolean flag = rp.isRegisterSubmitButtonVisible();
        System.out.println("Register submit button visibility: " + flag);
        Assert.assertTrue(flag);
    }

    @Then("the register submit button text should be {string}")
    public void the_register_submit_button_text_should_be(String expectedText) {
        String actualText = rp.getRegisterSubmitButtonText();
        System.out.println("Register submit button text: " + actualText);
        Assert.assertTrue(actualText.contains(expectedText));
    }

    @Then("the register submit button should be clickable")
    public void the_register_submit_button_should_be_clickable() {
        boolean flag = rp.isRegisterSubmitButtonClickable();
        System.out.println("Register submit button clickable: " + flag);
        Assert.assertTrue(flag);
    }

    @When("the user clicks the register submit button")
    public void the_user_clicks_the_register_submit_button() {
        rp.clickRegisterSubmitButton();
        System.out.println("Clicked the register submit button");
    }

    // ─── Successful Registration ──────────────────────────────────

    @When("the user registers with a unique username and password {string}")
    public void the_user_registers_with_a_unique_username_and_password(String password) {
        String uniqueUsername = "usr" + System.currentTimeMillis();
        System.out.println("Registering with unique username: " + uniqueUsername);
        rp.enterUsername(uniqueUsername);
        rp.enterPassword(password);
        rp.enterPasswordConfirmation(password);
        rp.clickRegisterSubmitButton();
    }

    // ─── Success Banner ───────────────────────────────────────────

    @Then("the register page success message text should contain {string}")
    public void the_register_page_success_message_text_should_contain(String expectedText) {
        String actualText = rp.getSuccessBannerText();
        System.out.println("Success banner text: " + actualText);
        Assert.assertTrue(actualText.contains(expectedText));
    }

    // ─── Validation — Empty Form (JS submit) ─────────────────────

    @When("the user submits the register form bypassing html5 validation")
    public void the_user_submits_the_register_form_bypassing_html5_validation() {
        rp.submitFormViaJS();
        System.out.println("Submitted empty form via JS bypassing HTML5 validation");
    }

    // ─── Validation — Error Banner ────────────────────────────────

    @Then("the register page error banner should be displayed")
    public void the_register_page_error_banner_should_be_displayed() {
        boolean flag = rp.isErrorBannerVisible();
        System.out.println("Error banner visibility: " + flag);
        Assert.assertTrue(flag);
    }

    @Then("the register page error banner text should contain {string}")
    public void the_register_page_error_banner_text_should_contain(String expectedText) {
        String actualText = rp.getErrorBannerText();
        System.out.println("Error banner text: " + actualText);
        Assert.assertTrue(actualText.contains(expectedText.toLowerCase()));
    }
}
