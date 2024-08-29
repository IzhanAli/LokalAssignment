package com.lokalassignment.applokal.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.lokalassignment.applokal.R
import com.lokalassignment.applokal.models.JobDetail

class JobPagingAdapter(private val onItemClick: (JobDetail) -> Unit,
                       private val onSaveClick: (JobDetail) -> Unit,
                       private val isJobSaved: (Int, (Boolean) -> Unit) -> Unit) :
    PagingDataAdapter<JobDetail, JobPagingAdapter.JobViewHolder>(JOB_COMPARATOR) {

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val role = itemView.findViewById<TextView>(R.id.textView_title)
        val salary = itemView.findViewById<TextView>(R.id.textView_salary)
        val company = itemView.findViewById<TextView>(R.id.textView_company)
        val location = itemView.findViewById<TextView>(R.id.textView_location)
        val callbtn = itemView.findViewById<TextView>(R.id.button_call)
        val wa = itemView.findViewById<TextView>(R.id.button_wa)
        val img = itemView.findViewById<ImageView>(R.id.banner)
        val ui = itemView.findViewById<MaterialCardView>(R.id.cardparent)
        val save = itemView.findViewById<MaterialCheckBox>(R.id.btnsave)


        fun bind(job: JobDetail) {

            if (job.type != 1040) {

                img.visibility = View.GONE
                ui.visibility = View.VISIBLE

                role.text = job.jobRole
                salary.text = job.primaryDetails?.salary
                company.text = job.companyName
                location.text = job.primaryDetails?.place

                callbtn.setOnClickListener {
                    val i = Intent(Intent.ACTION_DIAL)
                    i.data = Uri.parse(job.customLink)
                    startActivity(itemView.context, i, null)
                }

                wa.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(job.contactPreference?.whatsappLink)
                    startActivity(itemView.context, i, null)
                }
                itemView.setOnClickListener {
                    onItemClick(job)
                }


                isJobSaved(job.id!!){ saved ->

                  save.isChecked = saved

                }


                save.setOnClickListener {
                    onSaveClick(job)
                }


            }else{
                ui.visibility = View.GONE
                Glide.with(itemView).load(job.creatives?.get(0)?.imageUrl).into(img)
                img.visibility = View.VISIBLE

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item_layout, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = getItem(position)
        job?.let { holder.bind(it) }
    }

    companion object {
        private val JOB_COMPARATOR = object : DiffUtil.ItemCallback<JobDetail>() {
            override fun areItemsTheSame(oldItem: JobDetail, newItem: JobDetail): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: JobDetail, newItem: JobDetail): Boolean {
                return oldItem == newItem
            }
        }
    }

}
