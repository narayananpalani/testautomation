# version: 1.0

require 'httpclient'
require 'net/http'
require 'yaml'

def buildwise_start_build(options)
  the_response_content = contact_buildwise_post("/builds/begin", "options" => YAML.dump(options))
  new_build_id = the_response_content
end

def buildwise_finish_build(build_id)
  puts "[buildwise.rake] Finishing build: #{build_id}"
  if build_id && (build_id.class == Fixnum  || build_id =~ /\d+/)
    pdata = {
      "id" =>  build_id,
    }         
    outcome = contact_buildwise_post("/builds/#{build_id}/finish", pdata)    
    puts "[buildwise.rake] Post /builds/#{build_id}/finish => #{outcome}"
    return outcome
  end
end

def buildwise_build_status(build_id)
  return contact_buildwise_get("/builds/#{build_id}/status")
end

def buildwise_build_ui_test_status(build_id)
  return contact_buildwise_get("/builds/#{build_id}/ui_test_status")
end

def buildwise_build_failed(build_id)
  failed_files = contact_buildwise("/builds/#{build_id}/failures?stage=Functional") if build_id && build_id =~ /\d+/
  failed_file_list = failed_files.split(",")
end

# TODO when the Rake task is loaded, it will be invoked straight way
# 
# Check the builder order for this project
#
# @params: project_identifier, a string unique identifier
def buildwise_ui_test_order(project_identifier)
  puts "[INFO] Check test order of project identifier: #{project_identifier}"
  
  if project_identifier
    ui_test_in_order = contact_buildwise_get("/projects/#{project_identifier}/ui_test_priority", false)
    # puts "Get test order: #{ui_test_in_order}"
    ui_test_in_order.split(",")  rescue nil
  else
    return nil
  end
end


def buildwise_failed_build_tests(project_identifier)
  if project_identifier
    failed_full_build_tests = contact_buildwise_get("/projects/#{project_identifier}/failed_tests")
    failed_full_build_tests.split(",")  rescue []
  else
    return []
  end
end

def buildwise_successful_build_tests(project_identifier)
  if project_identifier
    failed_full_build_tests = contact_buildwise_get("/projects/#{project_identifier}/successful_tests")
    failed_full_build_tests.split(",")  rescue []
  else
    return []
  end
end


def contact_buildwise_get(path, raise_exception = false)
  begin
    client = HTTPClient.new
    url = "#{BUILDWISE_URL}#{path}"
    puts "  [buildwise.rake] Contacting Server: #{url}"
    the_res = client.get(url).body
    the_res = the_res.content if the_res.respond_to?("content")
    return nil if the_res.include?("Internal Server Error")
    return the_res
  rescue => e
    puts "error to contact BAM with GET:  #{e}"
    if raise_exception 
      raise e
    else
      return nil
    end
  end
end

def contact_buildwise_post(path, pdata)
  begin
    url = "#{BUILDWISE_URL}#{path}"
    puts "  [buildwise.rake] Posting to |#{url}|"
    response = HTTPClient.new.post(url, pdata)
    the_res = response.body
    the_res = the_res.content if the_res.respond_to?("content")    
    return the_res
  rescue => e
    puts "error to contact BAM with POST:  #{e}"
    return nil
  end
end