package com.so.filem.ui.profile

import android.content.Intent
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.so.filem.R
import com.so.filem.base.BaseViewModelFragment
import com.so.filem.databinding.FragmentProfileBinding
import com.so.filem.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ProfileFragment : BaseViewModelFragment<FragmentProfileBinding, ProfileViewModel>(
    FragmentProfileBinding::inflate
) {
    override val viewModel: ProfileViewModel by viewModels()
    private val dialogLogout by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.logout_text))
            .setNegativeButton(getString(R.string.lbl_no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl_yes) { dialog, _ ->
                logout()
                dialog.dismiss()
            }
    }

    private fun logout() {
        viewModel.doLogout()
        navigateToLogin()

    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
    override fun initView() {
        super.initView()
        (activity as AppCompatActivity).supportActionBar?.hide()
        val switchTheme = viewBinding().swDarkMode
        viewModel.getCurrentUser()
        viewBinding().btnLogout.setOnClickListener {
            dialogLogout.show()
        }
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }

    }

    override fun observeData() {
        super.observeData()
        viewModel.currentUserLiveData.observe(this) { user ->
            Timber.tag("Setting, SettingFragment").d(user?.displayName)
            if (user != null) {
                Timber
                viewBinding().tvName.text = user.displayName
                viewBinding().tvEmail.text = user.email
                viewBinding().ciImage.load(user.photoProfileUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_poster)
                }
            }
        }
    }

}