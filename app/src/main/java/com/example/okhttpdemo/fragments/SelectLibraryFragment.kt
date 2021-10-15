package com.example.okhttpdemo.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.okhttpdemo.R

class SelectLibraryFragment : Fragment(R.layout.fragment_select_library) {

    companion object {
        const val APP_PREFERENCES = "mysettings"
        const val APP_PREFERENCES_IS_RETROFIT_SELECTED = "IsRetrofitSelected"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rbRetrofit = view.findViewById<RadioButton>(R.id.radiobutton_retrofit_lib)
        val rbOkHttp = view.findViewById<RadioButton>(R.id.radiobutton_okHttp_lib)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radio_group)

        val sharedPreferences = context?.getSharedPreferences(
            APP_PREFERENCES, Context.MODE_PRIVATE
        )

        val isRetrofitInSharedPrefs = sharedPreferences?.getBoolean(
            APP_PREFERENCES_IS_RETROFIT_SELECTED, false
        ) ?: false

        if (isRetrofitInSharedPrefs) {
            radioGroup.check(rbRetrofit.id)
        } else {
            radioGroup.check(rbOkHttp.id)
        }

        radioGroup.setOnCheckedChangeListener { _, id ->
            val isRetrofitSelected = id == rbRetrofit.id
            sharedPreferences?.edit()
                ?.putBoolean(APP_PREFERENCES_IS_RETROFIT_SELECTED, isRetrofitSelected)
                ?.apply()
        }
    }
}