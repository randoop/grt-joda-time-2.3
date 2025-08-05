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
import java.io.Serializable;
import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;

/**
 * <code>DelegatedDateTimeField</code> delegates each method call to the
 * date time field it wraps.
 * <p>
 * DelegatedDateTimeField is thread-safe and immutable, and its subclasses must
 * be as well.
 *
 * @author Brian S O'Neill
 * @since 1.0
 * @see DecoratedDateTimeField
 */
public class DelegatedDateTimeField extends DateTimeField implements Serializable {

    /** Serialization version */
    private static final long serialVersionUID = -4730164440214502503L;

    /** The DateTimeField being wrapped */
    private final DateTimeField iField;
    /** The override field type */
    private final DateTimeFieldType iType;

    /**
     * Constructor.
     * 
     * @param field  the field being decorated
     */
    @Impure
    public DelegatedDateTimeField(DateTimeField field) {
        this(field, null);
    }

    /**
     * Constructor.
     * 
     * @param field  the field being decorated
     * @param type  the field type override
     */
    @Impure
    public DelegatedDateTimeField(DateTimeField field, DateTimeFieldType type) {
        super();
        if (field == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        iField = field;
        iType = (type == null ? field.getType() : type);
    }

    /**
     * Gets the wrapped date time field.
     * 
     * @return the wrapped DateTimeField
     */
    @Pure
    public final DateTimeField getWrappedField() {
        return iField;
    }

    @Pure
    public DateTimeFieldType getType() {
        return iType;
    }

    @Pure
    @Impure
    public String getName() {
        return iType.getName();
    }

    @Impure
    public boolean isSupported() {
        return iField.isSupported();
    }

    @Impure
    public boolean isLenient() {
        return iField.isLenient();
    }

    @Impure
    public int get(long instant) {
        return iField.get(instant);
    }

    @Impure
    public String getAsText(long instant, Locale locale) {
        return iField.getAsText(instant, locale);
    }

    @Impure
    public String getAsText(long instant) {
        return iField.getAsText(instant);
    }

    @Impure
    public String getAsText(ReadablePartial partial, int fieldValue, Locale locale) {
        return iField.getAsText(partial, fieldValue, locale);
    }

    @Impure
    public String getAsText(ReadablePartial partial, Locale locale) {
        return iField.getAsText(partial, locale);
    }

    @Impure
    public String getAsText(int fieldValue, Locale locale) {
        return iField.getAsText(fieldValue, locale);
    }

    @Impure
    public String getAsShortText(long instant, Locale locale) {
        return iField.getAsShortText(instant, locale);
    }

    @Impure
    public String getAsShortText(long instant) {
        return iField.getAsShortText(instant);
    }

    @Impure
    public String getAsShortText(ReadablePartial partial, int fieldValue, Locale locale) {
        return iField.getAsShortText(partial, fieldValue, locale);
    }

    @Impure
    public String getAsShortText(ReadablePartial partial, Locale locale) {
        return iField.getAsShortText(partial, locale);
    }

    @Impure
    public String getAsShortText(int fieldValue, Locale locale) {
        return iField.getAsShortText(fieldValue, locale);
    }

    @Impure
    public long add(long instant, int value) {
        return iField.add(instant, value);
    }

    @Impure
    public long add(long instant, long value) {
        return iField.add(instant, value);
    }

    @Impure
    public int[] add(ReadablePartial instant, int fieldIndex, int[] values, int valueToAdd) {
        return iField.add(instant, fieldIndex, values, valueToAdd);
    }

    @Impure
    public int[] addWrapPartial(ReadablePartial instant, int fieldIndex, int[] values, int valueToAdd) {
        return iField.addWrapPartial(instant, fieldIndex, values, valueToAdd);
    }

    @Impure
    public long addWrapField(long instant, int value) {
        return iField.addWrapField(instant, value);
    }

    @Impure
    public int[] addWrapField(ReadablePartial instant, int fieldIndex, int[] values, int valueToAdd) {
        return iField.addWrapField(instant, fieldIndex, values, valueToAdd);
    }

    @Impure
    public int getDifference(long minuendInstant, long subtrahendInstant) {
        return iField.getDifference(minuendInstant, subtrahendInstant);
    }

    @Impure
    public long getDifferenceAsLong(long minuendInstant, long subtrahendInstant) {
        return iField.getDifferenceAsLong(minuendInstant, subtrahendInstant);
    }

    @Impure
    public long set(long instant, int value) {
        return iField.set(instant, value);
    }

    @Impure
    public long set(long instant, String text, Locale locale) {
        return iField.set(instant, text, locale);
    }

    @Impure
    public long set(long instant, String text) {
        return iField.set(instant, text);
    }

    @Impure
    public int[] set(ReadablePartial instant, int fieldIndex, int[] values, int newValue) {
        return iField.set(instant, fieldIndex, values, newValue);
    }

    @Impure
    public int[] set(ReadablePartial instant, int fieldIndex, int[] values, String text, Locale locale) {
        return iField.set(instant, fieldIndex, values, text, locale);
    }

    @Impure
    public DurationField getDurationField() {
        return iField.getDurationField();
    }

    @Impure
    public DurationField getRangeDurationField() {
        return iField.getRangeDurationField();
    }

    @Impure
    public boolean isLeap(long instant) {
        return iField.isLeap(instant);
    }

    @Impure
    public int getLeapAmount(long instant) {
        return iField.getLeapAmount(instant);
    }

    @Impure
    public DurationField getLeapDurationField() {
        return iField.getLeapDurationField();
    }

    @Impure
    public int getMinimumValue() {
        return iField.getMinimumValue();
    }

    @Impure
    public int getMinimumValue(long instant) {
        return iField.getMinimumValue(instant);
    }

    @Impure
    public int getMinimumValue(ReadablePartial instant) {
        return iField.getMinimumValue(instant);
    }

    @Impure
    public int getMinimumValue(ReadablePartial instant, int[] values) {
        return iField.getMinimumValue(instant, values);
    }

    @Impure
    public int getMaximumValue() {
        return iField.getMaximumValue();
    }

    @Impure
    public int getMaximumValue(long instant) {
        return iField.getMaximumValue(instant);
    }

    @Impure
    public int getMaximumValue(ReadablePartial instant) {
        return iField.getMaximumValue(instant);
    }

    @Impure
    public int getMaximumValue(ReadablePartial instant, int[] values) {
        return iField.getMaximumValue(instant, values);
    }

    @Impure
    public int getMaximumTextLength(Locale locale) {
        return iField.getMaximumTextLength(locale);
    }

    @Impure
    public int getMaximumShortTextLength(Locale locale) {
        return iField.getMaximumShortTextLength(locale);
    }

    @Impure
    public long roundFloor(long instant) {
        return iField.roundFloor(instant);
    }

    @Impure
    public long roundCeiling(long instant) {
        return iField.roundCeiling(instant);
    }

    @Impure
    public long roundHalfFloor(long instant) {
        return iField.roundHalfFloor(instant);
    }

    @Impure
    public long roundHalfCeiling(long instant) {
        return iField.roundHalfCeiling(instant);
    }

    @Impure
    public long roundHalfEven(long instant) {
        return iField.roundHalfEven(instant);
    }

    @Impure
    public long remainder(long instant) {
        return iField.remainder(instant);
    }

    @Pure
    @Impure
    public String toString() {
        return ("DateTimeField[" + getName() + ']');
    }

}
