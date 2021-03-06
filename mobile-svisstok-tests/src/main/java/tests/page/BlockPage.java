package tests.page;

import tests.page.ios.BasePage;

import com.mobile.driver.nativedriver.NativeDriver;

public abstract class BlockPage extends BasePage{

	public BlockPage(NativeDriver driver) { 
		this.driver = driver;
	}

	@Override
	public void checkPage() {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void searchContacts(String text);
	
	public abstract void clickSearchResult(String name);
	
	public abstract void clickEditContacts();
	
	public abstract void clickDeletefromList();
	
	public abstract void clickDelete();
	
	public abstract boolean isContactStatusBlockAppears(String message);
	
	public abstract void clickCall() ;
	   
}
