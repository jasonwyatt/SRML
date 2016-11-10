# SRML
SRML stands for String Resource Markup Language: Style your localized strings for Android.

## SRML Tags

* `{{b}}Text{{/b}}` -> **Text**
* `{{i}}Text{{/i}}` -> *Text*
* `{{u}}Text{{/u}}` -> underlined text (markdown fail!)
* `{{strike}}Text{{/strike}}` -> ~~Text~~
* etc...

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