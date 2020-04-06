package com.tydeya.familycircle.ui.planpart.eventmanager.eventviewpage

import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.EVENT_EDIT_PAGE_WORKING_MODE
import com.tydeya.familycircle.data.constants.NavigateConsts.*
import com.tydeya.familycircle.data.eventmanager.FamilyEvent
import com.tydeya.familycircle.data.eventmanager.WorkingMode
import com.tydeya.familycircle.domain.eventmanager.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventmanager.interactor.details.EventInteractor
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import kotlinx.android.synthetic.main.fragment_event_view_page.*
import java.util.*
import javax.inject.Inject

class EventViewFragment : Fragment(R.layout.fragment_event_view_page), EventInteractorCallback {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    @Inject
    lateinit var eventInteractor: EventInteractor

    private lateinit var id: String

    private var eventTimer: CountDownTimer? = null

    private var year: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)

        id = arguments!!.getString(BUNDLE_ID, "")
        year = arguments!!.getInt(BUNDLE_YEAR, 0)

        if (id != "") {
            val event = eventInteractor.getEventById(id)
            event?.let {
                setCurrentData(event)
            }
        }
    }

    /**
     * Current data setters
     * */

    private fun setCurrentData(familyEvent: FamilyEvent) {
        event_view_title.text = familyEvent.title
        setAuthorText(familyEvent)
        setTypeText(familyEvent)
        setDescriptionText(familyEvent)
        setToolbar(familyEvent)

        val calendar = GregorianCalendar()
        calendar.timeInMillis = familyEvent.timestamp
        calendar.set(Calendar.YEAR, year)

        val currentTimestamp = calendar.timeInMillis

        if (getCleanTodayDate().timeInMillis < currentTimestamp) {
            setTimer(currentTimestamp - Date().time)
        } else {
            setTimerStub(getCleanTodayDate().timeInMillis == currentTimestamp)
        }
    }

    private fun setAuthorText(familyEvent: FamilyEvent) {

        val familyAssistant = FamilyAssistantImpl(familyInteractor.actualFamily)

        val author = familyAssistant.getUserByPhone(familyEvent.authorPhone).description.name
                ?: context!!.resources.getString(R.string.unknown_text)

        event_view_author.text = context!!.resources
                .getString(R.string.event_view_author_placeholder, author)
    }

    private fun setTypeText(familyEvent: FamilyEvent) {

        val priority = context!!.resources
                .getStringArray(R.array.events_priority)[familyEvent.priority.ordinal]

        val type = context!!.resources
                .getStringArray(R.array.events_type)[familyEvent.type.ordinal]

        event_view_type.text = context!!.resources.getString(R.string.event_view_type_placeholder,
                priority, type)
    }

    private fun setDescriptionText(familyEvent: FamilyEvent) {
        val description = familyEvent.description
        if (description == "") {
            event_view_description_card.visibility = View.INVISIBLE
        } else {
            event_view_description_card.visibility = View.VISIBLE
            event_view_description_text.text = description
        }
    }

    private fun setTimer(timeLeft: Long) {
        event_view_timer_layout.visibility = View.VISIBLE
        event_view_stub_layout.visibility = View.GONE
        eventTimer = object : CountDownTimer(timeLeft, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val daysLeft = millisUntilFinished / (24 * 60 * 60 * 1000)
                val hoursLeft = (millisUntilFinished / (60 * 60 * 1000)) % 24
                val minutesLeft = (millisUntilFinished / 60000) % 60
                val secondsLeft = (millisUntilFinished / 1000) % 60

                event_view_timer_days?.text = daysLeft.toString()
                event_view_timer_hours?.text = hoursLeft.toString()
                event_view_timer_minutes?.text = minutesLeft.toString()
                event_view_timer_seconds?.text = secondsLeft.toString()
            }

            override fun onFinish() {

            }

        }
        eventTimer?.start()
    }

    private fun setTimerStub(isTodayEvent: Boolean) {
        event_view_timer_layout.visibility = View.GONE
        event_view_stub_layout.visibility = View.VISIBLE
        event_view_stub_text.text = if (isTodayEvent) {
            context!!.resources.getString(R.string.event_view_this_is_today_event)
        } else {
            context!!.resources.getString(R.string.event_view_this_event_has_already_passed)
        }
    }

    /**
     * Toolbar settings
     * */

    private fun setToolbar(familyEvent: FamilyEvent) {
        event_view_settings.visibility = if (familyEvent.authorPhone ==
                FirebaseAuth.getInstance().currentUser!!.phoneNumber) {
            View.VISIBLE
        } else {
            View.GONE
        }

        event_view_toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        event_view_settings.setOnClickListener {
            showPopUpSettingsMenu(it)
        }
    }

    private fun showPopUpSettingsMenu(v: View) {
        val popupMenu = PopupMenu(context, v)
        val menuInflater = popupMenu.menuInflater
        menuInflater.inflate(R.menu.settings_event_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.event_view_menu_edit -> {
                    val bundle = Bundle().apply {
                        putString(BUNDLE_EVENT_ID, id)
                        putInt(EVENT_EDIT_PAGE_WORKING_MODE, WorkingMode.EDIT.ordinal)
                    }

                    NavHostFragment.findNavController(this)
                            .navigate(R.id.eventEditFragment, bundle)

                    true
                }
                R.id.event_view_menu_delete -> {
                    eventInteractor.deleteEvent(id)
                    NavHostFragment.findNavController(this).popBackStack()

                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    /**
     * Events callbacks
     * */

    override fun eventDataFromServerUpdated() {
        val event = eventInteractor.getEventById(id)
        if (event != null) {
            setCurrentData(event)
        } else {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        eventInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        eventTimer?.cancel()
        eventInteractor.unsubscribe(this)

    }

    private fun getCleanTodayDate(): Calendar {
        val rawCalendar = GregorianCalendar()
        return GregorianCalendar(rawCalendar.get(Calendar.YEAR), rawCalendar.get(Calendar.MONTH),
                rawCalendar.get(Calendar.DAY_OF_MONTH))
    }

}
