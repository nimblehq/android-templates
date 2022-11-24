package co.nimblehq.template.compose.ui.base

/**
 * An interface provide abstract commitments for the implemented class
 * from [BaseFragment], with the [BaseViewModel]
 *
 * These methods are set to go well with the Lifecycle of the [BaseFragment],
 * so developers don't have to worry about the basic setups,
 * which could produce conflicts with the fragment's lifecycle
 *
 * See more detail in each function.
 */
interface BaseFragmentCallbacks {

    /**
     * The initial callback where you want to place your initialize functions that trigger
     * the setup block for the ViewModel.
     *
     * This method usually get called only ONCE during the time the Fragment is created.
     * Ideally, you would want to place the network calls, api requests in here.
     *
     * This is called right after [BaseFragment.onCreate] so we should NOT implement or place
     * view events functions here.
     */
    fun initViewModel()

    /**
     * The initial callback where you want to place your view events functions.
     *
     * This method usually get called multiple times, whenever the Fragment view is being created/re-created.
     * Ideally, you would want to setup the data binding from ViewModel to View here.
     *
     * This is called right after [BaseFragment.onViewCreated]
     */
    fun bindViewModel()
}
