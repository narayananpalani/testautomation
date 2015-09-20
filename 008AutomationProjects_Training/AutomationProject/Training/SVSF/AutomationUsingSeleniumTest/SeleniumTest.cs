/*
 Author: Narayanan Palani
 EmailId: exceptionaltalent@tech-center.com
 Date: 14/05/2015
 */

using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
//using OpenQA.Selenium.IE;
//using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Chrome;
//add this name space to access WebDriverWait
using OpenQA.Selenium.Support.UI;
using System.Threading;
namespace AutomationUsingSeleniumTest
{
    /// <summary>
    /// Data driven testing of target website using selenium.
    /// </summary>
    [TestClass]
    public class SeleniumTest
    {
      
        private TestContext testContextInstance;
        
        public static IWebDriver WebDriver= new ChromeDriver(@"E:\AutomationProject\Training\SVSF\AutomationUsingSeleniumTest\packages\Selenium.WebDriver.ChromeDriver.2.16.0.0\driver");
        /// <summary>
        ///Gets or sets the test context which provides
        ///information about and functionality for the current test run.
        ///</summary>
        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

        #region Additional test attributes
      
        // Use TestInitialize to run code before running each test 
        [TestInitialize()]
        public void MyTestInitialize()
        {
           // var capabilitiesInternet = new OpenQA.Selenium.Remote.DesiredCapabilities();
            //capabilitiesInternet.SetCapability("ignoreProtectedModeSettings", true);
            //WebDriver = new InternetExplorerDriver(capabilitiesInternet);
            //WebDriver = new ChromeDriver();
            
        }

        //
        // Use TestCleanup to run code after each test has run
        [TestCleanup()]
        public void MyTestCleanup()
        {
            
            WebDriver.Quit();

        }
        //
        #endregion
        
       [DeploymentItem("AutomationUsingSeleniumTest\\DDT.csv"), DataSource("Microsoft.VisualStudio.TestTools.DataSource.CSV", "|DataDirectory|\\DDT.csv", "DDT#csv", DataAccessMethod.Sequential)]
        [TestMethod]
        public void TestCurrencyConvertorWithDDT()
        {
           
           //Read your first country currency name
            var convertVal = TestContext.DataRow["FirstCountryByText"].ToString();
           //Read your second contry currency
            var inToVal = TestContext.DataRow["SecondCountryByText"].ToString();
           //Read Expected value from data source
            var expectedValue = TestContext.DataRow["ExpectedValue"].ToString();
           //Goto the Target website
            WebDriver.Navigate().GoToUrl("http://www.x-rates.com/calculator.html");
            var setValueConvert = WebDriver.FindElement(By.Name("from"));
            var setValueInto = WebDriver.FindElement(By.Name("to"));
            var calculateButton = WebDriver.FindElement(By.Name("Calculate"));
            var outPutvalue = WebDriver.FindElement(By.Name("outV"));
          
            var selectConvertItem=new SelectElement(setValueConvert);
            var selectIntoItem = new SelectElement(setValueInto);
          
            selectConvertItem.SelectByText(convertVal);
            selectIntoItem.SelectByText(inToVal);
            calculateButton.Click();
            var currencyValue =outPutvalue.GetAttribute("value") ;
          
            Thread.Sleep(900);//Not a good practise to use Sleep
           //Get the screen shot of the web page and save it on local disk 
           SaveScreenShot(WebDriver.Title);
            Assert.AreEqual(expectedValue,currencyValue.Replace("INR","").Trim());
        
        }

        /// <summary>
        /// This will Take the screen shot of the webpage and will save it at particular location
        /// </summary>
        /// <param name="screenshotFirstName"></param>
        private static void SaveScreenShot(string screenshotFirstName)
        {
            var folderLocation = Environment.CurrentDirectory.Replace("Out","")+"\\ScreenShot\\";
            if (!Directory.Exists(folderLocation))
            {
                Directory.CreateDirectory(folderLocation);
            }
            var screenshot = ((ITakesScreenshot)WebDriver).GetScreenshot();
            var filename = new StringBuilder(folderLocation);
            filename.Append(screenshotFirstName);
            filename.Append(DateTime.Now.ToString("dd-mm-yyyy HH_mm_ss"));
            filename.Append(".png");
            screenshot.SaveAsFile(filename.ToString(), System.Drawing.Imaging.ImageFormat.Png);

        }

        [TestMethod]
        public void TestCurrencyConvertorWithoutDDT()
        {
            //Read your first country currency name
            var convertVal = "American Dollar";
            //Read your second contry currency
            var inToVal = "Indian Rupee";
            //Read Expected value from data source
            var expectedValue = "49.1446 INR";
            //Goto the Target website
            WebDriver.Navigate().GoToUrl("http://www.x-rates.com/calculator.html");
            WebDriverWait wait = new WebDriverWait(WebDriver, new TimeSpan(0, 0, 50));
            var setValueConvert = WebDriver.FindElement(By.Name("from"));
            var setValueInto = WebDriver.FindElement(By.Name("to"));
            var calculateButton = WebDriver.FindElement(By.Name("Calculate"));
            var outPutvalue = WebDriver.FindElement(By.Name("outV"));

            var selectConvertItem = new SelectElement(setValueConvert);
            var selectIntoItem = new SelectElement(setValueInto);

            selectConvertItem.SelectByText(convertVal);
            selectIntoItem.SelectByText(inToVal);
            calculateButton.Click();
            var currencyValue = outPutvalue.GetAttribute("value");

            Thread.Sleep(900);//Not a good practise to use Sleep
            //Get the screen shot of the web page and save it on local disk 
            SaveScreenShot(WebDriver.Title);
            Assert.AreEqual(expectedValue, currencyValue.Trim());

        }

    }
}
