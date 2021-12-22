[![Release](https://img.shields.io/github/release/quantisbr/brazilian-calendar.svg?style=flat)](https://jitpack.io/#quantisbr/brazilian-calendar/1.0.0)

*Read this doc in [English](README.md).*

# Calendário brasileiro
_Feriados brasileiro_ é uma biblioteca Kotlin/Java que prevê recursos para consultar feriados nacionais, 
feriados bancários, dias uteis, dias uteis bancários e data de eventos religiosos móveis.

## Dependência

Adicione o seguinte ao seu arquivo **build.gradle**:

```groovy
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.quantisbr:brazilian-calendar:1.0.0'
}
```

Ou adicione o seguinte ao seu arquivo **build.gradle.kts**:

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.quantisbr:brazilian-calendar:1.0.0")
}
```

Ou adicione o seguinte ao seu arquivo **pom.xml**:

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

**Outras dependências não são necessárias.**

## Usando

Acesse o projeto de exemplo [aqui](https://github.com/quantisbr/brazilian-calendar-example).

### Obtenha a data de um evento religioso móvel do ano

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

### Verifique se a data é um feriado

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

### Verifique se a data é um feriado bancário

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

### Verifique se a data é um dia útil

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

### Verifique se a data é um dia útil bancário

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

### Conte o número de dias úteis no intervalo de datas

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

### Conte o número de dias úteis bancários no intervalo de datas

**Kotlin:**

```kotlin
val start = LocalDate.of(2019, 3, 12)
val end = LocalDate.of(2021, 11, 15)
val range = DateRange(start, end)
println("Number of bank business day: " + range.countBankBusinessDays())
```

**Java:**

```java
final LocalDate start = LocalDate.of(2019, 3, 12);
final LocalDate end = LocalDate.of(2021, 11, 15);

System.out.println("Number of bank business day: " + BrazilianCalendar.countBankingBusinessDays(start, end));
```