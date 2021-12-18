[![Release](https://img.shields.io/github/release/quantisbr/brazilian-holidays.svg?style=flat)](https://jitpack.io/#quantisbr/brazilian-holidays)
# Brazilian Holidays
Brazilian Holidays is a Kotlin/Java library that provides resources to consult Brazilian holidays and business days

No other dependencies required.

## Dependency

Add the following to your **build.gradle** file:

```groovy
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.quantisbr:brazilian-holidays:1.0.0'
}
```

Or add the following to your **build.gradle.kts**:

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.quantisbr:brazilian-holidays:1.0.0")
}
```

Or add the following to your **pom.xml**:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.quantisbr</groupId>
    <artifactId>brazilian-holidays</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Using

### Get the date of a mobile religious event from the year

**Kotlin:**

```kotlin
val year = 2021
println(year.toEasterDate())
println(year.toCarnivalDate())
println(year.toGoodFridayDate())
println(year.toCorpusChristiDate())
```

**Java:**

```java
final int year = 2021;
System.out.println(BrazilianHolidays.toEasterDate(year));
System.out.println(BrazilianHolidays.toCarnivalDate(year));
System.out.println(BrazilianHolidays.toGoodFridayDate(year));
System.out.println(BrazilianHolidays.toCorpusChristiDate(year));
```

### Check if the date is a holiday

**Kotlin:**

```kotlin
val date = LocalDate.now()
if (date.isNationalHoliday())
    println("The '$date' is a holiday")
else
    println("The '$date' is not a holiday")
```

**Java:**

```java
final LocalDate date = LocalDate.now();
if (BrazilianHolidays.isNationalHoliday(date))
    System.out.println(String.format("The '%s' is a holiday", date));
else
    System.out.println(String.format("The '%s' is not a holiday", date));
```

### Check if the date is a bank public holiday

**Kotlin:**

```kotlin
val date = LocalDate.now()
if (date.isBankHoliday())
    println("The '$date' is a bank holiday")
else
    println("The '$date' is not a bank holiday")
```

**Java:**

```java
final LocalDate date = LocalDate.now();
if (BrazilianHolidays.isBankHoliday(date))
    System.out.println(String.format("The '%s' is a bank holiday", date));
else
    System.out.println(String.format("The '%s' is not a bank holiday", date));
```

### Check if the date is a business day

**Kotlin:**

```kotlin
val date = LocalDate.now()
if (date.isBusinessDay())
    println("The '$date' is a business day")
else
    println("The '$date' is not a business day")
```

**Java:**

```java
final LocalDate date = LocalDate.now();
if (BrazilianHolidays.isBusinessDay(date, false))
    System.out.println(String.format("The '%s' is a business day", date));
else
    System.out.println(String.format("The '%s' is not a business day", date));
```

### Check if the date is a bank business day

**Kotlin:**

```kotlin
val date = LocalDate.now()
if (date.isBankBusinessDay())
    println("The '$date' is a bank business day")
else
    println("The '$date' is not a bank business day")
```

**Java:**

```java
final LocalDate date = LocalDate.now();
if (BrazilianHolidays.isBankBusinessDay(date))
    System.out.println(String.format("The '%s' is a bank business day", date));
else
    System.out.println(String.format("The '%s' is not a bank business day", date));
```

### Count the number of business days in the date range

**Kotlin:**

```kotlin
import br.com.quantis.libraries.dates.holidays.brazil.countBusinessDays
import br.com.quantis.libraries.dates.rangeTo
import java.time.LocalDate

...

val start = LocalDate.of(2019, 3, 12)
val end  = LocalDate.of(2021, 11, 15)
val range = start..end
println("Number of business day: ${range.countBusinessDays()}")
```

**Java:**

```java
import br.com.quantis.libraries.dates.DateRange;
import br.com.quantis.libraries.dates.holidays.brazil.BrazilianHolidays;
import java.time.LocalDate;

...

final LocalDate start = LocalDate.of(2019, 3, 12);
final LocalDate end = LocalDate.of(2021, 11, 15);
final DateRange range = new DateRange(start, end);

System.out.println("Number of business day: " + BrazilianHolidays.countBankBusinessDays(range));
```

### Count the number of bank business days in the date range

**Kotlin:**

```kotlin
import br.com.quantis.libraries.dates.holidays.brazil.countBankBusinessDays
import br.com.quantis.libraries.dates.rangeTo
import java.time.LocalDate

...

val start = LocalDate.of(2019, 3, 12)
val end  = LocalDate.of(2021, 11, 15)
val range = start..end
println("Number of bank business day: ${range.countBankBusinessDays()}")
```

**Java:**

```java
import br.com.quantis.libraries.dates.DateRange;
import br.com.quantis.libraries.dates.holidays.brazil.BrazilianHolidays;
import java.time.LocalDate;

...

final LocalDate start = LocalDate.of(2019, 3, 12);
final LocalDate end = LocalDate.of(2021, 11, 15);
final DateRange range = new DateRange(start, end);

System.out.println("Number of bank business day: " + BrazilianHolidays.countBankBusinessDays(range));
```