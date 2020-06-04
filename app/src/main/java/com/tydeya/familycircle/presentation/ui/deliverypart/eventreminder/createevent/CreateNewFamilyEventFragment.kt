package com.tydeya.familycircle.presentation.ui.deliverypart.eventreminder.createevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.*
import com.tydeya.familycircle.databinding.FragmentCreateNewFamilyEventBinding
import com.tydeya.familycircle.presentation.MainActivity
import com.tydeya.familycircle.utils.extensions.getUserPhone
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
    private val eventTimeAndDateFormat
        get() = SimpleDateFormat("E, dd MMM yyyy HH:mm",
                Locale.getDefault())

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
        initRepeatTypeSpinner()
        initCreateEventButton()
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

        var firstTimeString = binding.firstTimePickerButton.text.toString()
        var secondTimeString = binding.secondTimePickerButton.text.toString()

        if (binding.eventTimeTypeSwitch.isChecked) {
            firstTimeString = "00:00"
            secondTimeString = "00:00"
        }

        withContext(Dispatchers.Default) {

            val firstDate = getCalendarFromInputtedData(binding.firstDatePickerButton.text.toString(),
                    firstTimeString)
            val secondDate = getCalendarFromInputtedData(binding.secondDatePickerButton.text.toString(),
                    secondTimeString)

            if (firstDate.timeInMillis > secondDate.timeInMillis) {
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

    private fun getCalendarFromInputtedData(dateString: String, timeString: String): Calendar {
        return GregorianCalendar().apply {
            time = eventTimeAndDateFormat.parse("$dateString $timeString") ?: Date()
        }

    }

    private fun initRepeatTypeSpinner() {
        binding.repeatTypeSpinner.adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.event_reminder_repeat_types, R.layout.support_simple_spinner_dropdown_item)
    }

    private fun initCreateEventButton() {
        binding.createEventButton.setOnClickListener {
            if (binding.createEventInputTitle.text.toString() != "") {
                createEventFromInputtedData()
            } else {
                binding.createEventInputTitle.error = getString(R.string.empty_necessary_field_warning)
            }
        }
    }

    private fun createEventFromInputtedData() {
        val eventInformation = FamilyEvent.FamilyEventInformation(
                title = binding.createEventInputTitle.text.toString(),
                description = binding.createEventInputDescription.text.toString(),
                type = when(binding.eventTypeRadioGroup.checkedRadioButtonId) {
                    binding.radioBirthdate.id -> EventType.BIRTHDATE
                    binding.radioOrdinal.id -> EventType.ROUTINE
                    else -> EventType.IMPORTANT
                })

        val eventStyle = FamilyEvent.FamilyEventStyle(
                theme = when(binding.eventColorRadioGroup.checkedRadioButtonId) {
                    binding.radioColorDarkBlue.id -> EventStyleTheme.COLOR_DARK_BLUE
                    binding.radioColorLightBlue.id -> EventStyleTheme.COLOR_LIGHT_BLUE
                    binding.radioColorDarkGreen.id -> EventStyleTheme.COLOR_DARK_GREEN
                    binding.radioColorLightGreen.id -> EventStyleTheme.COLOR_LIGHT_GREEN
                    else -> EventStyleTheme.COLOR_ORANGE
                }
        )

        val eventAudience = FamilyEvent.FamilyEventAudience(
                author = getUserPhone()
        )

        val firstCalendar = getCalendarFromInputtedData(binding.firstDatePickerButton.text.toString(),
                binding.firstTimePickerButton.text.toString())

        val secondCalendar = getCalendarFromInputtedData(binding.secondDatePickerButton.text.toString(),
                binding.secondTimePickerButton.text.toString())

        if (binding.eventTimeTypeSwitch.isChecked) {
            firstCalendar.set(Calendar.HOUR_OF_DAY, 0)
            secondCalendar.set(Calendar.HOUR_OF_DAY, 0)
            firstCalendar.set(Calendar.MINUTE, 0)
            secondCalendar.set(Calendar.MINUTE, 0)
        }

        val eventTimeType = getEventTimeType(firstCalendar.timeInMillis, secondCalendar.timeInMillis,
                !binding.eventTimeTypeSwitch.isChecked)


        val eventTime = FamilyEvent.FamilyEventTime(firstCalendar = firstCalendar,
                secondCalendar = secondCalendar, timeType = eventTimeType,
                repeatType = EventRepeatType.values()[binding.repeatTypeSpinner.selectedItemPosition])

        val event = FamilyEvent("", eventInformation, eventTime, eventAudience, eventStyle)
    }

    private fun getEventTimeType(firstMillis: Long, secondMillis: Long, exactTime: Boolean) =
            if (firstMillis == secondMillis && exactTime) {
                EventTimeType.DATE_AND_TIME_WITHOUT_PERIOD
            } else if (firstMillis == secondMillis && !exactTime) {
                EventTimeType.ONLY_DATE_WITHOUT_PERIOD
            } else if (firstMillis != secondMillis && exactTime) {
                EventTimeType.DATE_AND_TIME_WITH_PERIOD
            } else {
                EventTimeType.ONLY_DATE_WITH_PERIOD
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
