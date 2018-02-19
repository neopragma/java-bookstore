Given("client wants to know how to interact with the Bookstore API") do
  # no action 
end

When("client calls the base URI") do
  begin
    @response = RestClient::Request.execute(method: :get, url: "#{$BASE_URL}", 
    	headers: { accept: 'application/json' } )
  rescue RestClient::ExceptionWithResponse => e
    @error_response_body = e.response.body
    @error_message = e.message
  end
end

Then("client receives API help") do
  response = JSON.parse(@response.body)
  expect(response['description']).to eq("Bookstore Service")
  expect(response['version']).to eq("v1")
end
