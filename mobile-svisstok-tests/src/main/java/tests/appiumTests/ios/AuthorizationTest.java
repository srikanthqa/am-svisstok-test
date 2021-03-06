package tests.appiumTests.ios;

import helpers.GenerateRandomString;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import runner.Devices;
import tests.page.SettingsPage;
import tests.page.exceptions.XmlParametersException;

import com.ios.AppiumDriver;
import com.mobile.driver.wait.Sleeper;

public class AuthorizationTest extends NonAutorizationBaseTest {

	private final String VALUE_INPUT = "1234567890";

	private final String CHARACTERS_INPUT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	@Test(priority = 1, enabled = true)
	public void checkLoginFieldDigits() {
		Sleeper.SYSTEM_SLEEPER.sleep(10000);
		main.checkPage();
		main.inputLoginTextfield(VALUE_INPUT);
		Assert.assertEquals(main.getLoginFieldText(), VALUE_INPUT);
	}

	@Test(priority = 2, enabled = true)
	public void checkLoginFieldLetters() {
		main.checkPage();
		main.inputLoginTextfield(CHARACTERS_INPUT);
		Assert.assertEquals(main.getLoginFieldText(), CHARACTERS_INPUT);
		main.inputLoginTextfield(CHARACTERS_INPUT.toLowerCase());
		Assert.assertEquals(main.getLoginFieldText(),
				CHARACTERS_INPUT.toLowerCase());
	}
	
	@Test(priority = 3, enabled = true)
	public void checkLoginWithIncorrectCredentionals() {
		main.checkPage();
		String password = GenerateRandomString.generateString();
		main.inputLoginTextfield(INCORRECT_USER_NAME);
		main.inputPasswordTextfield(password);
		main.clickLogin();
		Assert.assertTrue(main.isErrorMessageAppears());
	}

	@Test(priority = 4, enabled = true)
	public void simpleLogin() {
		main.checkPage();
		call = main.simpleLogin(USER_NAME, USER_PASSWORD, false, false);
		checkPage();
		Assert.assertTrue(call.isStatusAvailable(), "CallPage doesn't open");
	}

	@Test(priority = 5, description = "Check save password functionality", enabled = true)
	public void loginWithSavePasswordFlag() throws Exception {
		AppiumDriver.class.cast(driver).quit();
		initPages();
		main.checkPage();
		call = main.simpleLogin(USER_NAME, USER_PASSWORD, true, false);
		checkPage();
		AppiumDriver.class.cast(driver).quit();
		initPages();
		Sleeper.SYSTEM_SLEEPER.sleep(10000);
		main.checkPage();
		Assert.assertTrue(main.isSavePasswordCorrect(),
				"Save password flag doesn't work correctly.Login or Password field are empty");
	}

	@Test(priority = 6, description = "Check auto login functionality", enabled = true)
	public void autoLogin() throws Exception {
		//AppiumDriver.class.cast(driver).quit();
		initPages();
		main.checkPage();
		call = main.simpleLogin(USER_NAME, USER_PASSWORD, true, true);
		checkPage();
		AppiumDriver.class.cast(driver).quit();
		initPages();
		//call = main.simpleLogin(USER_NAME, USER_PASSWORD, true, true);
		Sleeper.SYSTEM_SLEEPER.sleep(5000);
		Assert.assertTrue(call.isStatusAvailable(), "Status not availablee");
		SettingsPage settings = call.navigateToSettingsTab();
		settings.setAutoLogin(false);
		Assert.assertTrue(settings.isAutoLoginFlagDisable());
	}
	
	private void checkPage(){
		switch (Devices.valueOf(DEVICE)) {
		case IPHONE:
			call.checkPage();
			break;
		case IOS7:
			Sleeper.SYSTEM_SLEEPER.sleep(5000);
			if(call.isAccessContacts())
			  call.clickOk();
			checkUpdateAlert();
			call.checkPage();
			break;
			default:
				throw new XmlParametersException("Invalid device");
		}
	}
	private void checkUpdateAlert(){
		if(call.isAlertUpdate())
			call.clickCancel();
	}
	
	@AfterClass(alwaysRun=true)
	public void quit() {
		((AppiumDriver) driver).quit();
	}
}
