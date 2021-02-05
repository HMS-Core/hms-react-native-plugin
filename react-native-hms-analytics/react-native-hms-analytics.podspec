ENV['SWIFT_VERSION'] = '5'
require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-hms-analytics"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-hms-analytics
                   DESC
  s.homepage     = "https://github.com/HMS-Core/hms-react-native-plugin/tree/master/react-native-hms-analytics"
  # brief license entry:
  s.license      = package["license"]
  s.platforms    = { :ios => "9.0" }
  s.authors      = { "HMS-Core" => "https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides-V5/environment-faq-0000001050162062-V5" }
  s.source       = { :git => "", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true
  s.static_framework = true
  s.dependency "React"
  # HMS Analytics Kit SDK
  s.dependency "HiAnalytics", "5.1.0.300"

end

