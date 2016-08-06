# Extract from RWebSpec to use 
module RWebSpecUtils

	## for debugging, reuse current browser window and run selected test scripts in it.
	#
	def use_current_browser
  	@browser = @driver = $browser
	end

	# Try the operation up to specified timeout (in seconds), and sleep given interval (in seconds).
  # Error will be ignored until timeout
  # Example
  #    try_for { click_link('waiting')}
  #    try_for(10, 2) { click_button('Search' } # try to click the 'Search' button upto 10 seconds, try every 2 seconds
  #    try_for { click_button('Search' }
  def try_for(timeout = $testwise_polling_timeout, polling_interval = $testwise_polling_interval || 1, &block)
    start_time = Time.now

    last_error = nil
    until (duration = Time.now - start_time) > timeout
      begin
        yield
        last_error = nil
				return true 
      rescue ArgumentError => ae
        last_error = ae
      rescue RSpec::Expectations::ExpectationNotMetError => ree
        last_error = ree
      rescue => e
        last_error = e
      end
      sleep polling_interval
    end

    raise "Timeout after #{duration.to_i} seconds with error: #{last_error}." if last_error
    raise "Timeout after #{duration.to_i} seconds."
  end
  
  ##
  #  Convert :first to 1, :second to 2, and so on...
  def symbol_to_sequence(symb)
    value = {:zero => 0,
             :first => 1,
             :second => 2,
             :third => 3,
             :fourth => 4,
             :fifth => 5,
             :sixth => 6,
             :seventh => 7,
             :eighth => 8,
             :ninth => 9,
             :tenth => 10}[symb]
    return value || symb.to_i
  end



  # use win32screenshot library to save curernt active window, which shall be IE
  #
  # opts[:to_dir] => the direcotry to save image under
  def take_screenshot(opts = {})
    # puts "calling new take screenshot: #{$screenshot_supported}"
    unless $screenshot_supported
      puts " [WARN] Screenhost not supported, check whether win32screenshot gem is installed" 
      return
    end

     begin
        screenshot_image_filename =  "screenshot_" + Time.now.strftime("%m%d%H%M%S") + ".jpg"
        the_dump_dir = opts[:to_dir] || default_dump_dir
        FileUtils.mkdir_p(the_dump_dir) unless File.exists?(the_dump_dir)
        screenshot_image_filepath = File.join(the_dump_dir, screenshot_image_filename)
        screenshot_image_filepath.gsub!("/", "\\") if is_windows?

        FileUtils.rm_f(screenshot_image_filepath) if File.exist?(screenshot_image_filepath)

        Win32::Screenshot::Take.of(:foreground).write(screenshot_image_filepath)
        notify_screenshot_location(screenshot_image_filepath)
			rescue ::DL::DLTypeError => de
				puts "No screenshot libray found: #{de}"
      rescue => e
        puts "error on taking screenshot: #{e}"
      end
    
  end

  #= Convenient functions
  #

  # Using Ruby block syntax to create interesting domain specific language,
  # may be appeal to someone.

  # Example:
  #  on @page do |i|
  #    i.enter_text('btn1')
  #    i.click_button('btn1')
  #  end
  def on(page, & block)
    yield page
  end

  # fail the test if user can perform the operation
  #
  # Example:
  #  shall_not_allow { 1/0 }
  def shall_not_allow(& block)
    operation_performed_ok = false
    begin
      yield
      operation_performed_ok = true
    rescue
    end
    raise "Operation shall not be allowed" if operation_performed_ok
  end

  alias do_not_allow shall_not_allow

  # Does not provide real function, other than make enhancing test syntax
  #
  # Example:
  #   allow { click_button('Register') }
  def allow(& block)
    yield
  end

  alias shall_allow allow
  alias allowing allow

  # try operation, ignore if errors occur
  #
  # Example:
  #   failsafe { click_link("Logout") }  # try logout, but it still OK if not being able to (already logout))
  def failsafe(& block)
    begin
      yield
    rescue =>e
    end
  end

  alias fail_safe failsafe

  # default date format returned is 29/12/2007.
  # if supplied parameter is not '%m/%d/%Y' -> 12/29/2007
  # Otherwise, "2007-12-29", which is most approiate date format
  #
  #  %a - The abbreviated weekday name (``Sun'')
  #  %A - The  full  weekday  name (``Sunday'')
  #  %b - The abbreviated month name (``Jan'')
  #  %B - The  full  month  name (``January'')
  #  %c - The preferred local date and time representation
  #  %d - Day of the month (01..31)
  #  %H - Hour of the day, 24-hour clock (00..23)
  #  %I - Hour of the day, 12-hour clock (01..12)
  #  %j - Day of the year (001..366)
  #  %m - Month of the year (01..12)
  #  %M - Minute of the hour (00..59)
  #  %p - Meridian indicator (``AM''  or  ``PM'')
  #  %S - Second of the minute (00..60)
  #  %U - Week  number  of the current year,
  #          starting with the first Sunday as the first
  #          day of the first week (00..53)
  #  %W - Week  number  of the current year,
  #          starting with the first Monday as the first
  #          day of the first week (00..53)
  #  %w - Day of the week (Sunday is 0, 0..6)
  #  %x - Preferred representation for the date alone, no time
  #  %X - Preferred representation for the time alone, no date
  #  %y - Year without a century (00..99)
  #  %Y - Year with century
  #  %Z - Time zone name
  #  %% - Literal ``%'' character

  def today(format = nil)                
    format_date(Time.now, date_format(format))
  end
  alias getToday_AU today
  alias getToday_US today
  alias getToday today


  def days_before(days, format = nil)        
    return nil if !(days.instance_of?(Fixnum))
    format_date(Time.now - days * 24 * 3600, date_format(format))
  end

  def yesterday(format = nil)
    days_before(1, date_format(format))
  end

  def days_from_now(days, format = nil)
    return nil if !(days.instance_of?(Fixnum))
    format_date(Time.now + days * 24 * 3600, date_format(format))
  end
  alias days_after days_from_now

  def tomorrow(format = nil)
    days_from_now(1, date_format(format))
  end


  # return a random number >= min, but <= max
  def random_number(min, max)
    rand(max-min+1)+min
  end

  def random_boolean
    return random_number(0, 1) == 1
  end

  def random_char(lowercase = true)
    if lowercase
      sprintf("%c", random_number(97, 122))
    else
      sprintf("%c", random_number(65, 90))
    end
  end

  def random_digit()
    sprintf("%c", random_number(48, 57))
  end

  def random_str(length, lowercase = true)
    randomStr = ""
    length.times {
      randomStr += random_char(lowercase)
    }
    randomStr
  end

  # Return a random string in a rangeof pre-defined strings
  def random_string_in(arr)
    return nil if arr.empty?
    index = random_number(0, arr.length-1)
    arr[index]
  end
  alias random_string_in_collection random_string_in


  WORDS = %w(alias consequatur aut perferendis sit voluptatem accusantium doloremque aperiam eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo aspernatur aut odit aut fugit sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt neque dolorem ipsum quia dolor sit amet consectetur adipisci velit sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem ut enim ad minima veniam quis nostrum exercitationem ullam corporis nemo enim ipsam voluptatem quia voluptas sit suscipit laboriosam nisi ut aliquid ex ea commodi consequatur quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae et iusto odio dignissimos ducimus qui blanditiis praesentium laudantium totam rem voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident sed ut perspiciatis unde omnis iste natus error similique sunt in culpa qui officia deserunt mollitia animi id est laborum et dolorum fuga et harum quidem rerum facilis est et expedita distinctio nam libero tempore cum soluta nobis est eligendi optio cumque nihil impedit quo porro quisquam est qui minus id quod maxime placeat facere possimus omnis voluptas assumenda est omnis dolor repellendus temporibus autem quibusdam et aut consequatur vel illum qui dolorem eum fugiat quo voluptas nulla pariatur at vero eos et accusamus officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae itaque earum rerum hic tenetur a sapiente delectus ut aut reiciendis voluptatibus maiores doloribus asperiores repellat)

  # Pick a random value out of a given range.
  def value_in_range(range)
    case range.first
    when Integer then number_in_range(range)
    when Time then time_in_range(range)
    when Date then date_in_range(range)
    else range.to_a.rand
    end
  end

  # Generate a given number of words. If a range is passed, it will generate
  # a random number of words within that range.
  def words(total)
    if total.class == Range
      (1..interpret_value(total)).map { WORDS[random_number(total.min, total.max)] }.join(' ')
    else
      (1..interpret_value(total)).map { WORDS[random_number(0, total)] }.join(' ')
    end
  end

  # Generate a given number of sentences. If a range is passed, it will generate
  # a random number of sentences within that range.
  def sentences(total)
    (1..interpret_value(total)).map do
      words(5..20).capitalize
    end.join('. ')
  end

  # Generate a given number of paragraphs. If a range is passed, it will generate
  # a random number of paragraphs within that range.
  def paragraphs(total)
    (1..interpret_value(total)).map do
      sentences(3..8).capitalize
    end.join("\n\n")
  end

  # If an array or range is passed, a random value will be selected to match.
  # All other values are simply returned.
  def interpret_value(value)
    case value
    when Array then value.rand
    when Range then value_in_range(value)
    else value
    end
  end

  private

  def time_in_range(range)
    Time.at number_in_range(Range.new(range.first.to_i, range.last.to_i, rangee.exclude_end?))
  end

  def date_in_range(range)
    Date.jd number_in_range(Range.new(range.first.jd, range.last.jd, range.exclude_end?))
  end

  def number_in_range(range)
    if range.exclude_end?
      rand(range.last - range.first) + range.first
    else
      rand((range.last+1) - range.first) + range.first
    end
  end

  def format_date(date, date_format = '%d/%m/%Y')
    date.strftime(date_format)
  end

  def date_format(format_argument)
    if format_argument.nil? then
      get_locale_date_format(default_locale)        
    elsif format_argument.class == Symbol then
      get_locale_date_format(format_argument)
    elsif format_argument.class == String then
      format_argument
    else
      # invalid input, use default
      get_locale_date_format(default_date_format)
    end

  end

  def get_locale_date_format(locale)
    case locale
    when :us
      "%m/%d/%Y"
    when :au, :uk
      "%d/%m/%Y"
    else
      "%Y-%m-%d"
    end
  end

  def default_locale
    return :au
  end


  def average_of(array)
    array.inject(0.0) { |sum, e| sum + e } / array.length
  end

  # NOTE might cause issues
  # why it is removed total
  def sum_of(array)
    array.inject(0.0) { |sum, e| sum + e }
  end

	## Data Driven Tests
	#
	#  Processing each row in a CSV file, must have heading rows
	# 
	#  Usage:
	# 
	#   process_each_row_in_csv_file(@csv_file) { |row| 
	#     goto_page("/")
	#     enter_text("username", row[1])
	#     enter_text("password", row[2])
	#     click_button("Sign in")
	#     page_text.should contain(row[3])
	#     failsafe{ click_link("Sign off") }
	#   }
  #
	def process_each_row_in_csv_file(csv_file, &block)
    require 'faster_csv'
    connect_to_testwise("CSV_START",  csv_file) if $testwise_support
    has_error = false
    idx = 0
    FasterCSV.foreach(csv_file, :headers => :first_row, :encoding => 'u') do |row|
			connect_to_testwise("CSV_ON_ROW",  idx.to_s)  if $testwise_support 
			begin
        yield row
				connect_to_testwise("CSV_ROW_PASS",  idx.to_s)  if $testwise_support
			rescue => e
				connect_to_testwise("CSV_ROW_FAIL",  idx.to_s)  if $testwise_support
        has_error = true
      ensure
        idx += 1
			end
    end

		connect_to_testwise("CSV_END",  "")  if $testwise_support
		raise "Test failed on data" if has_error
  end


  def wait_for_ajax_complete(max_seconds)
    max_seconds.times do 
      is_ajax_complete = driver.execute_script("return jQuery.active == 0")
      if is_ajax_complete
        return
      end
      sleep(1)
    end
    raise "Timed out waiting for AJAX call after #{max_seconds} seconds"
  end
		
end

