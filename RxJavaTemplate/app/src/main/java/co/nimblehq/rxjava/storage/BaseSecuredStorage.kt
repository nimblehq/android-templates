package co.nimblehq.rxjava.storage

interface BaseSecuredStorage {

    // TODO: For demo purpose only, replace with actual value that needs to be stored securely
    fun getTestId(): String?

    // TODO: For demo purpose only, replace with actual value that needs to be stored securely
    fun saveTestId(testId: String)

    fun clearAllData()
}
