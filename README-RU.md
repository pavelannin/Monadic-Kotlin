[EN](README.md) | RU

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Что такое Monadic
Monadic - это распределенный Kotlin multiplatform библиотека, который предоставляет восможность использование 
функционального программирования в Kotlin.

## Что значит распределенный фреймворк
Monadic позволяет вам включать в зависимости только ту часть, которую вы планируете использовать в проекте.

Зачастую разработчики не добавляют в свои проекты большие функциональные библиотеки из-за излишней нагруженности, 
увеличение размера приложения и возмжности использования другими разработчиками нежелательной части библиотеки.

Monadic решает эту проблему - подключайте только те части библиотеки которые планируете использовать.

# Как использовать
## Использование BOM 
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-bom?label=monadic-bom)

Спецификации `BOM` позволяет вам управлять всеми версиями библиотеки Monadic, указав только версию спецификации. 
Сама спецификация содержит ссылки на версии различных библиотек Monadic. При использовании спецификации в вашем приложении 
вам не нужно добавлять какую-либо версию зависимости библиотеки Monadic. Когда вы обновляете версию спецификации, 
все используемые вами библиотеки автоматически обновляются до новых версий.

Рекомендуется использование спецификацию `BOM`, если используете больше одной части библиотеки Monadic.

```gradle
implementation(platform("io.github.pavelannin:monadic-bom:<version>"))
```

## Checkable
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-checkable-core?label=monadic-checkable-core)

Тип `Checkable` - это функциональная конструкция, используемая для расширения любого типа состояниями:
проверенно (отмеченно) или непроверенно (неотмеченно).

`Checkable` удабно использовать на уровне бизнеслогики и view стейтов приложений.

```gradle
implementation("io.github.pavelannin:monadic-checkable-core")
```

## Either
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-either-core?label=monadic-either-core)

Монада `Either` - это функциональная конструкция, используемая для обработки альтернативных результатов или ошибок. 

Типизированные ошибки относятся к технике функционального программирования, в которой явно в сигнатуре описываются потенциальные ошибки, 
которые могут возникнуть во время выполнения операции.

```gradle
implementation("io.github.pavelannin:monadic-either-core")
```

## Функции
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-function-core?label=monadic-function-core)

Утилиты для функций в функциональном стиле: композиция, каррирование и т.д.

```gradle
implementation("io.github.pavelannin:monadic-function-core")
```

## Identifiable
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-identifiable-core?label=monadic-identifiable-core)

Тип `Identifiable` представляет собой универсальную конструкцию, предназначенную для идентификации типов. 
Используется для добавления типизированного идентификатора к любому типу.

```gradle
implementation("io.github.pavelannin:monadic-identifiable-core")
```

## LCE
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-lce-core?label=monadic-lce-core)

Монада `LCE` - это функциональная конструкция, используемая для обработки состояния операций (состоящих из 3 состояний: выполнение,
выполненено успешно и ошибка).

Удобное использование для определения состояния экрана или операции на пользовательском представлении 
(использование в MVI подходах).

```gradle
implementation("io.github.pavelannin:monadic-lce-core")
```

### LCE - Either (Extensions)
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-lce-either?label=monadic-lce-either)

Набор утилит расширяющие возможности `LCE` и `Either`.

```gradle
implementation("io.github.pavelannin:monadic-lce-either")
```

## Optional
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-optional-core?label=monadic-optional-core)

Монада `Optinal` - это функциональная конструкция, используемая для значения, которое может быть `null`.

```gradle
implementation("io.github.pavelannin:monadic-optional-core")
```

### Optional - Either (Extensions)
![Maven Central](https://img.shields.io/maven-central/v/io.github.pavelannin/monadic-optional-either?label=monadic-optional-either)

Набор утилит расширяющие возможности `Optional` и `Either`.

```gradle
implementation("io.github.pavelannin:monadic-optional-either")
```
