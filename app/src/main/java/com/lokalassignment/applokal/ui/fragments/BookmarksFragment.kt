package com.lokalassignment.applokal.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lokalassignment.applokal.R
import com.lokalassignment.applokal.adapter.JobAdapter
import com.lokalassignment.applokal.models.JobDetail
import com.lokalassignment.applokal.ui.MainActivity
import com.lokalassignment.applokal.viewmodel.JobViewModel
import com.lokalassignment.applokal.viewmodel.SharedViewModelFactory

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {
    private lateinit var viewModel: JobViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeholder: LinearLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(JobViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler)
        placeholder = view.findViewById(R.id.placeholderlist)
        placeholder.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        observe();



    }



    private fun observe() {
        viewModel.saved.observeForever { jobs ->
            recyclerView.adapter?.notifyDataSetChanged()

            if (jobs != null) {
                recyclerView.adapter = JobAdapter(jobs.toList(), { job ->
                    viewModel.selectJob(job)
                    findNavController().navigate(R.id.action_bookmarksFragment_to_viewJobFragment)
                }, { j ->
                    viewModel.saveJob(j)
                }, { jobId, callback ->
                    viewModel.isJobSaved(jobId).observe(viewLifecycleOwner) { isSaved ->
                        callback(isSaved)
                    }
                })
                recyclerView.visibility = View.VISIBLE
                placeholder.visibility = View.GONE

            }else{
                recyclerView.visibility = View.GONE
                placeholder.visibility = View.VISIBLE
            }
        }
    }




}