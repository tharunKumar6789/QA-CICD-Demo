
package tests;

import org.openqa.selenium.WebDriver;

import pages.TaskPage;
import utils.DriverFactory;

public class TaskManagerTest {

    public static void main(String[] args) {

        WebDriver driver = DriverFactory.createDriver();

        try {

            TaskPage taskPage = new TaskPage(driver);

            taskPage.openApplication();

            // TC01: Launch application
            check(
                taskPage.getHeading().equals("Task Manager"),
                "TC01: Application launched successfully"
            );

            // TC02: Add task
            String taskName = "POM Selenium "
                    + System.currentTimeMillis();

            taskPage.addTask(taskName);

            check(
                taskPage.isTaskPresent(taskName),
                "TC02: Task added successfully"
            );

            // TC03: Delete task
            taskPage.deleteTask(taskName);

            check(
                taskPage.isTaskDeleted(taskName),
                "TC03: Task deleted successfully"
            );

            // TC04: Empty task validation
            int countBeforeEmpty = taskPage.getTaskCount();

            taskPage.addTask("");

            check(
                taskPage.isErrorDisplayed()
                && taskPage.getTaskCount() == countBeforeEmpty,
                "TC04: Empty task validation successful"
            );

            // TC05: Duplicate task validation
            String duplicateTask = "Duplicate Test "
                    + System.currentTimeMillis();

            taskPage.addTask(duplicateTask);

            check(
                taskPage.isTaskPresent(duplicateTask),
                "TC05: Initial task added"
            );

            int countBeforeDuplicate = taskPage.getTaskCount();

            taskPage.addTask(duplicateTask);

            check(
                taskPage.isErrorDisplayed()
                && taskPage.getTaskCount() == countBeforeDuplicate,
                "TC05: Duplicate task rejected successfully"
            );

            // TC06: Add multiple tasks
            String taskOne = "Multiple Task One "
                    + System.currentTimeMillis();

            String taskTwo = "Multiple Task Two "
                    + System.currentTimeMillis();

            taskPage.addTask(taskOne);
            taskPage.addTask(taskTwo);

            check(
                taskPage.isTaskPresent(taskOne)
                && taskPage.isTaskPresent(taskTwo),
                "TC06: Multiple tasks added successfully"
            );

            // TC07: Delete one task independently
            taskPage.deleteTask(taskOne);

            check(
                taskPage.isTaskDeleted(taskOne)
                && taskPage.isTaskPresent(taskTwo),
                "TC07: Independent task deletion successful"
            );

            // TC08: Maximum input length validation
            int maxLength = taskPage.getInputMaxLength();

            String longTask = "A".repeat(maxLength + 1);

            taskPage.enterTaskWithoutSubmitting(longTask);

            int actualLength = taskPage.getInputValueLength();

            System.out.println(
                "Maximum allowed length: " + maxLength);

            System.out.println(
                "Actual entered length: " + actualLength);

            check(
                actualLength == maxLength,
                "TC08: Maximum input length validation successful"
            );

            System.out.println("--------------------------------");
            System.out.println("ALL TEST CASES COMPLETED");
            System.out.println("--------------------------------");

        } catch (Exception e) {

            System.out.println("TEST EXECUTION FAILED");
            e.printStackTrace();

        } finally {

            DriverFactory.closeDriver(driver);
        }
    }

    // Reusable validation method
    public static void check(boolean condition, String message) {

        if (condition) {

            System.out.println("PASS: " + message);

        } else {

            throw new AssertionError("FAIL: " + message);
        }
    }
}
