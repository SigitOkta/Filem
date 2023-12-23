package com.so.filem.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.so.filem.databinding.ActivityAuthBinding
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.MainActivity
import com.so.filem.ui.base.BaseViewModelActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity :
    BaseViewModelActivity<ActivityAuthBinding, AuthViewModel>(
        ActivityAuthBinding::inflate
    ) {

    private val TAG = AuthActivity::class.java.simpleName

    override val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeData()
    }

    // Google SSO with Firebase
    // Google Auth -> Token Session -> Register To Firebase -> Firebase User
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    Timber.tag(TAG).d("account %s", account)
                    account.idToken?.let { firebaseAuthWithGoogle(it) }
                } catch (e: ApiException) {
                    Timber.tag(TAG).w(e, "Google sign in failed")
                }
            }
        }

    private fun observeData() {
        viewModel.loginResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //nothing
                }
                is Resource.Error -> {
                    Toast.makeText(
                        this,
                        "Failed to Login : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    showLoadingState(false)
                }
                is Resource.Loading -> {
                    showLoadingState(true)
                }
                is Resource.Success -> {
                    showLoadingState(false)
                    if (it.payload?.second != null) {
                        navigateToHome()
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun showLoadingState(isShow: Boolean) {
        binding.pbLogin.isVisible = isShow
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        viewModel.authenticateGoogleLogin(idToken)
    }

    private fun initView() {
        supportActionBar?.hide()
        setLoginAction()
    }

    private fun setLoginAction() {
        binding.btnSignInGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        Timber.tag("TAG").d("signIn: %s", signInIntent)
        launcher.launch(signInIntent)
    }
}