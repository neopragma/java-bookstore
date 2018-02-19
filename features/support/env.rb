# env.rb for bookstore
require 'json'
require 'json'
require 'rspec'
require 'rspec/expectations'
require 'rest-client'

ENV['RACK_ENV'] ||= 'development'
ENV['BASE_URI'] ||= 'http://localhost:8080'
$BASE_URL = ENV['BASE_URI']