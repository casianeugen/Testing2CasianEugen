import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class ParkingCalculatorTest {
    public WebDriver driver;
    @BeforeTest
    public void beforeTest() {
        driver = new ChromeDriver();
        BasicConfigurator.configure();
        driver.get("https://www.shino.de/parkcalc/");
    }
    @DataProvider(name = "data-provider")
    public Object[][] dpMethod(){
        Object data[][] = new Object[4][7];
        data[0][0] = "Economy Parking";
        data[0][1] = "2/14/2022";
        data[0][2] = "11:00";
        data[0][3] = "AM";
        data[0][4] = "2/20/2022";
        data[0][5] = "11:00";
        data[0][6] = "AM";

        data[1][0] = "Short-Term Parking";
        data[1][1] = "5/14/2022";
        data[1][2] = "8:00";
        data[1][3] = "AM";
        data[1][4] = "5/20/2022";
        data[1][5] = "11:00";
        data[1][6] = "PM";

        data[2][0] = "Short-Term Garage Parking";
        data[2][1] = "6/1/2022";
        data[2][2] = "10:00";
        data[2][3] = "AM";
        data[2][4] = "6/5/2022";
        data[2][5] = "18:00";
        data[2][6] = "PM";

        data[3][0] = "Valet Parking";
        data[3][1] = "2/14/2022";
        data[3][2] = "11:00";
        data[3][3] = "PM";
        data[3][4] = "6/5/2022";
        data[3][5] = "18:00";
        data[3][6] = "AM";
        return data;
    }
    @Test (dataProvider = "data-provider")
    public void Test1(String parkingLotS, String date1S, String time1S, String radioVal1,
                      String date2S, String time2S, String radioVal2)
    {
        WebElement parkingLot = driver.findElement(By.name("ParkingLot"));
        parkingLot.sendKeys(parkingLotS);
        WebElement date1 = driver.findElement(By.name("StartingDate"));
        date1.clear();
        date1.sendKeys(date1S);
        WebElement time1 = driver.findElement(By.name("StartingTime"));
        time1.clear();
        time1.sendKeys(time1S);
        WebElement radio1 = driver.findElement(By.xpath("//input[@name='StartingTimeAMPM'][@value='" + radioVal1 + "']"));
        radio1.click();
        WebElement date2 = driver.findElement(By.name("LeavingDate"));
        date2.clear();
        date2.sendKeys(date2S);
        WebElement time2 = driver.findElement(By.name("LeavingTime"));
        time2.clear();
        time2.sendKeys(time2S);
        WebElement radio2 = driver.findElement(By.xpath("//input[@name='LeavingTimeAMPM'][@value='" + radioVal2 + "']"));
        radio2.click();

        WebElement button = driver.findElement(By.name("Submit"));
        button.click();

    }
    @AfterTest
    public void afterTest() {
        System.out.println("Closing the browser ");
        driver.close();
    }

}
