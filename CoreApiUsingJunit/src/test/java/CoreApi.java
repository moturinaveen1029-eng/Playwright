import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class CoreApi {
	static Playwright playwright;
	static Browser browser;
	BrowserContext context;
	Page page;
	
	@BeforeAll
	static void launchBrowser() {
	playwright = Playwright.create();
	browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500));
	}
	
	@AfterAll
	static void CloseBrowser() {
		//playwright.close();
	}

	
	@BeforeEach
	public void createNewPageEverytime() {
	   context=browser.newContext();
	   page=context.newPage();
	   page.navigate("https://demo.playwright.dev/todomvc/#/");
	}
	
	@AfterEach
	public void closePageEverytime() {
		//context.close();
	}
	
	private void addTodo(String todo) {
        page.getByPlaceholder("What needs to be done?").fill(todo);
        page.keyboard().press("Enter");
    }
	
    @Test    
	void Test1() {
		assertTrue(page.title().contains("TodoMVC"));
	}
	
	@Test
	void Test2() {
		addTodo("Test 02");
		assertEquals(1, page.getByLabel("Toggle Todo").count());
	}
	
	@Test
	void Test3() {
		addTodo("Test 03-1");
		addTodo("Test 03-2");
		addTodo("Test 03-3");
		assertEquals(3, page.getByLabel("Toggle Todo").count());
	}
	   
	
	@Test
	void Test4() {
		addTodo("Test 04");
		page.getByRole(AriaRole.CHECKBOX).first().check();
		assertTrue(page.getByLabel("Toggle Todo").isChecked());
	}
	
	
	
	@Test
	void Test5() {
		 addTodo("A");
		 addTodo("B");
		 page.getByLabel("Mark all as complete").click();;
		 assertEquals(2,page.locator("[data-testid='todo-item'].completed").count());

	}
	
	@Test
	void Test6() {
		addTodo("Delete me");
		page.getByRole(AriaRole.CHECKBOX).first().check();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear completed")).click();
		assertEquals(0, page.getByLabel("Toggle Todo").count());
	}
	
	@Test
	void Test7() {
		addTodo("Active");
		addTodo("Done");
		page.getByRole(AriaRole.CHECKBOX).nth(1).check();
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Active")).click();
		assertEquals(1, page.getByLabel("Toggle Todo").count());
	}
	
	@Test
	void Test8() {
		addTodo("Task");
		page.getByRole(AriaRole.CHECKBOX).first().check();
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Completed")).click();
		assertEquals(1, page.getByLabel("Toggle Todo").count());

	}
	
	@Test
	void Test9() {
		addTodo("Edit me");
		page.getByTestId("todo-title").first().dblclick();
		page.getByLabel("Edit").first().fill("Edited!");
		page.keyboard().press("Enter");
		assertEquals("Edited!", page.getByTestId("todo-title").textContent());
	}
	
	@Test
	void Test10() {
		addTodo("Delete me");
		page.getByLabel("Toggle Todo").hover();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete")).click();
		assertEquals(0, page.getByLabel("Toggle Todo").count());
	}
	
	@Test
	void Test11() {	
	addTodo("Survive reload");
	page.reload();
	assertEquals(1, page.getByLabel("Toggle Todo").count());
	}
	
	
	
}
