package com.gotov.getmeapp.main.plans.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentPlansPageBinding
import com.gotov.getmeapp.main.plans.model.data.Menti
import com.gotov.getmeapp.main.plans.model.data.Plan
import com.gotov.getmeapp.main.plans.view.items.MentisViewAdapter
import com.gotov.getmeapp.main.plans.view.items.PlansViewAdapter
import com.gotov.getmeapp.main.plans.viewmodel.PlansViewModel
import com.gotov.getmeapp.sign.signup.view.ui.ContinueRegisterDialogFragment
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.DiffUtilsCallback
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlansFragment : Fragment(R.layout.fragment_plans_page) {
    companion object {
        private const val applyUserInfo = "Вы приняли менти, теперь можете заполнить план"
    }

    private val binding by viewBinding(FragmentPlansPageBinding::bind)

    private val plansViewModel by viewModel<PlansViewModel>()

    private var isMentor: Boolean? = null
    private var adapterPlans: PlansViewAdapter? = null
    private var adapterMentis: MentisViewAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPlansRecycleView()
        setMentisRecycleView()

        runLifeCycles()
        setStartParam()

        setEventListeners()

        plansViewModel.getMentis()
    }

    private fun setStartParam() {
        binding.plansPageInfoTitle.visibility = View.GONE
        val status = arguments?.getString("status")
        if (status == "menti") {
            mentiPage()
            plansViewModel.getPlansAsMenti()
        } else {
            plansViewModel.getIsMentor()
        }
    }

    private fun setPlansRecycleView() {
        binding.planList.layoutManager = LinearLayoutManager(context)
        adapterPlans?.let {
            binding.planList.adapter = it
        } ?: run {
            adapterPlans = PlansViewAdapter(arrayListOf())
            {
                findNavController().navigate(
                    R.id.action_PlansFragment_to_PlanFragment,
                    bundleOf("plan_id" to it.id)
                )
            }
            binding.planList.adapter = adapterPlans
        }
    }

    private fun setMentisRecycleView() {
        binding.mentiList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapterMentis?.let {
            binding.mentiList.adapter = it
        } ?: run {
            adapterMentis = MentisViewAdapter(
                arrayListOf(),
                { menti ->
                    val newFragment = NewPlanDialogFragment(menti.offerId, plansViewModel)
                    newFragment.show(childFragmentManager, "apply")
                },
                { menti ->
                    plansViewModel.cancelMenti(menti.offerId)
                }
            )
            binding.mentiList.adapter = adapterMentis
        }
    }

    private fun setEventListeners() {
        binding.radioMenti.setOnClickListener {
            if (isMentor == null) {
                plansViewModel.getIsMentor()
            } else {
                if (isMentor == true) {
                    binding.mentiList.visibility = View.VISIBLE
                    binding.dividerBetweenMentiAndPlan.visibility = View.VISIBLE
                    binding.plansPageInfoTitle.visibility = View.GONE
                    plansViewModel.getMentis()
                    plansViewModel.getPlansAsMentor()
                } else {
                    binding.plansPageInfoTitle.visibility = View.VISIBLE
                }
            }
        }

        binding.radioMentor.setOnClickListener {
            plansViewModel.getPlansAsMenti()
            binding.plansPageInfoTitle.visibility = View.GONE
            binding.mentiList.visibility = View.GONE
            binding.dividerBetweenMentiAndPlan.visibility = View.GONE
        }

        binding.plansPageSwipeLayout.setOnRefreshListener {
            if (binding.radioMentor.isChecked) {
                plansViewModel.getPlansAsMenti()
            } else {
                plansViewModel.getMentis()
                plansViewModel.getPlansAsMentor()
            }
            binding.plansPageSwipeLayout.isRefreshing = true
        }
    }

    private fun runLifeCycles() {
        runIsMentorLifeCycle()
        runMentisLifeCycle()
        runPlansLifeCycle()
        runNewPlanLifeCycle()
    }

    private fun runIsMentorLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            plansViewModel.isMentor.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data == true) {
                            isMentor = true
                            mentorPage()
                            plansViewModel.getMentis()
                            plansViewModel.getPlansAsMentor()
                        } else {
                            isMentor = false
                            mentiPage()
                            plansViewModel.getPlansAsMenti()
                        }
                        binding.plansPageSwipeLayout.isRefreshing = false
                    }
                    is Resource.Loading -> {
                        binding.plansPageSwipeLayout.isRefreshing = false
                    }
                    is Resource.Error -> {
                        binding.plansPageSwipeLayout.isRefreshing = false
                        displayToastOnTop(
                            context,
                            "Произошла ошибка загрузки страницы ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runNewPlanLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            plansViewModel.newPlan.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { plan ->
                            displayToastOnTop(
                                context,
                                applyUserInfo,
                                Toast.LENGTH_SHORT
                            )
                            findNavController().navigate(
                                R.id.action_PlansFragment_to_PlanFragment,
                                bundleOf("plan_id" to plan.id)
                            )
                        }
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка принятия менти ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runMentisLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            plansViewModel.mentis.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data != null && adapterMentis != null) {
                            val userDiffUtilCallback =
                                DiffUtilsCallback(
                                    adapterMentis!!.getData(),
                                    it.data.toList(),
                                    { oldItem: Menti, newItem: Menti ->
                                        oldItem.offerId == newItem.offerId
                                    },
                                    { oldItem: Menti, newItem: Menti ->
                                        oldItem.lastName == newItem.lastName &&
                                                oldItem.firstName == newItem.firstName &&
                                                oldItem.isMentor == newItem.isMentor &&
                                                oldItem.avatar == newItem.avatar &&
                                                oldItem.about === newItem.about
                                    }
                                )
                            val productDiffResult = DiffUtil.calculateDiff(userDiffUtilCallback)

                            adapterMentis!!.setData(it.data)
                            productDiffResult.dispatchUpdatesTo(adapterMentis!!)
                        }
                        binding.plansPageSwipeLayout.isRefreshing = false
                    }
                    is Resource.Loading -> {
                        binding.plansPageSwipeLayout.isRefreshing = true
                    }
                    is Resource.Error -> {
                        binding.plansPageSwipeLayout.isRefreshing = false
                        displayToastOnTop(
                            context,
                            "Произошла ошибка получения менти ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runPlansLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            plansViewModel.plans.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data != null && adapterPlans != null) {
                            val userDiffUtilCallback =
                                DiffUtilsCallback(
                                    adapterPlans!!.getData(),
                                    it.data.toList(),
                                    { oldItem: Plan, newItem: Plan ->
                                        oldItem.id == newItem.id
                                    },
                                    { oldItem: Plan, newItem: Plan ->
                                        oldItem.title == newItem.title &&
                                                oldItem.description == newItem.description &&
                                                oldItem.progress == newItem.progress &&
                                                oldItem.mentiId == newItem.mentiId &&
                                                oldItem.skills === newItem.skills
                                    }
                                )
                            val productDiffResult = DiffUtil.calculateDiff(userDiffUtilCallback)

                            adapterPlans!!.setData(it.data)
                            productDiffResult.dispatchUpdatesTo(adapterPlans!!)
                        }

                        binding.plansPageSwipeLayout.isRefreshing = false
                    }
                    is Resource.Loading -> {
                        binding.plansPageSwipeLayout.isRefreshing = true
                    }
                    is Resource.Error -> {
                        binding.plansPageSwipeLayout.isRefreshing = false
                        displayToastOnTop(
                            context,
                            "Произошла ошибка получения планов ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    private fun mentiPage() {
        binding.radioMenti.isChecked = false
        binding.radioMentor.isChecked = true
        binding.mentiList.visibility = View.GONE
        binding.dividerBetweenMentiAndPlan.visibility = View.GONE
    }

    private fun mentorPage() {
        binding.radioMenti.isChecked = true
        binding.radioMentor.isChecked = false
        binding.mentiList.visibility = View.VISIBLE
        binding.dividerBetweenMentiAndPlan.visibility = View.VISIBLE
    }
}
