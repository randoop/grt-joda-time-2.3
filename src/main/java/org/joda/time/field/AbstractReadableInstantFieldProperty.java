/*
 *  Copyright 2001-2005 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.time.field;

import org.checkerframework.dataflow.qual.Impure;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import java.io.Serializable;
import java.util.Locale;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DurationField;
import org.joda.time.Interval;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

/**
 * AbstractReadableInstantFieldProperty is a base class for binding a
 * ReadableInstant to a DateTimeField.
 * <p>
 * It allows the date and time manipulation code to be field based yet
 * still easy to use.
 * <p>
 * AbstractReadableInstantFieldProperty itself is thread-safe and immutable,
 * but the ReadableInstant being operated on may be mutable and not
 * thread-safe.
 *
 * @author Stephen Colebourne
 * @author Brian S O'Neill
 * @author Mike Schrag
 * @since 1.0
 */
public abstract class AbstractReadableInstantFieldProperty implements Serializable {

    /** Serialization version. */
    private static final long serialVersionUID = 1971226328211649661L;

    /**
     * Constructor.
     */
    @SideEffectFree
    public AbstractReadableInstantFieldProperty() {
        super();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the field being used.
     * 
     * @return the field
     */
    @Pure
    public abstract DateTimeField getField();

    /**
     * Gets the field type being used.
     * 
     * @return the field type
     */
    @Pure
    @Impure
    public DateTimeFieldType getFieldType() {
        return getField().getType();
    }

    /**
     * Gets the name of the field.
     * 
     * @return the field name
     */
    @Pure
    @Impure
    public String getName() {
        return getField().getName();
    }

    /**
     * Gets the milliseconds of the datetime that this property is linked to.
     * 
     * @return the milliseconds
     */
    @Pure
    @Impure
    protected abstract long getMillis();

    /**
     * Gets the chronology of the datetime that this property is linked to.
     * <p>
     * This implementation throws UnsupportedOperationException, and must be
     * implemented by subclasses to enable the equals() and hashCode() methods.
     * 
     * @return the chronology
     * @since 1.4
     */
    @Pure
    @Impure
    protected Chronology getChronology() {
        throw new UnsupportedOperationException(
                "The method getChronology() was added in v1.4 and needs " +
                "to be implemented by subclasses of AbstractReadableInstantFieldProperty");
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value of this property from the instant.
     * <p>
     * For example, the following two lines of code are equivalent:
     * <pre>
     * datetime.getDayOfMonth();
     * datetime.dayOfMonth().get();
     * </pre>
     * 
     * @return the current value
     * @see DateTimeField#get
     */
    @Impure
    public int get() {
        return getField().get(getMillis());
    }

    /**
     * Gets the value of this property from the instant as a string.
     * <p>
     * This method returns the value converted to a <code>String</code>
     * using <code>Integer.toString</code>. This method does NOT return
     * textual descriptions such as 'Monday' or 'January'.
     * See {@link #getAsText()} and {@link #getAsShortText()} for those.
     * 
     * @return the current value
     * @see DateTimeField#get
     * @since 1.1
     */
    @Impure
    public String getAsString() {
        return Integer.toString(get());
    }

    /**
     * Gets the textual value of this property from the instant as a
     * string in the default locale.
     * <p>
     * This method returns the value converted to a <code>String</code>
     * returning the appropriate textual description wherever possible.
     * Thus, a day of week of 1 would return 'Monday' in English.
     * 
     * @return the current text value
     * @see DateTimeField#getAsText
     */
    @Impure
    public String getAsText() {
        return getAsText(null);
    }

    /**
     * Gets the textual value of this property from the instant as a
     * string in the specified locale.
     * <p>
     * This method returns the value converted to a <code>String</code>
     * returning the appropriate textual description wherever possible.
     * Thus, a day of week of 1 would return 'Monday' in English.
     * 
     * @param locale  locale to use for selecting a text symbol, null means default
     * @return the current text value
     * @see DateTimeField#getAsText
     */
    @Impure
    public String getAsText(Locale locale) {
        return getField().getAsText(getMillis(), locale);
    }

    /**
     * Gets the short textual value of this property from the instant as a
     * string in the default locale.
     * <p>
     * This method returns the value converted to a <code>String</code>
     * returning the appropriate textual description wherever possible.
     * Thus, a day of week of 1 would return 'Mon' in English.
     * 
     * @return the current text value
     * @see DateTimeField#getAsShortText
     */
    @Impure
    public String getAsShortText() {
        return getAsShortText(null);
    }

    /**
     * Gets the short textual value of this property from the instant as a
     * string in the specified locale.
     * <p>
     * This method returns the value converted to a <code>String</code>
     * returning the appropriate textual description wherever possible.
     * Thus, a day of week of 1 would return 'Mon' in English.
     * 
     * @param locale  locale to use for selecting a text symbol, null means default
     * @return the current text value
     * @see DateTimeField#getAsShortText
     */
    @Impure
    public String getAsShortText(Locale locale) {
        return getField().getAsShortText(getMillis(), locale);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the difference between this field property instant and the one
     * passed in, in the units of this field. The sign of the difference
     * matches that of compareTo. In other words, this field property's instant
     * is the minuend.
     *
     * @param instant  the subtrahend, null means now
     * @return the difference in the units of this field
     * @see DateTimeField#getDifference
     */
    @Impure
    public int getDifference(ReadableInstant instant) {
        if (instant == null) {
            return getField().getDifference(getMillis(), DateTimeUtils.currentTimeMillis());
        }
        return getField().getDifference(getMillis(), instant.getMillis());
    }

    /**
     * Returns the difference between this field property instant and the one
     * passed in, in the units of this field. The sign of the difference
     * matches that of compareTo. In other words, this field property's instant
     * is the minuend.
     *
     * @param instant  the subtrahend, null means now
     * @return the difference in the units of this field
     * @see DateTimeField#getDifference
     */
    @Impure
    public long getDifferenceAsLong(ReadableInstant instant) {
        if (instant == null) {
            return getField().getDifferenceAsLong(getMillis(), DateTimeUtils.currentTimeMillis());
        }
        return getField().getDifferenceAsLong(getMillis(), instant.getMillis());
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the duration per unit value of this field. For example, if this
     * field represents "hour of day", then the duration is an hour.
     *
     * @return the duration of this field, or UnsupportedDurationField
     */
    @Impure
    public DurationField getDurationField() {
        return getField().getDurationField();
    }

    /**
     * Returns the range duration of this field. For example, if this field
     * represents "hour of day", then the range duration is a day.
     *
     * @return the range duration of this field, or null if field has no range
     */
    @Impure
    public DurationField getRangeDurationField() {
        return getField().getRangeDurationField();
    }

    /**
     * Gets whether this field is leap.
     * 
     * @return true if a leap field
     * @see DateTimeField#isLeap
     */
    @Impure
    public boolean isLeap() {
        return getField().isLeap(getMillis());
    }

    /**
     * Gets the amount by which this field is leap.
     * 
     * @return the amount by which the field is leap
     * @see DateTimeField#getLeapAmount
     */
    @Impure
    public int getLeapAmount() {
        return getField().getLeapAmount(getMillis());
    }

    /**
     * If this field were to leap, then it would be in units described by the
     * returned duration. If this field doesn't ever leap, null is returned.
     */
    @Impure
    public DurationField getLeapDurationField() {
        return getField().getLeapDurationField();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the minimum value for the field ignoring the current time.
     * 
     * @return the minimum value
     * @see DateTimeField#getMinimumValue
     */
    @Impure
    public int getMinimumValueOverall() {
        return getField().getMinimumValue();
    }

    /**
     * Gets the minimum value for the field.
     * 
     * @return the minimum value
     * @see DateTimeField#getMinimumValue
     */
    @Impure
    public int getMinimumValue() {
        return getField().getMinimumValue(getMillis());
    }

    /**
     * Gets the maximum value for the field ignoring the current time.
     * 
     * @return the maximum value
     * @see DateTimeField#getMaximumValue
     */
    @Impure
    public int getMaximumValueOverall() {
        return getField().getMaximumValue();
    }

    /**
     * Gets the maximum value for the field.
     * 
     * @return the maximum value
     * @see DateTimeField#getMaximumValue
     */
    @Impure
    public int getMaximumValue() {
        return getField().getMaximumValue(getMillis());
    }

    /**
     * Gets the maximum text length for the field.
     * 
     * @param locale  optional locale to use for selecting a text symbol
     * @return the maximum length
     * @see DateTimeField#getMaximumTextLength
     */
    @Impure
    public int getMaximumTextLength(Locale locale) {
        return getField().getMaximumTextLength(locale);
    }

    /**
     * Gets the maximum short text length for the field.
     * 
     * @param locale  optional locale to use for selecting a text symbol
     * @return the maximum length
     * @see DateTimeField#getMaximumShortTextLength
     */
    @Impure
    public int getMaximumShortTextLength(Locale locale) {
        return getField().getMaximumShortTextLength(locale);
    }


    /**
     * Returns the fractional duration milliseconds of this field.
     *
     * @see DateTimeField#remainder
     * @return remainder duration, in milliseconds
     */
    @Impure
    public long remainder() {
        return getField().remainder(getMillis());
    }

    /**
     * Returns the interval that represents the range of the minimum
     * and maximum values of this field.
     * <p>
     * For example, <code>datetime.monthOfYear().toInterval()</code>
     * will return an interval over the whole month.
     *
     * @return the interval of this field
     * @since 1.2
     */
    @Impure
    public Interval toInterval() {
        DateTimeField field = getField();
        long start = field.roundFloor(getMillis());
        long end = field.add(start, 1);
        Interval interval = new Interval(start, end);
        return interval;
    }

    //-----------------------------------------------------------------------
    /**
     * Compare this field to the same field on another instant.
     * <p>
     * The comparison is based on the value of the same field type, irrespective
     * of any difference in chronology. Thus, if this property represents the
     * hourOfDay field, then the hourOfDay field of the other instant will be queried
     * whether in the same chronology or not.
     * 
     * @param instant  the instant to compare to
     * @return negative value if this is less, 0 if equal, or positive value if greater
     * @throws IllegalArgumentException if the instant is null
     */
    @Impure
    public int compareTo(ReadableInstant instant) {
        if (instant == null) {
            throw new IllegalArgumentException("The instant must not be null");
        }
        int thisValue = get();
        int otherValue = instant.get(getFieldType());
        if (thisValue < otherValue) {
            return -1;
        } else if (thisValue > otherValue) {
            return 1;
        } else {
            return 0;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Compare this field to the same field on another partial instant.
     * <p>
     * The comparison is based on the value of the same field type, irrespective
     * of any difference in chronology. Thus, if this property represents the
     * hourOfDay field, then the hourOfDay field of the other partial will be queried
     * whether in the same chronology or not.
     * 
     * @param partial  the partial to compare to
     * @return negative value if this is less, 0 if equal, or positive value if greater
     * @throws IllegalArgumentException if the partial is null
     * @throws IllegalArgumentException if the partial doesn't support this field
     */
    @Impure
    public int compareTo(ReadablePartial partial) {
        if (partial == null) {
            throw new IllegalArgumentException("The partial must not be null");
        }
        int thisValue = get();
        int otherValue = partial.get(getFieldType());
        if (thisValue < otherValue) {
            return -1;
        } else if (thisValue > otherValue) {
            return 1;
        } else {
            return 0;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this property to another.
     * 
     * @param object  the object to compare to
     * @return true if equal
     */
    @Impure
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof AbstractReadableInstantFieldProperty == false) {
            return false;
        }
        AbstractReadableInstantFieldProperty other = (AbstractReadableInstantFieldProperty) object;
        return 
            get() == other.get() &&
            getFieldType().equals(other.getFieldType()) &&
            FieldUtils.equals(getChronology(), other.getChronology());
    }

    /**
     * Returns a hashcode compatible with the equals method.
     * 
     * @return the hashcode
     */
    @Impure
    public int hashCode() {
        return get() * 17 + getFieldType().hashCode() + getChronology().hashCode();
    }

    //-----------------------------------------------------------------------
    /**
     * Output a debugging string.
     * 
     * @return debugging string
     */
    @Pure
    @Impure
    public String toString() {
        return "Property[" + getName() + "]";
    }

}
