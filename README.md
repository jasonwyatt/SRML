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
SRML.getString(context, R.string.mystring);
```
