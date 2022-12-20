// Name: Manasse Bosango
// Course:CS 210
// Date: 06/13/20222
// Classs: CS210
// Reason: Understand the importance, and the use of objects and classes in java and programming

public class Date implements Comparable<Date> {
    // This is declaring the variables that will be used in the class.
    private int year;
    private int month;
    private int day;
    private static final int JANUARY = 1;
    private static final String[] monthNames = { "January", "February", "March", "April", "May", "June", "July",
            "August", " September",
            "October",
            "November", "December" };

    // This is the default constructor.
    public Date() {
        this(1970, JANUARY, 1);
    }

    // This is a constructor that is taking in the parameters of year, month, and
    // day. It is then
    // setting the values of the variables to the parameters.
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        checkForInvalidInput();
    }

    // Accessors
    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    // mutators
    private void checkForInvalidInput() {
        if (year < 0 || month < 1 || month > 12 || day < 1 ||
                day > getDaysInMonth()) {
            throw new IllegalArgumentException();
        }
    }

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        checkForInvalidInput();
    }

    public String lookForMonthName() {
        String nameOfMonth = "";
        for (int i = 0; i < monthNames.length; i++) {
            if (month == i + 1) {
                nameOfMonth = monthNames[i];
            }
        }
        return nameOfMonth;
    }

    public String longDate() {
        // Returning the month name, day, and year.
        return lookForMonthName() + " " + day + ", " + year;
    }

    public void addWeeks(int weeks) {
        // Multiplying the weeks by 7 and then adding that to the days.
        addDays(weeks * 7);
    }

    //
    public void addDays(int days) {
        day += days;

        // For positive input of days
        while (day > getDaysInMonth()) {
            // Subtracting the days in the month from the day.
            day -= getDaysInMonth();
            month++;

            if (month > 12) {
                year++;
                month -= 12;
            }
        }

        // For negative input of days
        while (day <= 0) {
            month--;

            if (month <= 0) {
                year--;
                month += 12;
            }
            // Adding the days in the month to the day.
            day += getDaysInMonth();
        }
    }

    public Date clone() {
        return new Date(this.year, this.month, this.day);
    }

    // helper method to get the number of days in a given month
    private int getDaysInMonth() {

        // checkForInvalidInput for months that have 31 days
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;

        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;

        } else {
            // 28 days for a non-leap year
            if (isLeapYear()) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    public int getDaysInYear() {
        // This is checking if the year is a leap year.
        if (isLeapYear()) {
            return 366;
        } else {
            return 365;
        }
    }

    // add the numbers days of each month
    /*
     * Returns the number of days that this Date must be adjusted to
     * make it equal to the given other Date .
     */

    public int numDaysInYears(Date other) {
        Date start = new Date(this.year, this.month, this.day);
        int sumOfYearDays = 0;

        // case 1
        while (start.year < other.year - 1) {
            start.year++;
            // Adding the days in the year to the sumOfYearDays.
            sumOfYearDays += start.getDaysInYear();
        }

        // case 2
        while (start.year > other.year + 1) {

            // Subtracting the days in the year from the sumOfYearDays.
            sumOfYearDays += start.getDaysInYear();
            start.year--;
        }

        return sumOfYearDays;
    }

    public int january1toDate(Date other) {
        Date current = new Date(other.year, 1, 1);
        int numDays = 0;
        while (current.month < other.month) {
            numDays += current.getDaysInMonth();
            current.month++;
        }
        numDays += other.day;
        return numDays;
    }

    public int dayTo1(Date other) {

        if (this.year == other.year)
            return numDays(other);

        if (this.year < other.year)
            return (this.getDaysInYear() - january1toDate(this))
                    + numDaysInYears(other) + january1toDate(other);
        else
            return ((other.getDaysInYear() - january1toDate(other))
                    + numDaysInYears(other) + january1toDate(this)) * (-1);

    }

    public int numDays(Date end) {

        if (this.month <= end.month) {
            Date start = new Date(this.year, this.month, this.day);
            return end.day - start.day + numDaysInMonths(start, end);
        } else {
            // end.month < this.month
            return -end.numDays(this);
        }
    }

    public static int numDaysInMonths(Date start, Date end) {

        int days = 0;
        while (start.getMonth() < end.getMonth()) {
            days += start.getDaysInMonth();
            start.month++;
        }
        return days;
    }

    public static void testDays2() {
        int year = 2020;
        int getDaysInYear = 366;

        int successes = 0, failures = 0;
        Date start = new Date(year, 1, 1);
        for (int dStart = 0; dStart < getDaysInYear; dStart++) {

            Date end = new Date(2030, 1, 1);
            for (int dEnd = 0; dEnd < getDaysInYear; dEnd++) {
                // check here
                // System.out.println(start + " vs. " + end);
                if (start.daysTo4(end) == start.daysTo(end))
                    successes++;
                else
                    failures++;
                end.addDays(1);
            }

            start.addDays(1);
        }
        System.out.println("testDays2: " + successes + " / failures: " + failures);
    }

    public static boolean checkDayTo1(Date start, Date end) {
        Date one = start.clone();

        one.addDays(one.dayTo1(end));

        if (!one.equals(end)) {
            // System.out.println("They are not the same");
            return false;
        }
        return true;
    }
    // dayto1 + this == end

    public static void testDays2_TODO() {
        Date base = new Date(2020, 9, 15);
        int[] c = { -2, 0, 2 };
        for (int yearC : c) {
            for (int monthC : c) {
                for (int dayC : c) {
                    int year = 2020 + yearC;
                    int month = 9 + monthC;
                    int day = 15 + dayC;
                    Date current = new Date(year, month, day);
                    System.out.println("2020-09-15 vs "
                            + year + "-" + month + "-" + day + " ==> "
                            + (base.daysTo(current) - base.daysTo2(current)));
                }
            }
        }
    }

    /*
     * public int january1toDate(Date other) {
     * Date current = new Date(other.year, 1, 1);
     * int numDays = 0;
     * while (current.month < other.month) {
     * numDays += current.getDaysInMonth();
     * current.month++;
     * }
     * numDays += other.day - current.day;
     * return numDays;
     * }
     */
    // returns the number of days from 'this' until 'end'
    public int daysTo4(Date end) {
        int startYear = Math.min(this.year, end.year);
        return january1toDate(startYear, end) - january1toDate(startYear, this);
    }

    public int january1toDate(int startYear, Date end) {
        Date current = new Date(startYear, 1, 1);
        int numDays = 0;

        while (current.year < end.year) {
            numDays += current.getDaysInYear();
            current.year++;
        }

        while (current.month < end.month) {
            numDays += current.getDaysInMonth();
            current.month++;
        }
        numDays += end.day;
        return numDays;
    }

    public int daysTo(Date other) {
        Date thisClone = new Date(this.year, this.month, this.day);
        int result = 0;
        int direction;

        if (dateIsGreater(thisClone, other)) {
            direction = -1;
        } else {
            direction = 1;
        }

        while (!dateIsEqual(thisClone, other)) {

            // Adding the days to the clone of the object.
            thisClone.addDays(direction);

            // Adding the direction to the result.
            result += direction;
        }
        return result;
    }

    public static void testDateIsGreater() {
        Date base = new Date(2020, 9, 15);
        int[] c = { -2, 0, 2 };
        for (int yearC : c) {
            for (int monthC : c) {
                for (int dayC : c) {
                    int year = 2020 + yearC;
                    int month = 9 + monthC;
                    int day = 15 + dayC;
                    Date current = new Date(year, month, day);
                    System.out.println("2020-09-15 vs "
                            + year + "-" + month + "-" + day + " ==> "
                            + (dateIsGreater2(base, current) == dateIsGreater(base, current)));
                }
            }
        }
    }

    public static boolean dateIsEqual(Date thisClone, Date other) {
        if (thisClone.compareTo(other) == 0) {
            return true;
        }
        return false;
    }

    public static boolean dateIsGreater(Date d1, Date d2) {
        return (d1.year > d2.year) ||
                (d1.year == d2.year && d1.month > d2.month) ||
                (d1.year == d2.year && d1.month == d2.month && d1.day > d2.day);
    }

    public static boolean dateIsSmaller(Date d1, Date d2) {
        return (d1.year < d2.year) ||
                (d1.year == d2.year && d1.month < d2.month) ||
                (d1.year == d2.year && d1.month == d2.month && d1.day < d2.day);
    }

    public static boolean dateIsGreater2(Date thisClone, Date other) {
        if (thisClone.compareTo(other) >= 1) {
            return true;
        }
        return false;

    }

    @Override
    public int compareTo(Date o) {

        // Subtracting the year of the object from the year of the other object.
        int diff = this.year - o.year;
        if (diff != 0) {
            // System.out.println("compareTo returns (1) " + diff);
            return diff;
        }

        // Subtracting the month of the object from the month of the other object.
        diff = this.month - o.month;
        if (diff != 0) {
            // System.out.println("compareTo returns (2) " + diff);
            return diff;
        }

        // Subtracting the day of the object from the day of the other object.
        // System.out.println("compareTo returns (3) " + (this.day - o.day));
        return this.day - o.day;
    }

    public static boolean dateIsLess(Date thisClone, Date other) {
        if (thisClone.compareTo(other) <= -1) {
            return true;
        }
        return false;
    }

    public void addYears(int years) {
        year += years;
    }

    public boolean isLeapYear() {
        // This is checking if the year is a leap year.
        return (year % 4 == 0) && (!(year % 100 == 0) || year % 400 == 0);
    }

    /**
     * If the object is a Date object, then compare the year, month, and day fields.
     * Otherwise, return
     * false
     */
    public boolean equals(Object o) {
        if (o instanceof Date) {
            Date other = (Date) o;
            return this.year == other.year && this.month == other.month
                    && this.day == other.day;

        } else { // not a Date object
            return false;
        }
    }

    public String toString() {
        // Creating a string and then adding the year, month, and day to the string. It
        // is then
        // returning the string.
        String strDate = "";

        strDate += year;
        strDate += "/";

        if (month < 10)
            strDate += "0";
        strDate += month;
        strDate += "/";

        if (day < 10)
            strDate += "0";
        strDate += day;

        return strDate;
    }

}
