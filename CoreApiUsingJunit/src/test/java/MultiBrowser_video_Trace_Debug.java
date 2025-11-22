import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

public class MultiBrowser_video_Trace_Debug {
	Playwright playwright;
	
	@BeforeEach
	public void start() {
		 playwright = Playwright.create();
	}
	
	@AfterEach
	public void destroy() {
		if(playwright !=null) playwright.close();
	}
	
	@Test
	public void fourscenarios() {
		String[] browserType= {"chromium","firefox","webkit"};
		
		for(String brow: browserType) {
			Browser browser=null;
			BrowserContext context=null;
			
			
			try {
				BrowserType broObj= switch(brow.toLowerCase().trim()) {
				case "firefox"->playwright.firefox();
				case "webkit"->playwright.webkit();
				default ->playwright.chromium();
				};
				
				browser=broObj.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500).setDevtools(true));
				context=browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos")).setRecordVideoSize(1280,720));
				context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
				
				Page page = context.newPage();
                page.navigate("https://google.com");
                page.fill("textarea[name=q]", "Playwright Java Day 3");
                page.pause();
                page.keyboard().press("Enter");
                //page.click("input[name=btnK] >> text=Google Search");
                System.out.println("SUCCESS in " + browserType);
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get("traces/" + browserType + "-passed.zip")));
				
			}catch(Exception e) {   
				System.err.println("FAILED in " + brow);
                e.printStackTrace();
                if (context != null) {
                    try {
                        context.tracing().stop(new Tracing.StopOptions()
                            .setPath(Paths.get("traces/" + brow + "-FAILED.zip")));
                    } catch (Exception te) {
                        System.err.println("Could not save failure trace: " + te.getMessage());
                    }
                }
			}finally {
                if (context != null) context.close();
                if (browser != null) browser.close();
            }
			
			
		}
	}

}
