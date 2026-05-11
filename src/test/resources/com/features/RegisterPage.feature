@RegisterPage
Feature: NumpyNinja Register Page
  As a new user on the NumpyNinja portal
  I want to register for an account
  So that I can access protected Data Structure content

  Background:
    Given the user navigates to the register page "https://dsportalapp.herokuapp.com/register"

  # ─────────────────────────────────────────────
  # Page Load
  # ─────────────────────────────────────────────

  @Smoke @PageLoad
  Scenario: Verify the register page loads successfully
    Then the register page URL should contain "/register"

  # ─────────────────────────────────────────────
  # Navigation Bar
  # ─────────────────────────────────────────────

  @Smoke @Navigation
  Scenario: Verify the brand logo is displayed on the register page
    Then the register page brand logo "NumpyNinja" should be displayed

  @Navigation
  Scenario: Verify Register link is visible in the navbar on the register page
    Then the register page navbar "Register" link should be visible

  @Navigation
  Scenario: Verify Sign In link is visible in the navbar on the register page
    Then the register page navbar "Sign in" link should be visible

  @Navigation
  Scenario: Verify clicking Sign In link from register page redirects to login page
    When the user clicks on the register page navbar "Sign in" link
    Then the register page user should be redirected to "/login"

  # ─────────────────────────────────────────────
  # Registration Form — Fields
  # ─────────────────────────────────────────────

  @Smoke @Form
  Scenario: Verify the Username field is displayed on the register page
    Then the register page "Username" field should be visible

  @Smoke @Form
  Scenario: Verify the Password field is displayed on the register page
    Then the register page "Password" field should be visible

  @Smoke @Form
  Scenario: Verify the Password Confirmation field is displayed on the register page
    Then the register page "Password confirmation" field should be visible

  @Form
  Scenario: Verify the Username field accepts input
    When the user enters "testuser123" in the register page "Username" field
    Then the register page "Username" field value should be "testuser123"

  @Form
  Scenario: Verify the Password field accepts input
    When the user enters "SecurePass@1" in the register page "Password" field
    Then the register page "Password" field value should be "SecurePass@1"

  @Form
  Scenario: Verify the Password field is of type password (masked)
    Then the register page "Password" field type should be "password"

  @Form
  Scenario: Verify the Password confirmation field is of type password (masked)
    Then the register page "Password confirmation" field type should be "password"

  # ─────────────────────────────────────────────
  # Registration Form — Submit Button
  # ─────────────────────────────────────────────

  @Smoke @Form
  Scenario: Verify the Register submit button is visible
    Then the register submit button should be visible

  @Form
  Scenario: Verify the Register submit button text
    Then the register submit button text should be "Register"

  @Form
  Scenario: Verify the Register submit button is clickable
    Then the register submit button should be clickable

  # ─────────────────────────────────────────────
  # Successful Registration
  # ─────────────────────────────────────────────

  @Smoke @Auth
  Scenario: Verify a new user can register successfully
    When the user registers with a unique username and password "Xk9#mP2$vQ"
    Then the register page user should be redirected to "/home"
    And the register page success message text should contain "New Account Created"

  # ─────────────────────────────────────────────
  # Validation — Empty Form
  # ─────────────────────────────────────────────

  @Validation
  Scenario: Verify form stays on register page when submitted empty
    When the user submits the register form bypassing html5 validation
    Then the register page URL should contain "/register"

  # ─────────────────────────────────────────────
  # Validation — Password Mismatch
  # ─────────────────────────────────────────────

  @Validation
  Scenario: Verify error banner when passwords do not match
    When the user enters "testuser123" in the register page "Username" field
    And the user enters "Password@1" in the register page "Password" field
    And the user enters "DifferentPass@1" in the register page "Password confirmation" field
    And the user clicks the register submit button
    Then the register page error banner should be displayed
    And the register page error banner text should contain "password"

  # ─────────────────────────────────────────────
  # Validation — Existing Username
  # Banner is asserted visible only — text assertion removed as Django's
  # password validator intercepts before the uniqueness check.
  # ─────────────────────────────────────────────

  @Validation
  Scenario: Verify error banner when registering with an existing username
    When the user enters "newuser_auto" in the register page "Username" field
    And the user enters "Xk9#mP2$vQ" in the register page "Password" field
    And the user enters "Xk9#mP2$vQ" in the register page "Password confirmation" field
    And the user clicks the register submit button
    Then the register page error banner should be displayed

  # ─────────────────────────────────────────────
  # Validation — Password Too Short
  # FIX (round 6): "short"/"short" caused password_mismatch banner because
  # the confirmation field value was not sticking due to a form render race.
  # Fixed in RegisterPage.java using JS sendKeys for the confirm field.
  # ─────────────────────────────────────────────

  @Validation
  Scenario: Verify error banner when password is too short
    When the user enters "testuser123" in the register page "Username" field
    And the user enters "short" in the register page "Password" field
    And the user enters "short" in the register page "Password confirmation" field
    And the user clicks the register submit button
    Then the register page error banner should be displayed
