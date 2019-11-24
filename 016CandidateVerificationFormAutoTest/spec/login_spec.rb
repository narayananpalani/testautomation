load File.dirname(__FILE__) + '/../test_helper.rb'

describe "User Auth" do
  include TestHelper

  before(:all) do    
    @driver = $browser = Selenium::WebDriver.for(browser_type)
    @driver.navigate.to(site_url)
    sleep 10
  end

  after(:all) do
    @driver.quit unless debugging?
  end


  it "[1] Candidate can be verified" do
    verify("Mark Harrison", "mark@testlab.com","07878345445","6","2","1.5","2","ABCltd")
    sleep 10
    expect(page_text).to include("Your response has been recorded")
  end

end
