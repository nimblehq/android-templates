package com.nimbl3.di.modules

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@GlideModule
class GlideModule : AppGlideModule()
