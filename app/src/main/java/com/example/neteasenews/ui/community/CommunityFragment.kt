package com.example.neteasenews.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.neteasenews.databinding.FragmentCommunityBinding
import com.example.neteasenews.databinding.FragmentHomeBinding
import com.example.neteasenews.ui.mine.MineFragment


class CommunityFragment : Fragment() {


    val viewModel by lazy { ViewModelProvider(requireActivity()).get(CommunityViewModel::class.java) }

    private var _binding: FragmentCommunityBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = CommunityFragment()
    }

}