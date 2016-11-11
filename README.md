[![](https://jitpack.io/v/jasonwyatt/SRML.svg)](https://jitpack.io/#jasonwyatt/SRML)

# SRML
SRML stands for "String Resource Markup Language": Style your localized strings for Android.

## SRML Tags

* `{{b}}Text{{/b}}` -> **Text**
* `{{i}}Text{{/i}}` -> *Text*
* `{{u}}Text{{/u}}` -> underlined text (markdown fail!)
* `{{strike}}Text{{/strike}}` -> ~~Text~~
* `{{color value=#FF0000}}Text{{/color}}` -> red "Text"
  * supports `value` in the following formats:
     * Three character (css-like): #ABC
     * Six character opaque: #FF00FF
     * Eight character alpha: #50000000
* etc...

## Configuring your Dependencies

Add [jitpack.io](https://jitpack.io) to your root `build.gradle` at the end of `repositories`:

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add SRML as a dependency to your app's `build.gradle`:

```groovy
dependencies {
    compile 'com.github.jasonwyatt:SRML:-SNAPSHOT'
}
```

## How to use

```java
// simple case
SRML.getString(context, R.string.mystring);

// parameterized strings
SRML.getString(context, R.string.my_parameterized_string, firstArg, secondArg, ...);

// quantity strings
SRML.getQuantityString(context, R.plurals.my_plurals_resource, quantity, ...format args...);

// String array resources
SRML.getStringArray(context, R.array.my_string_array);
```

Your resources can be arbitrarily complex, involving multiple, nested tags.