package com.salesforce.dev.pages;

import com.salesforce.dev.framework.DriverManager;
import com.salesforce.dev.pages.Home.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Jimmy Vargas on 6/5/2015.
 */
public class TopHeader {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "userNavLabel")
    WebElement userMenu;

    @FindBy(id = "phHeaderLogoImage")
    WebElement salesforceLogo;

    @FindBy(css = "a.menuButtonMenuLink.firstMenuItem")
    WebElement userMyProfileMenu;

    @FindBy(xpath = "//a[contains(@href, '/secur/logout.jsp')]")
    WebElement logoutMenuOption;

    public TopHeader(WebDriver driver){
        this.driver = driver;
        this.wait = DriverManager.getInstance().getWait();
        PageFactory.initElements(this.driver,this);
        this.wait.until(ExpectedConditions.visibilityOf(salesforceLogo));
    }

    public void clickUserNameMenu(){
        try{
            wait.until(ExpectedConditions.visibilityOf(userMenu));
            this.userMenu.click();
        }
        catch (WebDriverException e){
            throw new WebDriverException(e);
        }
    }
    public String getUserName(){
        String userLogged = "";
        try{
            wait.until(ExpectedConditions.visibilityOf(userMenu));
            userLogged = this.userMenu.getText();
        }
        catch (WebDriverException e){
            throw new WebDriverException(e);
        }
        return userLogged;
    }

    public boolean isUserMenuPresent(){
        return !getUserName().equals("");
    }

    public boolean isLoggedUser(String account){
        return getUserName().equals(account);
    }

    public LoginPage logout(){
        this.clickUserNameMenu();
        logoutMenuOption.click();

        return new LoginPage(this.driver);
    }

    public LoginPage clickLogoutOption(){
        try{
            wait.until(ExpectedConditions.visibilityOf(logoutMenuOption));
            logoutMenuOption.click();
        }
        catch (WebDriverException e){
            throw new WebDriverException(e);
        }
        return new LoginPage(driver);
    }

    public boolean checkIfCookieIsPresent() {
        if(this.driver.manage().getCookieNamed("com.salesforce.LocaleInfo").getDomain().equals(".salesforce.com")) {
            return true;
        }
        else {
            return false;
        }
    }
}
