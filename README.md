# detekt-extensions

This is extensions for [detekt](https://github.com/arturbosch/detekt).  
This RuleSet reports mismatched [KDoc](https://kotlinlang.org/docs/reference/kotlin-doc.html#block-tags) block tags.

## Usage
### build.gradle
Add JitPack repository

in `build.gradle`
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

or

`build.gradle.kts`
```kotlin
repositories {
    maven("https://jitpack.io")
}
```

and add dependency
```kotlin
dependencies {
    detektPlugins("com.github.duck8823:detekt-kdoc-ruleset:0.0.1")
}
```
