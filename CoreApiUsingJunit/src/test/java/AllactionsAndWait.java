
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class AllactionsAndWait extends CoreApi {
	
   @Test
   void AllActions() {
	/* Locator add= page.locator("input[class=new-todo]");
	 add.fill("Leatn playwright with java");
	 page.keyboard().press("Enter");
	 add.fill("I will delte this");
	 page.keyboard().press("Enter");
	 page.locator("//label[text()='I will delte this']/preceding-sibling::input").click();
	 page.navigate("https://rahulshettyacademy.com/AutomationPractice/");
	 page.locator("label:has-text('Radio2')").locator("input").click();
    page.selectOption("#dropdown-class-example", "Option3");
    page.getByPlaceholder("Enter Your Name").fill("Naveen");
        page.locator("#alertbtn").click();
        page.onceDialog(dialog -> dialog.accept());
	   
	   
	   page.navigate("https://the-internet.herokuapp.com/upload");
	   page.setInputFiles("#file-upload", Paths.get("pom.xml"));
	   page.locator("#file-submit").click(); 
	   
	   
	   page.navigate(
			    "https://samplelib.com/sample-mp4.html",
			    new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
			);

	  Download download= page.waitForDownload(()->{
		  page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Download")).first().click();
		  });
	   Path downloadPath = Paths.get("downloads", download.suggestedFilename());
	   download.saveAs(downloadPath);
	   System.out.println("Downloaded: " + downloadPath.toAbsolutePath());*/
	   
	   page.navigate("https://the-internet.herokuapp.com/dynamic_loading/2");
       page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Start")).click();
       page.waitForSelector("#finish", new Page.WaitForSelectorOptions()
    		    .setState(WaitForSelectorState.VISIBLE)
    		    .setTimeout(10000));
       assertThat(page.getByText("Hello World!").isVisible());

	  
	  
	  
}}
