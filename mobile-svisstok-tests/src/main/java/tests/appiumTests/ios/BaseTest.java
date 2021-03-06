package tests.appiumTests.ios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import runner.DeviceConfig;
import runner.Devices;
import tests.page.BlockPage;
import tests.page.CallPage;
import tests.page.CardContactsPage;
import tests.page.FavoritePage;
import tests.page.HistoryPage;
import tests.page.LoginPage;
import tests.page.SavedContactsPage;
import tests.page.SettingsPage;
import tests.page.android.CardContactsPageAndroid;
import tests.page.android.LoginPageAndroid;
import tests.page.exceptions.XmlParametersException;
import tests.page.ios.CardContactsPageIos;
import tests.page.ios.LoginPageIos;
import utils.ApplicationStorage;

import com.ios.AppiumDriver;
import com.mobile.driver.nativedriver.NativeDriver;
import com.mobile.driver.page.PageFactory;
import com.mobile.driver.wait.Sleeper;

import driver.IosDriverWrapper;

/**
 * @author aleksei_mordas
 * 
 */
public class BaseTest {

	protected static final String DELIMETER = ":";

	protected static final String HOST = DeviceConfig.getHost();

	protected static final String PORT = DeviceConfig.getPort();

	protected static final String DEVICE = DeviceConfig.getDevice();
	
	protected static final String USER_NAME_SEARCH = "sipnet";

	protected static final String USER_NAME = ApplicationStorage.getDefaultUsername();;//"skustov4";// //"sip:skustov2@sipnet.ru";//"7812009957@217.195.69.250";
															// 7789 pas 1246

	protected static final String USER_PASSWORD = ApplicationStorage.getDefaultPassword();//vstarshinin psw: 6Terminator6// //"zzzzzz";//"JNcW5qTBaRvy";

	protected static final String ABONENT_NAME = ApplicationStorage.getDefaultPassword();
	
	protected static final String PHONE_NUMBER = ApplicationStorage
			.getCallerNumberYourself();
	
	protected static final String INCORRECT_USER_NAME = "7812001245@211.195.68.250";

	protected static final String INCORRECT_PASSWORD = "70mNZcEy05G123";

	protected NativeDriver driver;

	protected LoginPage main;

	protected CallPage call;

	protected CardContactsPage cardContacts;

	protected SettingsPage setting;
	
	protected BlockPage block;
	
	protected FavoritePage favorite;
	
	protected SavedContactsPage savedContacts;
	
	protected HistoryPage history;

	@BeforeClass(description = "Init and check page")
	public void initPages() throws Exception {
		switch (Devices.valueOf(DEVICE)) {
		case IPHONE:
			driver = IosDriverWrapper.getIos(HOST, PORT);
			driver.setDriverType(DEVICE);
			main = PageFactory.initElements(driver, LoginPageIos.class);
			call = main.simpleLogin(USER_NAME, USER_PASSWORD, false, false);
			Sleeper.SYSTEM_SLEEPER.sleep(2000);
			checkUpdateAlert();
			cardContacts = PageFactory.initElements(driver, CardContactsPageIos.class);
			Sleeper.SYSTEM_SLEEPER.sleep(5000);
			break;
		case IOS7:
			driver = IosDriverWrapper.getIos(HOST, PORT);
			driver.setDriverType(DEVICE);
			main = PageFactory.initElements(driver, LoginPageIos.class);
			call = main.simpleLogin(USER_NAME, USER_PASSWORD, false, false);
			cardContacts = PageFactory.initElements(driver, CardContactsPageIos.class);
			Sleeper.SYSTEM_SLEEPER.sleep(5000);
			if(call.isAccessContacts())
			  call.clickOk();
			checkUpdateAlert();
			break;
		case ANDROID:
			driver = IosDriverWrapper.getAndroid(HOST, PORT);
			Sleeper.SYSTEM_SLEEPER.sleep(10000);
			driver.setDriverType(DEVICE);
			main = PageFactory.initElements(driver, LoginPageAndroid.class);
			call = main.simpleLogin(USER_NAME, USER_PASSWORD, false, false);
			cardContacts = PageFactory.initElements(driver, CardContactsPageAndroid.class);
			call.checkPage();
			break;
		default:
			throw new XmlParametersException("Invalid device");
		}

	}

	public static boolean checkTimer(String element) {
		Pattern p = Pattern.compile("^(([0,1][0-9])|(2[0-3])):[0-5][0-9]$");
		Matcher m = p.matcher(element.replace(" ", ""));
		return m.matches();
	}

	@AfterClass
	public void tearDown() throws Exception {
		AppiumDriver.class.cast(driver).quit();
		
	}
	
	private void checkUpdateAlert(){
		if(call.isAlertUpdate())
			call.clickCancel();
	}
}
