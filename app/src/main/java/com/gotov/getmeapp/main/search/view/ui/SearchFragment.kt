package com.gotov.getmeapp.main.search.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentSearchBinding
import com.gotov.getmeapp.main.search.model.data.Skill
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.search.viewmodel.SearchViewModel
import com.gotov.getmeapp.ui.items.UsersViewAdapter
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.DiffUtilsCallback
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import com.gotov.getmeapp.utils.ui.toDips
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {
    companion object {
        private const val textSizeChip = 11F
        private const val heightChip = 48f
    }

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val searchViewModel by viewModel<SearchViewModel>()

    private var adapter: UsersViewAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycleView()
        startedUIProperty()
        runSkillsLifeCycle()
        runMentorsLifeCycle()

        setListeners()

        searchViewModel.getSkills()
        searchViewModel.getMentors()
    }

    private fun setListeners() {
        binding.searchSwipeLayout.setOnRefreshListener {
            searchViewModel.getMentors()
            binding.searchSwipeLayout.isRefreshing = true
        }
    }

    private fun setRecycleView() {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        binding.userList.layoutManager = layoutManager
        adapter?.let {
            binding.userList.adapter = it
        } ?: run {
            adapter = UsersViewAdapter(arrayListOf())
            binding.userList.adapter = adapter
        }
    }

    private fun startedUIProperty() {
        binding.searchPageEmptyResultString.visibility = View.GONE
        binding.loadSkillsList.visibility = View.VISIBLE
        binding.userList.visibility = View.GONE
        binding.mentorInfoSkillsList.visibility = View.GONE
        binding.searchSwipeLayout.isRefreshing = false
    }

    private fun runSkillsLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchViewModel.skills.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { skills ->
                            addSearchSkill(skills)
                            binding.loadSkillsList.visibility = View.GONE
                            binding.mentorInfoSkillsList.visibility = View.VISIBLE
                            searchViewModel.getMentors()
                        }
                    }
                    is Resource.Loading -> {
                        binding.loadSkillsList.visibility = View.VISIBLE
                        binding.searchSwipeLayout.isRefreshing = false
                        binding.userList.visibility = View.GONE
                        binding.mentorInfoSkillsList.visibility = View.GONE
                    }
                    is Resource.Error -> {
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

    private fun runMentorsLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchViewModel.mentors.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.searchPageEmptyResultString.visibility = View.GONE
                        if (it.data != null && adapter != null) {
                            val userDiffUtilCallback =
                                DiffUtilsCallback(
                                    adapter!!.getData(),
                                    it.data.toList(),
                                    { oldItem: User, newItem: User ->
                                        oldItem.id == newItem.id
                                    },
                                    { oldItem: User, newItem: User ->
                                        oldItem.firstName == newItem.firstName &&
                                                oldItem.lastName == newItem.lastName &&
                                                oldItem.isMentor == newItem.isMentor &&
                                                oldItem.about == newItem.about &&
                                                oldItem.skills === newItem.skills
                                    }
                                )
                            val productDiffResult = DiffUtil.calculateDiff(userDiffUtilCallback)

                            adapter!!.setData(it.data)
                            productDiffResult.dispatchUpdatesTo(adapter!!)
                        } else {
                            binding.searchPageEmptyResultString.visibility = View.VISIBLE
                        }

                        binding.searchSwipeLayout.isRefreshing = false
                        binding.userList.visibility = View.VISIBLE
                    }
                    is Resource.Loading -> {
                        binding.searchPageEmptyResultString.visibility = View.GONE
                        binding.searchSwipeLayout.isRefreshing = true
                        binding.userList.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка поиска менторов ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        binding.searchPageEmptyResultString.visibility = View.GONE
                        binding.searchSwipeLayout.isRefreshing = false
                    }
                    else -> {}
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun getChipSkill(skill: String, context: Context): Chip {
        val tmp = Chip(context)
        tmp.text = skill
        tmp.isCheckable = true
        tmp.isChecked = false
        tmp.setChipBackgroundColorResource(R.drawable.ic_custom_skill_chip_draw)
        tmp.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tmp.setTextColor(Color.WHITE)
        tmp.textSize = textSizeChip
        tmp.layoutParams =
            ChipGroup.LayoutParams(
                ChipGroup.LayoutParams.WRAP_CONTENT,
                (heightChip).toDips(resources)
            )

        tmp.setOnCheckedChangeListener { compoundButton, b ->
            searchViewModel.changeStateOfSkill(compoundButton.text.toString(), b)
            searchViewModel.getMentors()
        }

        return tmp
    }

    private fun addSearchSkill(skills: List<Skill>) {
        binding.mentorInfoSkillsListChips.removeAllViews()
        for (skill in skills) {
            binding.mentorInfoSkillsListChips.addView(
                getChipSkill(
                    skill.name,
                    binding.mentorInfoSkillsListChips.context
                )
            )
        }
    }
}
