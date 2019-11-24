require "#{File.dirname(__FILE__)}/../rwebspec_utils.rb"
require "#{File.dirname(__FILE__)}/../testwise_support.rb"

# This is the parent page for all page objects, for operations across all pages, define here
class AbstractPage 

  
  # If want to use utility methods such as fail_safe { }, uncomment the line below 
  # 
  include RWebSpecUtils

  # If want to use debug('message') to TestWise Console, uncomment the statements below
  #
	if defined?(TestWiseRuntimeSupport)
	  include TestWiseRuntimeSupport
  else
  	include TestWiseSupport
	end
  

  def initialize(driver, text = "")
    @driver = driver
    raise "Page text '#{text}' not found on #{self.class.name}" unless driver.page_source.include?(text)	
  end

  
  def driver
    @driver
  end
  alias browser driver
  
end
