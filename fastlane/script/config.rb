# frozen_string_literal: true

module Config
  module_function

  SLACK_URL = ''

  # Firebase tester groups
  internal_group = 'internal'
  product_group = 'product'
  tester_group = 'testers'

  # Return the absolute path to the app's apk for the build variant
  def apk_path(flavour, build_type)
    "app/build/outputs/apk/#{flavour}/#{build_type}/app-#{flavour}-#{build_type}.apk".downcase
  end

  TARGETS = {
    staging_debug: {
      app_id: '',
      groups: [internal_group, tester_group].join(', '),
      flavor: 'Staging',
      build_type: 'Debug',
      apk_path: apk_path('staging', 'debug')
    },

    production_debug: {
      app_id: '',
      groups: [internal_group, product_group, tester_group].join(', '),
      flavor: 'Production',
      build_type: 'Debug',
      apk_path: apk_path('production', 'debug')
    }
  }.freeze

  # Ensure that all the required Environment variables and tool Paths are available,
  # Throws meaningful error messages if it finds any missing Configuration.
  def sanity_check
    errors = []
    errors << verify_slack_config unless verify_slack_config.nil?
    errors << verify_workspace_config unless verify_workspace_config.nil?
    errors << verify_firebase_token unless verify_firebase_token.nil?
    throw errors unless errors.empty?
  end

  def verify_slack_config
    return unless ENV['SLACK_URL'].nil?

    'Missing env.SLACK_URL, please set it accordingly and retry'
  end

  def verify_workspace_config
    return unless ENV['WORKSPACE'].nil?

    'Missing env.WORKSPACE, please set it accordingly and retry'
  end

  def verify_firebase_token
    return unless ENV['FIREBASE_TOKEN'].nil?

    'Missing env.FIREBASE_TOKEN for Firebase, please set it accordingly and retry'
  end
end
