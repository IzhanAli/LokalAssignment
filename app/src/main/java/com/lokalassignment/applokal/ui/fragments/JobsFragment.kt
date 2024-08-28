package com.lokalassignment.applokal.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.lokalassignment.applokal.R
import com.lokalassignment.applokal.adapter.JobAdapter
import com.lokalassignment.applokal.viewmodel.APIState
import com.lokalassignment.applokal.viewmodel.JobViewModel
import com.lokalassignment.applokal.viewmodel.SharedViewModelFactory

class JobsFragment : Fragment(R.layout.fragment_jobs) {

    private lateinit var viewModel: JobViewModel

    private lateinit var status: TextView
    private lateinit var recyclerView: RecyclerView
    lateinit var shimmer: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(JobViewModel::class.java)

        shimmer = view.findViewById(R.id.shimmer_view)
        status = view.findViewById(R.id.loadstatus)
        initList()
        observeViewModel()

    }

    private fun initList() {
        recyclerView = view?.findViewById(R.id.recycler_view)!!
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewModel.apiStatus.observe(viewLifecycleOwner) {
            when (it) {
                is APIState.Loading -> {
                    shimmer.startShimmer()
                    shimmer.visibility = View.VISIBLE
                    recyclerView.adapter = null
                    status.visibility = View.GONE
                }

                is APIState.Success -> {
                    status.visibility = View.GONE
                    shimmer.stopShimmer()
                    shimmer.visibility = View.GONE
                    recyclerView.adapter = JobAdapter(it.jobs,
                        { job ->
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


                }

                is APIState.Error -> {
                    shimmer.stopShimmer()
                    shimmer.visibility = View.GONE
                    status.visibility = View.VISIBLE
                    status.text = it.message
                    recyclerView.adapter = null
                }


            }

        }
    }
}