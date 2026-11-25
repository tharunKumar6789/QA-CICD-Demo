
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

public class TaskPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By pageHeading = By.id("page-heading");
    private By taskInput = By.id("task-input");
    private By addTaskButton = By.id("add-task-button");
    private By errorMessage = By.id("error-message");
    private By taskItems = By.cssSelector("li.task-item");

    // Constructor
    public TaskPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }

    // Open application
    public void openApplication() {

        driver.get("http://127.0.0.1:5000");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageHeading));
    }

    // Get page heading
    public String getHeading() {

        return driver.findElement(pageHeading).getText();
    }

    // Add task and submit
    public void addTask(String taskName) {

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(taskInput));

        input.clear();
        input.sendKeys(taskName);

        driver.findElement(addTaskButton).click();
    }

    // Enter task without submitting
    public void enterTaskWithoutSubmitting(String taskName) {

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(taskInput));

        input.clear();
        input.sendKeys(taskName);
    }

    // Get total task count
    public int getTaskCount() {

        return driver.findElements(taskItems).size();
    }

    // Check whether task is present
    public boolean isTaskPresent(String taskName) {

        By taskLocator = By.xpath(
                "//span[contains(@class,'task-text') and normalize-space()=\""
                + taskName + "\"]");

        try {

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(taskLocator));

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    // Check validation message
    public boolean isErrorDisplayed() {

        try {

            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorMessage));

            return !error.getText().trim().isEmpty();

        } catch (TimeoutException e) {

            return false;
        }
    }

    // Delete task
    public void deleteTask(String taskName) {

        By deleteButton = By.xpath(
                "//li[contains(@class,'task-item')]"
                + "[.//span[contains(@class,'task-text') and normalize-space()=\""
                + taskName + "\"]]"
                + "//button[contains(@class,'delete-button')]");

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(deleteButton));

        button.click();
    }

    // Verify task deleted
    public boolean isTaskDeleted(String taskName) {

        By taskLocator = By.xpath(
                "//span[contains(@class,'task-text') and normalize-space()=\""
                + taskName + "\"]");

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(taskLocator));

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    // Get maximum input length
    public int getInputMaxLength() {

        String maxLength = driver.findElement(taskInput)
                .getDomAttribute("maxlength");

        return Integer.parseInt(maxLength);
    }

    // Get current input value length
    public int getInputValueLength() {

        String value = driver.findElement(taskInput)
                .getDomProperty("value");

        return value.length();
    }
}
