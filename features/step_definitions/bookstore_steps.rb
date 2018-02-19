Given(/^no authors are defined$/) do
  # no action
end

Given("{int} authors are defined") do |count|
  @count = count
end

When /^I add an author with display name (.*), surname (.*), given name (.*), and middle name (.*)$/ do |display_name, surname, given_name, middle_name|
  @expected = { 'display-name' => display_name, 
                'surname' => surname ,
                'given-name' => given_name,
                'middle-name' => middle_name }
  begin
    jsonPayload = { 'display-name' => display_name, 
        'surname' => surname,
        'given-name' => given_name,
        'middle-name' => middle_name }.to_json
    @response = RestClient.post "#{$BASE_URL}/v1/author", 
      jsonPayload, 
      :content_type => :json, :accept => :json
  rescue RestClient::ExceptionWithResponse => e
    @error_response_body = e.response.body
    @error_message = e.message
  end
end

When("I request a list of authors") do
  begin
    @response = RestClient.get "#{$BASE_URL}/v1/authors", 
      :content_type => :json, :accept => :json
  rescue RestClient::ExceptionWithResponse => e
    @error_response_body = e.response.body
    @error_message = e.message
  end
end

Then("the system will return information about that author") do
  responseHash = JSON.parse(@response)
  @expected.each do |key, value|
    expect(responseHash[key]).to eq(@expected[key])
  end  
end

Then("I receive a list of the authors") do 
  responseHash = JSON.parse(@response)
  expect(responseHash.count).to eq(@count)
end  