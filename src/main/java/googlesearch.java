import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.util.Elements;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class googlesearch {
    @Test
    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        Duration duration = Duration.ofSeconds(10);
        WebDriver driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        options.addArguments("--start-maximized");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //wait before each element
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


        driver.get("https://www.google.com");
        driver.getWindowHandle();
        String wordToSearch = "testing";
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(wordToSearch);
        element.submit();

        new WebDriverWait(driver, duration).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver1) {
                return driver1.getTitle().toLowerCase().startsWith(wordToSearch);
            }
        });

        try{
            List<WebElement> links = driver.findElements(By.xpath( "//div[@class='yuRUbf']/a"));
            for (WebElement link : links) {
                link.sendKeys(selectLinkOpenInNewTab);
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document document = dbBuilder.newDocument();

            Element word = document.createElement(wordToSearch);
            document.appendChild(word);

            Element linksToSite = document.createElement("links");
            word.appendChild(linksToSite);

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            for (int i = 0; i < tabs.size(); i++) {
                Element linkToSite = document.createElement("link");
                linksToSite.appendChild(linkToSite);
                Element url = document.createElement("url");
                Element pageName = document.createElement("pageName");
                Element wordCount = document.createElement("wordCount");
                String tab = tabs.get(i);
                driver.switchTo().window(tab);
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                url.appendChild(document.createTextNode(driver.getCurrentUrl()));
                linkToSite.appendChild(url);
                pageName.appendChild(document.createTextNode(driver.getTitle()));
                linkToSite.appendChild(pageName);
                wordCount.appendChild(document.createTextNode(String.valueOf(StringUtils.countMatches(driver.getPageSource().toLowerCase(), wordToSearch))));
                linkToSite.appendChild(wordCount);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("D:\\Casian Eugen\\Univer anul III sem 2\\Testarea software automatizata\\Testing2CasianEugen\\search.xml"));
            transformer.transform(source, result);
        }
        catch(ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }

        Thread.sleep(30000);
        driver.quit();
    }
}