require 'bundler/setup'
require 'rubygems'
require 'cucumber'
require 'cucumber/rake/task'

Cucumber::Rake::Task.new(:features) do |t|
  ENV['HEADLESS'] = 'false'
  t.profile = 'default'
end

Cucumber::Rake::Task.new(:headless) do |t|
  ENV['HEADLESS'] = 'true'
  t.cucumber_opts = "-f pretty -f html --out hellocukes.html"
end

task :default => :features