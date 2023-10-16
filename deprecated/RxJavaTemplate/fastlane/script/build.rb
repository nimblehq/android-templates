# frozen_string_literal: true

module Fastlane
  class FastFile
    def build_and_distribute(config)
      build_apk(config[:flavor], config[:build_type])

      firebase_app_distribution(
        app: config[:app_id],
        apk_path: config[:apk_path],
        groups: config[:groups]
      )
    end

    private

    def build_apk(flavour, build_type)
      gradle(task: 'app:assemble', flavor: flavour, build_type: build_type)
    end
  end
end
