package com.example.okhttpdemo.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.okhttpdemo.R

class MainFragment : Fragment(R.layout.fragment_main_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSendRequest = view.findViewById<Button>(R.id.button_send_request)
        btnSendRequest.setOnClickListener {
            parentFragmentManager.commit {
                addToBackStack("SendRequestFragment")
                replace<SendRequestFragment>(R.id.fragment_container_view)
            }
        }

        val btnSelectLibrary = view.findViewById<Button>(R.id.button_select_library)
        btnSelectLibrary.setOnClickListener {
            parentFragmentManager.commit {
                addToBackStack("SelectLibraryFragment")
                replace<SelectLibraryFragment>(R.id.fragment_container_view)
            }
        }
    }
}