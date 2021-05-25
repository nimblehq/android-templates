package co.nimblehq.coroutine.ui.base

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import co.nimblehq.coroutine.extension.getResourceName
import timber.log.Timber

interface BaseNavigator {

    val navHostFragmentId: Int

    fun findNavController(): NavController?

    fun navigate(event: NavigationEvent)

    fun navigateUp()
}

abstract class BaseNavigatorImpl(
    protected val fragment: Fragment
) : BaseNavigator {

    private var navController: NavController? = null

    override fun findNavController(): NavController? {
        return navController ?: try {
            fragment.findNavController().apply {
                navController = this
            }
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
        val currentGraph = fragment.requireActivity().getResourceName(navController?.graph?.id)
        val currentDestination =
            fragment.requireActivity().getResourceName(navController?.currentDestination?.id)
        handleError(
            NavigationException.UnsupportedNavigationException(
                currentGraph,
                currentDestination
            )
        )
    }

    protected fun NavController.navigateToDestinationByDeepLink(
        destinationId: Int,
        bundle: Parcelable? = null
    ) {
        createDeepLink()
            .setDestination(destinationId).apply {
                bundle?.let {
                    setArguments(Bundle().apply {
                        putParcelable("bundle", bundle)
                    })
                }
            }
            .createPendingIntent()
            .send()
    }

    private fun handleError(error: Throwable) {
        if (fragment is BaseFragment<*>) {
            Timber.e(error)
            fragment.displayError(error)
        } else {
            throw error
        }
    }
}
