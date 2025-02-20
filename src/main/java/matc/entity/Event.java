package matc.entity;

import jakarta.persistence.*;

/**
 * The type Event.
 */
@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "e_id")
    private int eventId;
    @Column(name = "e_name")
    private String eventName;
    @Column(name = "e_location_street")
    private String eventLocationStreet;
    @Column(name = "e_location_city")
    private String eventLocationCity;
    @Column(name = "e_location_state")
    private String eventLocationState;
    @Column(name = "e_location_zip")
    private String eventLocationZip;
    @Column(name = "e_date_start")
    private String eventDateTimeStart;
    @Column(name = "e_date_end")
    private String eventDateTimeEnd;

    /**
     * Instantiates a new Event.
     */
    public Event() {
    }

    /**
     * Instantiates a new Event.
     *
     * @param eventId             the event id
     * @param eventName           the event name
     * @param eventLocationStreet the event location street
     * @param eventLocationCity   the event location city
     * @param eventLocationState  the event location state
     * @param eventLocationZip    the event location zip
     * @param eventDateTimeStart  the event date time start
     * @param eventDateTimeEnd    the event date time end
     */
    public Event(int eventId, String eventName, String eventLocationStreet, String eventLocationCity, String eventLocationState, String eventLocationZip, String eventDateTimeStart, String eventDateTimeEnd) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocationStreet = eventLocationStreet;
        this.eventLocationCity = eventLocationCity;
        this.eventLocationState = eventLocationState;
        this.eventLocationZip = eventLocationZip;
        this.eventDateTimeStart = eventDateTimeStart;
        this.eventDateTimeEnd = eventDateTimeEnd;
    }

    /**
     * Gets event id.
     *
     * @return the event id
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Sets event id.
     *
     * @param eventId the event id
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Gets event name.
     *
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets event name.
     *
     * @param eventName the event name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets event location street.
     *
     * @return the event location street
     */
    public String getEventLocationStreet() {
        return eventLocationStreet;
    }

    /**
     * Sets event location street.
     *
     * @param eventLocationStreet the event location street
     */
    public void setEventLocationStreet(String eventLocationStreet) {
        this.eventLocationStreet = eventLocationStreet;
    }

    /**
     * Gets event location city.
     *
     * @return the event location city
     */
    public String getEventLocationCity() {
        return eventLocationCity;
    }

    /**
     * Sets event location city.
     *
     * @param eventLocationCity the event location city
     */
    public void setEventLocationCity(String eventLocationCity) {
        this.eventLocationCity = eventLocationCity;
    }

    /**
     * Gets event location state.
     *
     * @return the event location state
     */
    public String getEventLocationState() {
        return eventLocationState;
    }

    /**
     * Sets event location state.
     *
     * @param eventLocationState the event location state
     */
    public void setEventLocationState(String eventLocationState) {
        this.eventLocationState = eventLocationState;
    }

    /**
     * Gets event location zip.
     *
     * @return the event location zip
     */
    public String getEventLocationZip() {
        return eventLocationZip;
    }

    /**
     * Sets event location zip.
     *
     * @param eventLocationZip the event location zip
     */
    public void setEventLocationZip(String eventLocationZip) {
        this.eventLocationZip = eventLocationZip;
    }

    /**
     * Gets event date time start.
     *
     * @return the event date time start
     */
    public String getEventDateTimeStart() {
        return eventDateTimeStart;
    }

    /**
     * Sets event date time start.
     *
     * @param eventDateTimeStart the event date time start
     */
    public void setEventDateTimeStart(String eventDateTimeStart) {
        this.eventDateTimeStart = eventDateTimeStart;
    }

    /**
     * Gets event date time end.
     *
     * @return the event date time end
     */
    public String getEventDateTimeEnd() {
        return eventDateTimeEnd;
    }

    /**
     * Sets event date time end.
     *
     * @param eventDateTimeEnd the event date time end
     */
    public void setEventDateTimeEnd(String eventDateTimeEnd) {
        this.eventDateTimeEnd = eventDateTimeEnd;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventLocationStreet='" + eventLocationStreet + '\'' +
                ", eventLocationCity='" + eventLocationCity + '\'' +
                ", eventLocationState='" + eventLocationState + '\'' +
                ", eventLocationZip='" + eventLocationZip + '\'' +
                ", eventDateTimeStart='" + eventDateTimeStart + '\'' +
                ", eventDateTimeEnd='" + eventDateTimeEnd + '\'' +
                '}';
    }
}


