package com.example.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SecondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SecondFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("SecondFragment", "onCreateView")
        return inflater.inflate(R.layout.second_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("SecondFragment", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.e("SecondFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("SecondFragment", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.e("SecondFragment", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SecondFragment", "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("SecondFragment", "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("SecondFragment", "onDetach")
    }

    override fun onPause() {
        super.onPause()
        Log.e("SecondFragment", "onPause")
    }
}