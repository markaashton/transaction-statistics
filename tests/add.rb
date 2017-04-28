require 'net/http'
require 'json'
require 'date'

def send_add
    uri = URI('http://localhost:8080/transactions')
    http = Net::HTTP.new(uri.host, uri.port)
    req = Net::HTTP::Post.new(uri.path, 'Content-Type' => 'application/json')
    req.body = {amount: '10.0', timestamp: Time.now.to_i*1000 }.to_json
    res = http.request(req)
    puts "response code: #{res.code}"
rescue => e
    puts "failed #{e}"
end

send_add
