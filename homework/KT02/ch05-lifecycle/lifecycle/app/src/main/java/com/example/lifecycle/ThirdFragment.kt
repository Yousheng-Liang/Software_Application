package com.example.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ThirdFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ThirdFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("ThirdFragment", "onCreateView")
        return inflater.inflate(R.layout.third_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("ThirdFragment", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.e("ThirdFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("ThirdFragment", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.e("ThirdFragment", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("ThirdFragment", "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("ThirdFragment", "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("ThirdFragment", "onDetach")
    }

    override fun onPause() {
        super.onPause()
        Log.e("ThirdFragment", "onPause")
    }
}