// shared/src/commonMain/kotlin/com/example/shared/AuthManager.kt

expect class AuthManager() {
    fun signUpWithEmailAndPassword(email: String, password: String, callback: (Boolean, String?) -> Unit)
    fun signInWithEmailAndPassword(email: String, password: String, callback: (Boolean, String?) -> Unit)
}
