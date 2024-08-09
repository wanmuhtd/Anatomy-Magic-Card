package com.anatomy.magic.card

// HomepageFragment.kt
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class HomepageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.homepage, container, false)

        val scannerBanner = view.findViewById<ImageView>(R.id.scannerBanner)
        val quizBanner = view.findViewById<ImageView>(R.id.quizBanner)

        scannerBanner.setOnClickListener{
            (activity as MainActivity).openCameraActivity()
        }

        return view
    }
}

