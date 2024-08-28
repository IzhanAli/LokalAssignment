package com.lokalassignment.applokal.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lokalassignment.applokal.R
import com.lokalassignment.applokal.databinding.FragmentJobViewBinding
import com.lokalassignment.applokal.models.ContentV3
import com.lokalassignment.applokal.models.Field
import com.lokalassignment.applokal.ui.MainActivity
import com.lokalassignment.applokal.viewmodel.JobViewModel
import com.lokalassignment.applokal.viewmodel.SharedViewModelFactory

class ViewJobFragment : Fragment(R.layout.fragment_job_view) {


    private var _binding: FragmentJobViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: JobViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJobViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), SharedViewModelFactory(requireActivity().application)).get(JobViewModel::class.java)

        binding.backAction.setOnClickListener(){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        viewModel.selectedJob.observe(viewLifecycleOwner){
            Log.d("ViewJobFragment", "Job: $it")
            binding.textViewTitle.text = it.jobRole
            binding.textViewSalary.text = it.primaryDetails?.salary
            binding.textViewCompany.text = it.companyName
            binding.textViewLocation.text = it.primaryDetails?.place
            binding.openingsCount.text = it.openingsCount.toString()
            binding.jobCategory.text = it.jobCategory
            binding.views.text = it.views
            binding.Experience.text = it.primaryDetails?.experience
            binding.Qualification.text = it.primaryDetails?.qualification
            binding.jobHours.text = it.jobHours
            binding.title.text = it.title
            binding.otherDetails.text = it.otherDetails
            binding.expire.text = it.expireOn?.substring(0,10) ?: "NA"
            val content: List<Field> = it.content!!.V3

            for (field in content){
                if (field.fieldKey == "Gender") binding.Gender.text = field.fieldValue
                else if (field.fieldKey == "Shift timing") binding.ShiftTiming.text = field.fieldValue
            }

            binding.buttonCall.setOnClickListener {v->
                val i = Intent(Intent.ACTION_DIAL)
                i.data = Uri.parse(it.customLink)
                startActivity(i)
            }

            binding.buttonWa.setOnClickListener {v->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(it.contactPreference?.whatsappLink)
                startActivity(i)
            }


        }



    }
}