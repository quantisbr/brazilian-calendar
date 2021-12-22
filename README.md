[![Release](https://img.shields.io/github/release/quantisbr/brazilian-calendar.svg?style=flat)](https://jitpack.io/#quantisbr/brazilian-calendar/1.0.0)

*Leia esta documentação em [Português](README.pt-br.md).*

# Brazilian Calendar
_Brazilian Calendar_ is a Kotlin/Java library that provides resources to consult national holidays, banking holidays,
business days, banking business days and dates of mobile religious events.

## Dependency

Add the following to your **build.gradle** file:

```groovy
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.quantisbr:brazilian-calendar:1.0.0'
}
```

Or add the following to your **build.gradle.kts**:

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.quantisbr:brazilian-calendar:1.0.0")
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
    <artifactId>brazilian-calendar</artifactId>
    <version>1.0.0</version>
</dependency>
```

**No other dependencies required.**

## Using

Access the example project [here](https://github.com/quantisbr/brazilian-calendar-example).

### Get the date of a mobile religious event from the year

**Kotlin:**

```kotlin
val year = 2021
println("Carnival: " + year.toCarnivalDate())
println("Good Friday: " + year.toGoodFridayDate())
println("Easter: " + year.toEasterDate())
println("Corpus Christi: " + year.toCorpusChristiDate())
```

**Java:**

```java
final int year = 2021;
System.out.println("Carnival: " + MobileReligiousEvents.toCarnivalDate(year));
System.out.println("Good Friday: " + MobileReligiousEvents.toGoodFridayDate(year));
System.out.println("Easter: " + MobileReligiousEvents.toEasterDate(year));
System.out.println("Corpus Christi: " + MobileReligiousEvents.toCorpusChristiDate(year));
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
if (BrazilianCalendar.isNationalHoliday(date))
    System.out.printf("The '%s' is a holiday%n", date);
else
    System.out.printf("The '%s' is not a holiday%n", date);
```

### Check if the date is a banking holiday

**Kotlin:**

```kotlin
val date = LocalDate.now()
if (date.isBankingHoliday())
    println("The '$date' is a banking holiday")
else
    println("The '$date' is not a banking holiday")
```

**Java:**

```java
final LocalDate date = LocalDate.now();
if (BrazilianCalendar.isBankingHoliday(date))
    System.out.printf("The '%s' is a banking holiday%n", date);
else
    System.out.printf("The '%s' is not a banking holiday%n", date);
```

### Check if the date is a business day

**Kotlin:**

```kotlin
val date = LocalDate.now()
if (date.isBusinessDay(false))
    println("The '$date' is a business day")
else
    println("The '$date' is not a business day")
```

**Java:**

```java
final LocalDate date = LocalDate.now();
if (BrazilianCalendar.isBusinessDay(date, false))
    System.out.printf("The '%s' is a business day%n", date);
else
    System.out.printf("The '%s' is not a business day%n", date);
```

### Check if the date is a banking business day

**Kotlin:**

```kotlin
val date = LocalDate.now()
if (date.isBankingBusinessDay())
    println("The '$date' is a banking business day")
else
    println("The '$date' is not a banking business day")
```

**Java:**

```java
final LocalDate date = LocalDate.now();
if (BrazilianCalendar.isBankingBusinessDay(date))
    System.out.printf("The '%s' is a bank business day%n", date);
else
    System.out.printf("The '%s' is not a bank business day%n", date);
```

### Count the number of business days in the date range

**Kotlin:**

```kotlin
val start = LocalDate.of(2019, 3, 12)
val end = LocalDate.of(2021, 11, 15)
val range = DateRange(start, end)
println("Number of business day: " + range.countBusinessDays())
```

**Java:**

```java
final LocalDate start = LocalDate.of(2019, 3, 12);
final LocalDate end = LocalDate.of(2021, 11, 15);

System.out.println("Number of business day: " + BrazilianCalendar.countBusinessDays(start, end, false));
```

### Count the number of banking business days in the date range

**Kotlin:**

```kotlin
val start = LocalDate.of(2019, 3, 12)
val end = LocalDate.of(2021, 11, 15)
val range = start..end

println("Number of banking business day: " + range.countBankingBusinessDays())
```

**Java:**

```java
final LocalDate start = LocalDate.of(2019, 3, 12);
final LocalDate end = LocalDate.of(2021, 11, 15);

System.out.println("Number of banking business day: " + BrazilianCalendar.countBankingBusinessDays(start, end));
```