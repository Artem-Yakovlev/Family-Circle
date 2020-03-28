package com.tydeya.familycircle.ui.planpart.eventreminder.eventviewpage

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tydeya.familycircle.App

import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.data.eventreminder.FamilyEventType
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.interactor.details.EventInteractor
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import kotlinx.android.synthetic.main.fragment_event_view_page.*
import java.time.Year
import java.util.*
import javax.inject.Inject

class EventViewPage : Fragment(R.layout.fragment_event_view_page), EventInteractorCallback {

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

        id = arguments!!.getString("id", "")
        year = arguments!!.getInt("year", 0)

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

        val calendar = GregorianCalendar()
        calendar.timeInMillis = familyEvent.timestamp
        calendar.set(Calendar.YEAR, year)

        val currentTimestamp = calendar.timeInMillis

        if (Date().time < currentTimestamp) {
            setTimer(currentTimestamp - Date().time)
        } else {
            setTimerStub()
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
        eventTimer = object: CountDownTimer(timeLeft, 1000) {

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

    private fun setTimerStub() {

    }

    /**
     * Events callbacks
     * */

    override fun eventDataFromServerUpdated() {
        val event = eventInteractor.getEventById(id)
        event?.let {
            setCurrentData(event)
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

}
