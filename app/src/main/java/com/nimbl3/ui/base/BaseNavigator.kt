package com.nimbl3.ui.base

import android.app.Activity
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.findNavController
import co.omise.gcpf.app.extension.getResourceName
import timber.log.Timber

interface BaseNavigator {

    val navHostFragment: Int?

    fun findNavController(): NavController?

    fun navigate(event: NavigationEvent)

    fun navigateUp()
}

abstract class BaseNavigatorImpl(protected val activity: Activity) : BaseNavigator {

    private var navController: NavController? = null

    override fun findNavController(): NavController? {
        return navController ?: try {
            navHostFragment?.let(activity::findNavController)
        } catch (e: IllegalStateException) {
            // Log Crashlytics as non-fatal for monitoring
            Timber.e(e)
            null
        }
    }

    override fun navigateUp() {
        findNavController()?.navigateUp()
    }

    protected fun popBackTo(@IdRes destinationId: Int, inclusive: Boolean = false) {
        findNavController()?.popBackStack(destinationId, inclusive)
    }

    protected fun unsupportedNavigation() {
        val navController = findNavController()
        val currentGraph = activity.getResourceName(navController?.graph?.id)
        val currentDestination = activity.getResourceName(navController?.currentDestination?.id)
        handleError(NavigationError.UnsupportedNavigationError(currentGraph, currentDestination))
    }

    private fun handleError(error: Throwable) {
        if (activity is BaseActivity<*>) {
            Timber.e(error)
            activity.displayError(error)
        } else {
            throw error
        }
    }
}
