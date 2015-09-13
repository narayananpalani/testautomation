using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
//using System.Runtime.Remoting.Metadata.W3cXsd2001;
//using System.Text;
//using System.Threading;
//using System.Threading.Tasks;
using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;

namespace FinanceIUA.PageObjects
{
    public class FinancePage
    {
 
        [FindsBy(How = How.CssSelector, Using = "#feMain2 .local_table")]
        private IWebElement banksRatesTable;

        [FindsBy(How = How.XPath, Using = "//div[@class='Right']//h2[contains(text(), 'Средний курс валют')]/../table[@class='local_table']/tbody/tr[2]/td[2]")]
        private IWebElement averageUSDPurchasingRate;

        [FindsBy(How = How.XPath, Using = "//div[@class='Right']//h2[contains(text(), 'Средний курс валют')]/../table[@class='local_table']/tbody/tr[2]/td[3]")]
        private IWebElement averageUSDSellinRate;

        [FindsBy(How = How.XPath, Using = "//div[@class='Right']//h2[contains(text(), 'Средний курс валют')]/../table[@class='local_table']/tbody/tr[3]/td[2]")]
        private IWebElement averageEURPurchasingRate;

        [FindsBy(How = How.XPath, Using = "//div[@class='Right']//h2[contains(text(), 'Средний курс валют')]/../table[@class='local_table']/tbody/tr[3]/td[3]")]
        private IWebElement averageEURSellinRate;

        [FindsBy(How = How.CssSelector, Using = "a[href='/eur/']")] private IWebElement eur_link;
        [FindsBy(How = How.CssSelector, Using = "a[href='/usd/']")] private IWebElement usd_link;

        [FindsBy(How = How.XPath,
            Using ="//div[@id='feMain2']/h1[contains(text(), 'Курс валют банков в Украине')]/../ul/li[@class='current']")]
        private IWebElement currentCurrency;


        private static IWebDriver driver;

        public static FinancePage NavigateTo(IWebDriver webDriver)
        {
            driver = webDriver;
            driver.Navigate().GoToUrl("http://www.seleniumhq.org/");
            var financePage = new FinancePage();
            PageFactory.InitElements(driver, financePage);
            return financePage;
        }



        public String SetCurrensy(string currency)
        {
            if (currency.Equals("EUR"))
            {
                eur_link.Click();
            }
            else if (currency.Equals("USD"))
            {
                usd_link.Click();
            }
            //WaitForAjax();
            return currentCurrency.Text;
        }

        public Hashtable GetAverageRates(string currency)
        {
            var averageRates = new Hashtable();

            if (currency.Equals("EUR"))
            {
                averageRates.Add("Purchasing", averageEURPurchasingRate.Text.Substring(0, averageEURPurchasingRate.Text.IndexOf("\r\n", System.StringComparison.Ordinal)));
                averageRates.Add("Selling", averageEURSellinRate.Text.Substring(0, averageEURSellinRate.Text.IndexOf("\r\n", System.StringComparison.Ordinal)));
            }
            else if (currency.Equals("USD"))
            {
                averageRates.Add("Purchasing", averageUSDPurchasingRate.Text.Substring(0, averageUSDPurchasingRate.Text.IndexOf("\r\n", System.StringComparison.Ordinal)));
                averageRates.Add("Selling", averageUSDSellinRate.Text.Substring(0, averageUSDSellinRate.Text.IndexOf("\r\n", System.StringComparison.Ordinal)));                
            }

            return averageRates;
        }


        public Hashtable GetAverageBanksRates(int decimalDigits)
        {
            var averageRates = new Hashtable();

            var banksPurchasingRates = banksRatesTable.FindElements(By.XPath("//tr[position()>1 and position() < last()-4]/td[2]"));
            var banksAveragePurchasingRate = banksPurchasingRates.Select(cell => cell.Text).ToList();

            var banksSellingRates = banksRatesTable.FindElements(By.XPath("//tr[position()>1 and position() < last()-4]/td[3]"));
            var banksAverageSellingRate = banksSellingRates.Select(cell => cell.Text).ToList();

            averageRates.Add("Purchasing", CalculateAverageRate(banksAveragePurchasingRate, decimalDigits));
            averageRates.Add("Selling", CalculateAverageRate(banksAverageSellingRate, decimalDigits));

            return averageRates;
        }


        public string CalculateAverageRate(List<string> values, int decimalDigits)
        {
            double averageSellingRate = values.Sum(item => Convert.ToDouble(item)) / values.Count;
            return Math.Round(averageSellingRate, decimalDigits, MidpointRounding.ToEven).ToString(CultureInfo.InvariantCulture);
        }

        //public void WaitForAjax()
        //{
        //    while (true) // Handle timeout somewhere
        //    {
        //        var ajaxIsComplete = (bool)(driver as IJavaScriptExecutor).ExecuteScript("return jQuery.active == 0", driver);
        //        if (ajaxIsComplete)
        //            break;
        //        Thread.Sleep(100);
        //    }
        //}
    }
}
