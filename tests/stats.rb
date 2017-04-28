require 'net/http'
require 'uri'

def send_get
    url = 'http://localhost:8080/statistics/' # trailing slash is important
    uri = URI.parse(url)
    output = Net::HTTP.get(uri) # GET request
    puts output
end

send_get
