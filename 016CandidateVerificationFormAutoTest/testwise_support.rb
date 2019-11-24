require 'socket'

MAX_MESSAGE_LENGTH = 8192 # < 10K

$testwise_support = true

module TestWiseSupport

  def debugging?
    if ENV["TESTWISE_DEBUGGING"].to_s == "true" && ENV["TESTWISE_RUNNING_AS"] == "test_case"
      return true
    end
    return $TESTWISE_DEBUGGING && $TESTWISE_RUNNING_AS == "test_case"
  end

  def debug(message)
    Thread.pass
    if (ENV["RUN_IN_TESTWISE"].to_s == "true" || $RUN_IN_TESTWISE) && message
      the_sent_msg = message.to_s
      if the_sent_msg.size > MAX_MESSAGE_LENGTH
        the_sent_msg = the_sent_msg[0..MAX_MESSAGE_LENGTH] + "..."
      end
      connect_to_testwise(" DEBUG",  the_sent_msg + "\r\n")
    else
      puts message
    end

  end


  # Support of TestWise to ajust the intervals between keystroke/mouse operations
  def operation_delay
    begin
      $TESTWISE_OPERATION_DELAY = ENV["TESTWISE_OPERATION_DELAY"] if ENV["TESTWISE_OPERATION_DELAY"]
      if $TESTWISE_OPERATION_DELAY && $TESTWISE_OPERATION_DELAY > 0 &&
        $TESTWISE_OPERATION_DELAY < 30000  then # max 30 seconds
          Thread.pass
          sleep($TESTWISE_OPERATION_DELAY / 1000)
      end

      while (ENV["TESTWISE_PAUSE"] && ENV["TESTWISE_PAUSE"].to_s == "true")  || $TESTWISE_PAUSE
        Thread.pass
        debug("Paused, waiting ...")
        sleep 1
      end
    rescue => e
      puts "Error on delaying: #{e}"
      # ignore
    end
  end

  def notify_screenshot_location(image_file_path)
    connect_to_testwise("  SHOT", image_file_path)
  end

  # find out the line (and file) the execution is on, and notify iTest via Socket
  def dump_caller_stack
    return unless (ENV["TESTWISE_TRACE_EXECUTION"].to_s == "true" || $TESTWISE_TRACE_EXECUTION)
    begin
      trace_lines = []
      trace_file = nil
      found_first_spec_reference = false
      caller.each_with_index do |position, idx|
        next unless position =~ /\A(.*?):(\d+)/
        trace_file = $1
        if trace_file =~ /(_spec|_test|_rwebspec)\.rb\s*$/ || trace_file =~ /\.feature$/
          found_first_spec_reference = true
          trace_lines << position
          break
        end
        trace_lines << position
        break if trace_file =~ /example\/example_methods\.rb$/ or trace_file =~ /example\/example_group_methods\.rb$/
        break if trace_lines.size > 10
        # TODO: send multiple trace to be parse with pages.rb
        # break if trace_file =~ /example\/example_methods\.rb$/ or trace_file =~ /example\/example_group_methods\.rb$/ or trace_file =~ /driver\.rb$/ or trace_file =~ /timeout\.rb$/ # don't include rspec or ruby trace
      end

      #  (trace_file.include?("_spec.rb") || trace_file.include?("_rwebspec.rb") || trace_file.include?("_test.rb") || trace_file.include?("_cmd.rb"))
      if !trace_lines.empty?
        connect_to_testwise(" TRACE", trace_lines.reverse.join("|"))
      end

    rescue => e
      puts "failed to capture log: #{e}"
    end
  end


  def connect_to_testwise (message_type, body)
    Thread.pass
    begin
      the_message = message_type + "|" + body
      if @last_message == the_message then # ignore the message same as preivous one
        return
      end
      itest_port = ENV["TESTWISE_TRACE_PORT"].to_i || $TESTWISE_TRACE_PORT || 7025
      itest_socket = Socket.new(Socket::AF_INET, Socket::SOCK_STREAM, 0)
      itest_socket.connect(Socket.pack_sockaddr_in(itest_port, '127.0.0.1'))
      itest_socket.puts(the_message)
      @last_message = the_message
      itest_socket.close
    rescue => e
    end
  end

end