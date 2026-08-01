# veil

A library that provides Kotlin DSL and extension functions
for [polymer-virtual-entity](https://github.com/patbox/polymer). It allows you to write elements and element holders in
DSL style and use useful extension functions.

> Note: Until version 1.0.0, the API may change without prior notice.

## Gradle Setup

### Kotlin

```kotlin
repositories {
    maven("https://repo.biryeong.kim/releases")
}

dependencies {
    implementation("org.zuttomae:veil:YOUR_VERSION")
}
```

### Groovy

```groovy
repositories {
    maven { url 'https://repo.biryeong.kim/releases' }
}

dependencies {
    implementation 'org.zuttomae:veil:YOUR_VERSION'
}
```