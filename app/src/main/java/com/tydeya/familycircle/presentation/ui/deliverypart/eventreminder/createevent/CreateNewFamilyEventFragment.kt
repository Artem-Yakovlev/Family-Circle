package com.tydeya.familycircle.presentation.ui.deliverypart.eventreminder.createevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.tydeya.familycircle.databinding.FragmentCreateNewFamilyEventBinding
import com.tydeya.familycircle.presentation.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class CreateNewFamilyEventFragment : Fragment() {

    private var _binding: FragmentCreateNewFamilyEventBinding? = null
    private val binding get() = _binding!!

    private val eventDateFormat get() = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
    private val eventTimeFormat get() = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateNewFamilyEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        initEventDatePickers()
    }

    private fun initEventDatePickers() {
        binding.eventTimeTypeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.firstTimePickerButton.visibility = View.GONE
                binding.secondTimePickerButton.visibility = View.GONE
            } else {
                binding.firstTimePickerButton.visibility = View.VISIBLE
                binding.secondTimePickerButton.visibility = View.VISIBLE
            }
            sortEventTimeInterval()
        }

        val actualCalendar = GregorianCalendar()

        // Date picker listener

        class EventDatePickerListener(
                private val listenerText: TextView
        ) : DatePickerDialog.OnDateSetListener {

            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                listenerText.text = eventDateFormat
                        .format(GregorianCalendar(year, month, dayOfMonth).time)
                sortEventTimeInterval()
            }
        }

        val datePickerButtonClickListener = View.OnClickListener {
            DatePickerDialog(requireContext(), EventDatePickerListener(it as TextView),
                    actualCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.MONTH),
                    actualCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Time picker listener

        class EventTimePickerListener(
                private val listenerText: TextView
        ) : TimePickerDialog.OnTimeSetListener {

            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                listenerText.text = eventTimeFormat.format(GregorianCalendar().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }.time)
                sortEventTimeInterval()
            }
        }

        val timePickerButtonClickListener = View.OnClickListener {
            TimePickerDialog(requireContext(), EventTimePickerListener(it as TextView),
                    actualCalendar.get(Calendar.HOUR_OF_DAY), actualCalendar.get(Calendar.MINUTE),
                    true).show()
        }

        val actualDateString = eventDateFormat.format(actualCalendar.time)
        val actualTimeString = eventTimeFormat.format(actualCalendar.time)

        binding.firstDatePickerButton.text = actualDateString
        binding.firstDatePickerButton.setOnClickListener(datePickerButtonClickListener)
        binding.firstTimePickerButton.text = actualTimeString
        binding.firstTimePickerButton.setOnClickListener(timePickerButtonClickListener)

        binding.secondDatePickerButton.text = actualDateString
        binding.secondDatePickerButton.setOnClickListener(datePickerButtonClickListener)
        binding.secondTimePickerButton.text = actualTimeString
        binding.secondTimePickerButton.setOnClickListener(timePickerButtonClickListener)

    }

    private fun sortEventTimeInterval() = lifecycleScope.launch(Dispatchers.Main) {

        var firstTimeString = binding.firstTimePickerButton.text
        var secondTimeString = binding.secondTimePickerButton.text

        if (binding.eventTimeTypeSwitch.isChecked) {
            firstTimeString = "00:00"
            secondTimeString = "00:00"
        }

        val firstDateAndTimeString = "${binding.firstDatePickerButton.text} $firstTimeString"

        val secondDateAndTimeString = "${binding.secondDatePickerButton.text} $secondTimeString"

        withContext(Dispatchers.Default) {

            val eventTimeAndDateFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm",
                    Locale.getDefault())
            val firstDate = eventTimeAndDateFormat.parse(firstDateAndTimeString) ?: Date()
            val secondDate = eventTimeAndDateFormat.parse(secondDateAndTimeString) ?: Date()

            if (firstDate.time > secondDate.time) {
                withContext(Dispatchers.Main) {

                    binding.firstDatePickerButton.text = binding.secondDatePickerButton.text
                            .also {
                                binding.secondDatePickerButton.text =
                                        binding.firstDatePickerButton.text
                            }

                    binding.firstTimePickerButton.text = binding.secondTimePickerButton.text
                            .also {
                                binding.secondTimePickerButton.text =
                                        binding.firstTimePickerButton.text
                            }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setBottomNavigationVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MainActivity).setBottomNavigationVisibility(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
