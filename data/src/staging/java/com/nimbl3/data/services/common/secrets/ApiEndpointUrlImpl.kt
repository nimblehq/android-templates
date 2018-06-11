package com.nimbl3.data.service.common.secrets

import com.nimbl3.data.service.common.secrets.ApiEndpointUrl

class ApiEndpointUrlImpl : ApiEndpointUrl {
    override val value: String
        get() = "https://www.reddit.com/"
}