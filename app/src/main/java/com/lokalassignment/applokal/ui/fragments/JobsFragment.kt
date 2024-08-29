package com.lokalassignment.applokal.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.lokalassignment.applokal.R
import com.lokalassignment.applokal.adapter.JobAdapter
import com.lokalassignment.applokal.adapter.JobPagingAdapter
import com.lokalassignment.applokal.databinding.FragmentJobsBinding
import com.lokalassignment.applokal.viewmodel.JobViewModel
import com.lokalassignment.applokal.viewmodel.SharedViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class JobsFragment : Fragment(R.layout.fragment_jobs) {

    private lateinit var viewModel: JobViewModel
    private lateinit var adapter: JobPagingAdapter

    private lateinit var status: TextView
    private lateinit var recyclerView: RecyclerView
    lateinit var shimmer: ShimmerFrameLayout

    private var _binding: FragmentJobsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(JobViewModel::class.java)
        initViews()
        observeViewModel()

    }

    private fun initViews() {
        recyclerView = view?.findViewById(R.id.recycler_view)!!
        recyclerView = binding.recyclerView
        status = binding.loadstatus
        shimmer = binding.shimmerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {


        adapter = JobPagingAdapter({ job ->
            //Log.i("TAG", "observeViewModel: $job")
            viewModel.selectJob(job)
            findNavController().navigate(R.id.action_jobsFragment_to_viewJobFragment)
        }, { j ->
            viewModel.saveJob(j)
        }, { jobId, callback ->
            viewModel.isJobSaved(jobId).observe(viewLifecycleOwner) { isSaved ->
                callback(isSaved)
            }
        })
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.jobFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }


        adapter.addLoadStateListener { loadState ->

            shimmer.visibility = if (loadState.refresh is LoadState.Loading) View.VISIBLE else View.GONE
            if (loadState.refresh is LoadState.Loading) status.visibility = View.GONE

            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            errorState?.let {
                shimmer.visibility = View.GONE
                status.text = it.error.localizedMessage
                status.visibility = View.VISIBLE}
        }


    }
}