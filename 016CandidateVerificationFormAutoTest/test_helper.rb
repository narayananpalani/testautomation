require 'rubygems'
require 'selenium-webdriver'
require 'rspec'


# use utils in RWebSpec and better integration with TestWise
require "#{File.dirname(__FILE__)}/rwebspec_utils.rb"

# when running in TestWise, it will auto load TestWiseRuntimeSupport, ignore otherwise
if defined?(TestWiseRuntimeSupport)
  ::TestWise::Runtime.load_webdriver_support # for selenium webdriver support
else
require "#{File.dirname(__FILE__)}/testwise_support.rb"
end


# this loads defined page objects under pages folder
require "#{File.dirname(__FILE__)}/pages/abstract_page.rb"
Dir["#{File.dirname(__FILE__)}/pages/*_page.rb"].each { |file| load file }



# The default base URL for running from command line or continuous build process
$BASE_URL = "https://docs.google.com/forms/d/e/1FAIpQLScLxTQWgPTnbxTi3uLQNPE2z4r8S_ZRcGTbfSU--WfGNDzQ0Q/viewform"

# This is the helper for your tests, every test file will include all the operation
# defined here.
module TestHelper

  include RWebSpecUtils

  if defined?(TestWiseRuntimeSupport)  # TestWise 5
    include TestWiseRuntimeSupport 
  else
    include TestWiseSupport	 # TestWise 4
  end


  

  #
  # An Example helper function
  #
  # In you test case, you can use
  #
  #   login_as("homer", "Password")
  #   login_as("bart")  # the password will be default to 'TestWise'
  #   login("lisa")     # same as login_as
  #
  # 
  # def login_as(username, password = "TestWise")
  #   enter_text("username", username)
  #   enter_text("password", password)
  #   click_link("Login")
  # end
  # alias login login_as


  def browser_type
    if $TESTWISE_BROWSER then
      $TESTWISE_BROWSER.downcase.to_sym
    elsif ENV["BROWSER"]
      ENV["BROWSER"].downcase.to_sym
    else
      RUBY_PLATFORM =~ /mingw/ ? "ie".to_sym : "firefox".to_sym
    end
  end
  alias the_browser browser_type

  def site_url(default = $BASE_URL)
    $TESTWISE_PROJECT_BASE_URL || ENV['BASE_URL'] || default
  end
	


	def driver
		@driver
	end
	alias browser driver

	def	page_text
	  driver.find_element(:tag_name => "body").text
	end
	
	def visit(path)
	  driver.navigate.to(site_url + path)
	end
  def verify(fullname, emailid,contactnumber,experience,sapexp,netweaverexp,kernelexp,currentcompany) 
    driver.find_element(:id,'entry_1000000').send_keys(fullname)
    driver.find_element(:id,'entry_1000013').send_keys(emailid)
    driver.find_element(:id,'entry_1000014').send_keys(contactnumber)
    driver.find_element(:id,'group_1000001_1').click
    driver.find_element(:id,'entry_1000002').send_keys(sapexp)
    driver.find_element(:id,'entry_1000003').send_keys(netweaverexp)
    driver.find_element(:id,'entry_1000004').send_keys(kernelexp)
    driver.find_element(:id,'entry_1000005').send_keys(currentcompany)
    sleep 10
    driver.find_element(:id,'ss-submit').click

  end
	 

  	
end
