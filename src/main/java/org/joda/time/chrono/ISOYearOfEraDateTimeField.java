/*
 *  Copyright 2001-2013 Stephen Colebourne
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
package org.joda.time.chrono;

import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.Impure;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.ReadablePartial;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.FieldUtils;

/**
 * This field is not publicy exposed by ISOChronology, but rather it is used to
 * build the yearOfCentury and centuryOfEra fields. It merely drops the sign of
 * the year.
 *
 * @author Brian S O'Neill
 * @see GJYearOfEraDateTimeField
 * @since 1.0
 */
class ISOYearOfEraDateTimeField extends DecoratedDateTimeField {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 7037524068969447317L;

    /**
     * Singleton instance
     */
    static final DateTimeField INSTANCE = new ISOYearOfEraDateTimeField();

    /**
     * Restricted constructor.
     */
    @Impure
    private ISOYearOfEraDateTimeField() {
        super(GregorianChronology.getInstanceUTC().year(), DateTimeFieldType.yearOfEra());
    }

    @Impure
    public int get(long instant) {
        int year = getWrappedField().get(instant);
        return year < 0 ? -year : year;
    }

    @Impure
    public long add(long instant, int years) {
        return getWrappedField().add(instant, years);
    }

    @Impure
    public long add(long instant, long years) {
        return getWrappedField().add(instant, years);
    }

    @Impure
    public long addWrapField(long instant, int years) {
        return getWrappedField().addWrapField(instant, years);
    }

    @Impure
    public int[] addWrapField(ReadablePartial instant, int fieldIndex, int[] values, int years) {
        return getWrappedField().addWrapField(instant, fieldIndex, values, years);
    }

    @Impure
    public int getDifference(long minuendInstant, long subtrahendInstant) {
        return getWrappedField().getDifference(minuendInstant, subtrahendInstant);
    }

    @Impure
    public long getDifferenceAsLong(long minuendInstant, long subtrahendInstant) {
        return getWrappedField().getDifferenceAsLong(minuendInstant, subtrahendInstant);
    }

    @Impure
    public long set(long instant, int year) {
        FieldUtils.verifyValueBounds(this, year, 0, getMaximumValue());
        if (getWrappedField().get(instant) < 0) {
            year = -year;
        }
        return super.set(instant, year);
    }

    @Pure
    public int getMinimumValue() {
        return 0;
    }

    @Impure
    public int getMaximumValue() {
        return getWrappedField().getMaximumValue();
    }

    @Impure
    public long roundFloor(long instant) {
        return getWrappedField().roundFloor(instant);
    }

    @Impure
    public long roundCeiling(long instant) {
        return getWrappedField().roundCeiling(instant);
    }

    @Impure
    public long remainder(long instant) {
        return getWrappedField().remainder(instant);
    }

    /**
     * Serialization singleton
     */
    @Pure
    private Object readResolve() {
        return INSTANCE;
    }
}
