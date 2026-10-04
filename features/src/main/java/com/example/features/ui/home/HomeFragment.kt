package com.example.features.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.core.ui.base.BaseFragment
import com.example.features.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLanguage()
    }

    private fun setLanguage() {
        binding.switchLanguage.isChecked =
            when (resources.configuration.locales[0].language) {
                BAHASA_LANGUAGE -> true
                else -> false
            }

        binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(BAHASA_LANGUAGE)
                AppCompatDelegate.setApplicationLocales(appLocale)
            } else {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(ENGLISH_LANGUAGE)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
        }
    }

    companion object {
        const val BAHASA_LANGUAGE = "in"
        const val ENGLISH_LANGUAGE = "en"
    }

}